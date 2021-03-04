package com.banistmo.ctg.mdw.payroll.api.business.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldValueRange;
import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldsConfiguration;
import com.banistmo.ctg.mdw.payroll.api.domain.LineInfo;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
class RecordValidatorImpTest {
	
	@Autowired
	private RecordValidatorImp recordValidatorImp;
	
	static List<Validator> validators;
	static String separator;
	static List<BigDecimal> totalAmounts;
	static BufferedWriter errorsWriter;
	static List<FieldsConfiguration> fieldsConfigurations;
	
	@BeforeAll
	public static void setData() throws IOException {
		separator = ",";
		totalAmounts = new ArrayList<>();
		errorsWriter = new BufferedWriter(new FileWriter("testFiles/error.csv", true));
		
		fieldsConfigurations = new ArrayList<>();
		fieldsConfigurations.add(getFieldsConfigurations(1, "Número de Cuenta Beneficiario", 1, "NUMERIC", true, 17));
		fieldsConfigurations.add(getFieldsConfigurations(2, "Monto de Transacción", 2, "DECIMAL", true, 12));
		fieldsConfigurations.add(getFieldsConfigurations(3, "Descripción de la Transacción", 3, "ALPHANUMERIC", true, 30));
		fieldsConfigurations.add(getFieldsConfigurations(4, "Nombre de Beneficiario", 4, "RANGE", true, 40));
		fieldsConfigurations.add(getFieldsConfigurations(5, "Email del Beneficiario", 5, "EMAIL", true, 50));
		validators = new ArrayList<>();
		validators.add(new RequiredValidator());
		validators.add(new TypeFileValidator());
		validators.add(new MaxLengthValidator());
	}
	
	
	private static FieldsConfiguration getFieldsConfigurations(int id, String name, int position,String dataType, 
			boolean isMandatory, int maxLenth) {
		
		FieldsConfiguration fieldsConfiguration = new FieldsConfiguration();
		fieldsConfiguration.setFieldsConfigurationId(id);
		fieldsConfiguration.setFieldName(name);
		fieldsConfiguration.setFieldPosition(position);
		fieldsConfiguration.setDataType(dataType);
		fieldsConfiguration.setMandatory(isMandatory);
		fieldsConfiguration.setMaxLength(maxLenth);
		fieldsConfiguration.setFieldValueRanges(new ArrayList<>());
		
		return fieldsConfiguration;
	}


	@Test
	void testCorrectLine() throws IOException {
		String lineContent = "108349821,100,382748 Pago de prueba fdd,jose r,jfdkjkf@banis.com";
		LineInfo lineInfo = new LineInfo();
		lineInfo.setLineContent(lineContent);
		lineInfo.setLineNumber(1);
		lineInfo.setAmountFieldId(2);
		lineInfo.setSeparator(separator);
		
		assertTrue(recordValidatorImp.checkLine(lineInfo, totalAmounts, errorsWriter, 
					fieldsConfigurations, validators));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"jkfldjfdsfjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj,,Qq@@@@@ ,@@@@@@,jfdkjkf@banis.com",
			"108349821,100",
			"",
			"108349821,100,Descripcion,,,,"
			})
	void testIncorrectValuesLine(String lineContent) throws IOException {
		LineInfo lineInfo = new LineInfo();
		lineInfo.setLineContent(lineContent);
		lineInfo.setLineNumber(1);
		lineInfo.setAmountFieldId(2);
		lineInfo.setSeparator(separator);
		
		assertFalse(recordValidatorImp.checkLine(lineInfo, totalAmounts, errorsWriter, 
					fieldsConfigurations, validators));
	}
	
	@Test
	void testValidateRangeValueError() throws IOException {
		String lineContent = "108349821,100,382748 Pago de prueba fdd,jose r,jfdkjkf@banis.com";
		FieldsConfiguration fieldsConfiguration = fieldsConfigurations.stream()
				.filter(fieldConf -> fieldConf.getDataType().equals("RANGE"))
				.findAny().get();
		
		var fieldValueRanges = new ArrayList<FieldValueRange>();
		var valueOne = new FieldValueRange();
		valueOne.setFieldValue("SHA");
		var valueTwo = new FieldValueRange();
		valueOne.setFieldValue("ABA");
		fieldValueRanges.add(valueOne);
		fieldValueRanges.add(valueTwo);
		fieldsConfiguration.setFieldValueRanges(fieldValueRanges);
		
		LineInfo lineInfo = new LineInfo();
		lineInfo.setLineContent(lineContent);
		lineInfo.setLineNumber(1);
		lineInfo.setAmountFieldId(2);
		lineInfo.setSeparator(separator);
		
		assertFalse(recordValidatorImp.checkLine(lineInfo, totalAmounts, errorsWriter, 
				fieldsConfigurations, validators));
	}
	
	@AfterAll
	public static void closeConnection() throws IOException {
		errorsWriter.close();

	}

}
