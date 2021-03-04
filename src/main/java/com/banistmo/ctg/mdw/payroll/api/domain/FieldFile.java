package com.banistmo.ctg.mdw.payroll.api.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldFile {
	
	private int fileId;
	private String nameField;
	private String typeField;
	private boolean required;
	private int maxLength;
	private String content;
	private List<FieldMandatoryValue> fieldMandatoryValues;
}
