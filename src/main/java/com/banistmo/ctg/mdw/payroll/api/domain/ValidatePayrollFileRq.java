package com.banistmo.ctg.mdw.payroll.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatePayrollFileRq {
	
	private String file;
	private String fileName;
	private String extension;
	private String separator;
}
