package com.banistmo.ctg.mdw.payroll.api.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.banistmo.ctg.mdw.payroll.api.business.validators.RecordValidator;
import com.banistmo.ctg.mdw.payroll.api.dbmodel.BatchType;
import com.banistmo.ctg.mdw.payroll.api.domain.LineInfo;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRq;
import com.banistmo.ctg.mdw.payroll.api.repository.FileTypeRepository;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;


@SpringBootTest
@AutoConfigureTestDatabase
class PayrollBusinessTest {
	
	private FileTypeRepository fileTypeRepository;
	private RecordValidator recordValidatorComponent;
	
	@BeforeEach
    void init() {
		fileTypeRepository = mock(FileTypeRepository.class);
		recordValidatorComponent = mock(RecordValidator.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testValidatePayrollFileSuccess() throws IOException {
		var lineContent = "108349821,0.02,382748 Pago de prueba ,jose r";
		BufferedWriter errorsWriter = mock(BufferedWriter.class);
		var amountList = mock(ArrayList.class);
		var validatorsList = mock(List.class);
		
		var validatePayrollFileRq = new ValidatePayrollFileRq();
		validatePayrollFileRq.setExtension("csv");
		validatePayrollFileRq.setFileName("archivo-lotes-planilla");
		validatePayrollFileRq.setSeparator(",");
		validatePayrollFileRq.setFile("MTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1"
				+ "ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ"
				+ "1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcH"
				+ "J1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ"
				+ "1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ"
				+ "1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1"
				+ "ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZW"
				+ "JhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJ"
				+ "hICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJh"
				+ "ICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJh"
				+ "ICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhIC"
				+ "xqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxq"
				+ "b3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3"
				+ "NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3Nl"
				+ "IHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIH"
				+ "IsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIs"
				+ "IA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0K"
				+ "MTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA"
				+ "4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4M"
				+ "zQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0K");
		
		BatchType batchType = new BatchType();
		batchType.setFieldsConfigurations(new ArrayList<>());
		
		when(this.fileTypeRepository.findByBatchTypeId(1))
        	.thenReturn(new BatchType());
		
		LineInfo lineInfo = new LineInfo();
		lineInfo.setLineContent(lineContent);
		lineInfo.setLineNumber(1);
		lineInfo.setAmountFieldId(2);
		lineInfo.setSeparator(validatePayrollFileRq.getSeparator());
		
		when(recordValidatorComponent.checkLine(lineInfo, amountList, 
				errorsWriter, batchType.getFieldsConfigurations(), validatorsList))
			.thenReturn(Boolean.TRUE);
		
		PayrollBusiness payrollBusiness = new PayrollBusinessImp(fileTypeRepository, recordValidatorComponent);
		var validatePayrollFileRs = payrollBusiness.validatePayrollFile(validatePayrollFileRq);
		
		assertEquals(Constants.SUCCESS_CODE, validatePayrollFileRs.getStatus());
		
	}
	
	@Test
	void testFileParametersError() throws IOException {
		var validatePayrollFileRq = new ValidatePayrollFileRq();
		validatePayrollFileRq.setExtension("csv");
		validatePayrollFileRq.setFileName("archivo-lotes-planilla");
		validatePayrollFileRq.setSeparator("|");
		validatePayrollFileRq.setFile("MTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1"
				+ "ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ1ZWJhICxqb3NlIHIsIA0KMTA4MzQ5ODIxLDAuMDIsMzgyNzQ4IFBhZ28gZGUgcHJ");
		
		BatchType batchType = new BatchType();
		batchType.setFieldsConfigurations(new ArrayList<>());
		
		var payrollBusiness = new PayrollBusinessImp(fileTypeRepository, recordValidatorComponent);
		
		var validatePayrollFileRs = payrollBusiness.validatePayrollFile(validatePayrollFileRq);
		
		assertEquals(Constants.ERROR_GENERAL_CODE, validatePayrollFileRs.getStatus());
	}
}
