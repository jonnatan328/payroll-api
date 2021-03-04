package com.banistmo.ctg.mdw.payroll.api.business.validators;

import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.banistmo.ctg.mdw.payroll.api.util.Utilities;

public class MaxLengthValidator implements Validator {

	@Override
	public boolean apply(FieldFile fieldFile) {
		return fieldFile.getMaxLength() > 0 && !Utilities.isNullEmpty(fieldFile.getContent());
	}

	@Override
	public boolean isValid(FieldFile fieldFile) {
		return fieldFile.getContent().length() <= fieldFile.getMaxLength();
	}

	@Override
	public String getErrorMsg(FieldFile fieldFile) {
		return Constants.ERROR_MSG_MAX_LENGTH_FIELD;
	}

}
