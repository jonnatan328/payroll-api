package com.banistmo.ctg.mdw.payroll.api.business.validators;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.tinylog.Logger;

import com.banistmo.ctg.mdw.payroll.api.business.mapper.ObjectMapper;
import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldsConfiguration;
import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;
import com.banistmo.ctg.mdw.payroll.api.domain.LineInfo;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.banistmo.ctg.mdw.payroll.api.util.Utilities;


@Component
public class RecordValidatorImp implements RecordValidator{
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public RecordValidatorImp(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	/**
	 * Método que valida una linea del archivo
	 * @param lineInfo
	 * @param totalAmounts
	 * @param errorsWriter
	 * @param fieldsConfigurations
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean checkLine(LineInfo lineInfo, List<BigDecimal> totalAmounts, BufferedWriter errorsWriter, 
			List<FieldsConfiguration> fieldsConfigurations, List<Validator> validators) throws IOException {
		boolean isCorrectLine = true;
		var lineErrors = new StringBuilder();
		
		if (Utilities.isNullEmpty(lineInfo.getLineContent())) {
			lineErrors.append(Constants.ERROR_MSG_EMPTY_RECORD);
			saveLineErrors(errorsWriter, lineErrors, lineInfo.getLineNumber());
			return false;
		}
		
		String [] fields = lineInfo.getLineContent().split(lineInfo.getSeparator(), -1);
		Logger.info("Cantidad de columnas: " + fields.length);
		if (fields.length > 1) {
			
			FieldFile fieldInfo;
			if (!isValidAmountFields(fields, fieldsConfigurations, lineErrors)) {
				saveLineErrors(errorsWriter, lineErrors, lineInfo.getLineNumber());
				return false;
			}
			
			int position = 1;
			for (String fieldContent : fields) {
				var fieldsConfiguration = getFieldConfiguration(position, fieldsConfigurations);
				fieldInfo = objectMapper.getFileInfoFromFieldConfiguration(fieldsConfiguration, fieldContent);
				
				validateField(fieldInfo, lineErrors, totalAmounts, fieldContent, validators,
						lineInfo.getAmountFieldId());
				position++;
			}
			
			if (lineErrors.length() > 0) {
				isCorrectLine = false;
				saveLineErrors(errorsWriter, lineErrors, lineInfo.getLineNumber());
			}
		} else {
			Logger.info("Fila " + lineInfo.getLineNumber() + "sin separador definido");
			lineErrors.append(Constants.ERROR_MSG_ICORRECT_SEPARATOR);
			saveLineErrors(errorsWriter, lineErrors, lineInfo.getLineNumber());
			isCorrectLine = false;
		}
		return isCorrectLine;
	}
	
	/**
	 * Valida que el campo sea correcto
	 * @param fieldInfo
	 * @param lineErrors
	 * @param totalAmounts
	 * @param fieldContent
	 */
	private void validateField(FieldFile fieldInfo, StringBuilder lineErrors, List<BigDecimal> totalAmounts,
			String fieldContent, List<Validator> validators, int amountFieldId) {
		var isFieldValid = true;
		
		for (Validator validator : validators) {					
			if (validator.apply(fieldInfo) && !validator.isValid(fieldInfo)) {
				isFieldValid = false;
				lineErrors.append(getMessageError(validator, fieldInfo));
			}
		}
		
		if(isFieldValid && fieldInfo.getFileId() == amountFieldId){
			totalAmounts.add(NumberUtils.parseNumber(fieldContent, BigDecimal.class));
		}
	}
	
	/**
	 * Valida la cantidad máxima y minima de campos
	 * @param fields
	 * @param fieldsConfigurations
	 * @param lineErrors
	 * @return
	 */
	private boolean isValidAmountFields(String [] fields, List<FieldsConfiguration> fieldsConfigurations,
			StringBuilder lineErrors) {
		boolean isValid = true;
		long amountFiedsMandatories = fieldsConfigurations.stream()
				.filter(FieldsConfiguration::isMandatory)
				.count();
		
		if (fields.length < amountFiedsMandatories ) {
			lineErrors.append(Constants.ERROR_MSG_MIN_AMOUNT_FIELDS.replace("{}", String.valueOf(amountFiedsMandatories)));
			isValid = false;
		} else if(fields.length > fieldsConfigurations.size()) {
			lineErrors.append(Constants.ERROR_MSG_MAX_AMOUNT_FIELDS.replace("{}", String.valueOf(fieldsConfigurations.size())));
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Obtiene la configuración del campo
	 * @param position
	 * @param fieldsConfigurations
	 * @return
	 */
	private FieldsConfiguration getFieldConfiguration(int position, List<FieldsConfiguration> fieldsConfigurations) {
		return fieldsConfigurations.stream()
				.filter(configuration -> position == configuration.getFieldPosition())
				.findAny()
				.orElseThrow();
	}
	
	/**
	 * Guarda en un archivo una línea de error
	 * @param errorsWriter
	 * @param lineErrors
	 * @throws IOException
	 */
	private void saveLineErrors(BufferedWriter errorsWriter, StringBuilder lineErrors, long lineNumber) throws IOException {
		Logger.info(lineErrors.toString());
		lineErrors.insert(0, Constants.ERROR_LABEL_LINE_NUMBER.replace("#", String.valueOf(lineNumber)));
		var lineError = lineErrors.toString().replaceAll(",\\s$", "");
		errorsWriter.append(lineError + Constants.LINE_BREAK);
	}
	/**
	 * Obtiene el mensaje formateado para el archivo de errores
	 * @param validator
	 * @param fileInfo
	 * @return
	 */
	private String getMessageError(Validator validator, FieldFile fileInfo) {
		return new StringBuilder()
				.append(Constants.QUOTATION_MARK).append(fileInfo.getNameField())
				.append(validator.getErrorMsg(fileInfo))
				.append(Constants.QUOTATION_MARK)
				.append(Constants.COMMA)
				.append(Constants.SPACE)
				.toString();

	}

	
	
}
