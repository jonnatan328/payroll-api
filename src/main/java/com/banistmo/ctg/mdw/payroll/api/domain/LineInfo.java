package com.banistmo.ctg.mdw.payroll.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineInfo {
	
	private long lineNumber;
	private String lineContent;
	private String separator;
	private int amountFieldId;
}
