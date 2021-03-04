package com.banistmo.ctg.mdw.payroll.api.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import com.banistmo.ctg.mdw.payroll.api.business.validators.MaxLengthValidator;
import com.banistmo.ctg.mdw.payroll.api.business.validators.RecordValidator;
import com.banistmo.ctg.mdw.payroll.api.business.validators.RequiredValidator;
import com.banistmo.ctg.mdw.payroll.api.business.validators.TypeFileValidator;
import com.banistmo.ctg.mdw.payroll.api.business.validators.Validator;
import com.banistmo.ctg.mdw.payroll.api.dbmodel.BatchType;
import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldsConfiguration;
import com.banistmo.ctg.mdw.payroll.api.domain.LineInfo;
import com.banistmo.ctg.mdw.payroll.api.domain.Response;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRq;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRs;
import com.banistmo.ctg.mdw.payroll.api.exception.ValidateFileStructureException;
import com.banistmo.ctg.mdw.payroll.api.repository.FileTypeRepository;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.banistmo.ctg.mdw.payroll.api.util.Utilities;

@Service
public class PayrollBusinessImp implements PayrollBusiness {

	private final FileTypeRepository fileTypeRepository;
	private final RecordValidator recordValidatorComponent;

	private static final String PATTERN_FILE_NAME = "[A-Za-z0-9\\s_-]+";
	private static final String FILE_PATH_VOLUME = System.getenv("FILES_PATH");
	private static final String FILE_NAME_ERROR = ".error";
	private static final String CSV_EXTENSION = ".csv";
	private static final int FILENAME_MAX_CHARACTERS = 50;
	private static final int ID_FILE_TYPE = 1;
	private static final int AMOUNT_FIELD_ID = 2;

	@Autowired
	public PayrollBusinessImp(FileTypeRepository fileTypeRepository, RecordValidator recordValidatorComponent) {
		this.fileTypeRepository = fileTypeRepository;
		this.recordValidatorComponent = recordValidatorComponent;
	}

	@Override
	public Response<ValidatePayrollFileRs> validatePayrollFile(ValidatePayrollFileRq validatePayrollFileRq) {
		Response<ValidatePayrollFileRs> response;

		byte[] decodedBytes = Base64.getDecoder().decode(validatePayrollFileRq.getFile());

		var validatePayrollFileRs = new ValidatePayrollFileRs();
		try {
			if (!validateFileParameters(validatePayrollFileRq.getFileName(), validatePayrollFileRq.getExtension(),
					validatePayrollFileRq.getSeparator())) {
				throw new ValidateFileStructureException("Error validando parametros");
			}

			var identifier = Utilities.generateUuid();
			Path directoryPath = Files.createDirectories(Paths.get(FILE_PATH_VOLUME + LocalDate.now()));
			
			Path filePath = directoryPath.resolve(formatFileName(validatePayrollFileRq.getFileName(), identifier, 
					validatePayrollFileRq.getExtension()));

			Files.write(filePath, decodedBytes, StandardOpenOption.CREATE);

			String errorFilePath = directoryPath + Constants.SLASH + formatErrorFileName(validatePayrollFileRq.getFileName(),
					identifier);
			validateFileStructure(validatePayrollFileRs, decodedBytes, validatePayrollFileRq.getSeparator(),
					errorFilePath);

			validatePayrollFileRs.setNameValidatedFile(filePath.toString());
			validatePayrollFileRs.setNameErrorFile(errorFilePath);

		} catch (IOException e) {
			Logger.error("Error guardando el archivo con lotes: " + e.getMessage());
			return new Response<>(Constants.ERROR_GENERAL_CODE, Constants.ERROR_MSG);
		} catch (Exception e) {
			Logger.error("Ocurrio un error inesperado en la validación del archivo" + e.getMessage());
			return new Response<>(Constants.ERROR_GENERAL_CODE, Constants.ERROR_MSG);
		}

		response = new Response<>(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, validatePayrollFileRs);
		return response;
	}

