package com.banistmo.ctg.mdw.payroll.api.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.banistmo.ctg.mdw.payroll.api.business.PayrollBusiness;
import com.banistmo.ctg.mdw.payroll.api.domain.Response;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRq;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRs;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
class PayrollRestTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private PayrollBusiness payrollBusiness;
	
	@Test
	void testValidateAchFile() throws Exception {
		var validatePayrollFileRs = new ValidatePayrollFileRs();
		validatePayrollFileRs.setAmountCorrectRecords(3);
		validatePayrollFileRs.setAmountIncorrectRecords(3);
		validatePayrollFileRs.setNameErrorFile("/files/payroll/archivo-lotes-planilla.error-123kl12-123kj-aslsa-90asdf.csv");
		validatePayrollFileRs.setNameValidatedFile("/files/payroll/archivo-lotes-planilla-123kl12-123kj-aslsa-90asdf.csv");
		validatePayrollFileRs.setTotalAmount(BigDecimal.valueOf(100));
		
		var validatePayrollFileRq = new ValidatePayrollFileRq();
		validatePayrollFileRq.setExtension("csv");
		validatePayrollFileRq.setFileName("archivo-lotes-planilla");
		validatePayrollFileRq.setSeparator(",");
		validatePayrollFileRq.setFile("assdffgsdffgsdffgsdffg");
		
		Response<ValidatePayrollFileRs> response = new Response<ValidatePayrollFileRs>();
		response.setStatus(Constants.SUCCESS_CODE);
		response.setMessage(Constants.SUCCESS_MSG);
		response.setBody(validatePayrollFileRs);
		
		when(payrollBusiness.validatePayrollFile(validatePayrollFileRq))
			.thenReturn(response);
		
		String body = (new ObjectMapper()).valueToTree(validatePayrollFileRq).toString();
		 //Execute and Validate
        mvc.perform(post("/api/v1/validation-file")
        		.content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	@Test
	void testHealthcheck() throws Exception {
		
        mvc.perform(get("/api/v1/health"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.status", equalTo(Constants.SUCCESS_CODE)))
        		.andExpect(jsonPath("$.message", equalTo(Constants.SUCCESS_MSG)));
	}

}
