package com.banistmo.ctg.mdw.payroll.api.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatePayrollFileRs {
	
	private long amountCorrectRecords;
	private long amountIncorrectRecords;
	private BigDecimal totalAmount;
	private String nameValidatedFile;
	private String nameErrorFile;
	private String validationDate;
}