	/**
	 * Valida los parametros definidos para un archivo
	 * @param fileName
	 * @param extension
	 * @param separator
	 * @return
	 */
	private boolean validateFileParameters(String fileName, String extension, String separator) {
		boolean isValidFileParameters = true;

		if (fileName.length() > FILENAME_MAX_CHARACTERS || !fileName.matches(PATTERN_FILE_NAME)
				|| ("CSV".equalsIgnoreCase(extension) && !Constants.COMMA.equalsIgnoreCase(separator))
				|| ("TXT".equalsIgnoreCase(extension) && !Constants.PIPE.equalsIgnoreCase(separator)
						&& !Constants.SEMICOLON.equalsIgnoreCase(separator))) {
			isValidFileParameters = false;
		}

		return isValidFileParameters;

	}
	
	/**
	 * Valida la estructura del archivo de acuerdo a la configuración de los campos en cada linea
	 * @param validatePayrollFileRs
	 * @param file
	 * @param separator
	 * @param errorFilePath
	 * @return
	 * @throws ValidateFileStructureException
	 */
	private boolean validateFileStructure(ValidatePayrollFileRs validatePayrollFileRs, byte[] file, String separator,
			String errorFilePath) {
		boolean isValidStructure = false;
		long amountCorrectLines = 0;
		long amountIncorrectLines = 0;
		boolean isFileEmpty = true;

		var isr = new InputStreamReader(new ByteArrayInputStream(file), StandardCharsets.UTF_8);
		var totalAmounts = new ArrayList<BigDecimal>();
		
		try (BufferedWriter errorsWriter = new BufferedWriter(new FileWriter(errorFilePath, true));
				var br = new BufferedReader(isr)) {
	
			String lineContent;
			List<FieldsConfiguration> fieldsConfigurations;
			BatchType batchType = this.fileTypeRepository.findByBatchTypeId(ID_FILE_TYPE);
			if (batchType != null) {				
				fieldsConfigurations = batchType.getFieldsConfigurations();
			} else {
				throw new ValidateFileStructureException("Error obteniendo la configuración del tipo de archivo");
			}
			
			var validators = getFieldValidators();
			
			int numberLine = 1;
			while ((lineContent = br.readLine()) != null) {
				isFileEmpty = false;
				Logger.info("Contenido del registro ", lineContent);
				LineInfo lineInfo = new LineInfo();
				lineInfo.setLineContent(lineContent);
				lineInfo.setLineNumber(numberLine);
				lineInfo.setAmountFieldId(AMOUNT_FIELD_ID);
				lineInfo.setSeparator(separator);
				
				if (recordValidatorComponent.checkLine(lineInfo, totalAmounts, errorsWriter,
						fieldsConfigurations, validators)) {
					amountCorrectLines++;
				} else {
					amountIncorrectLines++;
				}
				numberLine++;
			}
		} catch (IOException e) {
			throw new ValidateFileStructureException("Error en escritura o lectura de archivos");
		} 

		if (isFileEmpty) {
			throw new ValidateFileStructureException("Archivo sin registros");
		}

		validatePayrollFileRs.setTotalAmount(totalAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		validatePayrollFileRs.setAmountCorrectRecords(amountCorrectLines);
		validatePayrollFileRs.setAmountIncorrectRecords(amountIncorrectLines);
		validatePayrollFileRs.setValidationDate(String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli()));

		return isValidStructure;

	}
	
	private String formatFileName(String fileName, String identifier, String extension) {
		return new StringBuilder().append(fileName)
				.append(Constants.SCORE)
				.append(identifier)
				.append(Constants.POINT)
				.append(extension).toString();
	}
	
	private String formatErrorFileName(String fileName, String identifier) {
		return new StringBuilder().append(fileName)
				.append(FILE_NAME_ERROR)
				.append(Constants.SCORE)
				.append(identifier)
				.append(CSV_EXTENSION).toString();
				
	}
	
	/**
	 * Obtiene los validadores de un campo
	 * @return
	 */
	private List<Validator> getFieldValidators() {
		var validators = new ArrayList<Validator>();
		
		validators.add(new RequiredValidator());
		validators.add(new TypeFileValidator());
		validators.add(new MaxLengthValidator());
		
		return validators;
	}

}
