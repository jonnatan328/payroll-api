package com.banistmo.ctg.mdw.payroll.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldMandatoryValue {
	
	private String valueName;
	private String valueDescription;
}
