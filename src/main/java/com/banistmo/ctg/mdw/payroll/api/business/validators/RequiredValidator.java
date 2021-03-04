package com.banistmo.ctg.mdw.payroll.api.business.validators;

import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.banistmo.ctg.mdw.payroll.api.util.Utilities;

public class RequiredValidator implements Validator {

	@Override
	public boolean apply(FieldFile fieldFile) {
		return fieldFile.isRequired();
	}

	@Override
	public boolean isValid(FieldFile fieldFile) {
		boolean isValid = true;
		String fieldContent = fieldFile.getContent();
		if (fieldFile.isRequired() && (Utilities.isNullEmpty(fieldContent) || fieldContent.matches("[0.]+"))) {
			isValid = false;
		} 				
		return isValid;
	}

	@Override
	public String getErrorMsg(FieldFile fieldFile) {
		return Constants.ERROR_MSG_REQUIRED_FIELD;
	}

}
