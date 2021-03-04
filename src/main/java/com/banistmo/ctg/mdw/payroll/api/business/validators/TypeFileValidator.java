package com.banistmo.ctg.mdw.payroll.api.business.validators;

import java.util.List;
import java.util.stream.Collectors;

import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;
import com.banistmo.ctg.mdw.payroll.api.domain.FieldMandatoryValue;
import com.banistmo.ctg.mdw.payroll.api.enums.DataTypesEnum;
import com.banistmo.ctg.mdw.payroll.api.util.Constants;
import com.banistmo.ctg.mdw.payroll.api.util.Utilities;


public class TypeFileValidator implements Validator {
	
	
	@Override
	public boolean apply(FieldFile fieldFile) {
		return !Utilities.isNullEmpty(fieldFile.getTypeField()) && !Utilities.isNullEmpty(fieldFile.getContent());
	}

	@Override
	public boolean isValid(FieldFile fieldFile) {
		DataTypesEnum dataTypesEnum = DataTypesEnum.valueOf(fieldFile.getTypeField());
		String pattern = "";
		boolean isValid = false;
		
		if (dataTypesEnum.equals(DataTypesEnum.RANGE)) {
			isValid = validateMandatoriesValues(fieldFile);
		} else {
			pattern = Constants.getRegexDataTypeMap().get(dataTypesEnum);
			isValid = fieldFile.getContent().matches(pattern);
		} 
		
		return isValid;
	}

	@Override
	public String getErrorMsg(FieldFile fieldFile) {
		DataTypesEnum dataType = DataTypesEnum.valueOf(fieldFile.getTypeField());
		String errorMsg = Constants.getMsgErrorDataTypeMap().get(dataType);
		
		if (dataType.equals(DataTypesEnum.RANGE)) {
			errorMsg = errorMsg.replace("{}", formatRangeMsg(fieldFile.getFieldMandatoryValues()));
		}
		return errorMsg;
	}
	
	/**
	 * Valida que el contenido esté entre los valores configurados del campo
	 * @param fieldFile
	 * @return
	 */
	private boolean validateMandatoriesValues(FieldFile fieldFile) {
		List<FieldMandatoryValue> fieldMandatoryValues = fieldFile.getFieldMandatoryValues();
		boolean isValid = true;
		if (fieldMandatoryValues != null && !fieldMandatoryValues.isEmpty()) {
						List<String> values = fieldMandatoryValues.stream().map(FieldMandatoryValue::getValueName)
								.collect(Collectors.toList());
			if (!values.contains(fieldFile.getContent())) {
				isValid = false;
			}
		}
		
		return isValid;
	}
	
	/**
	 * Formatea el mensaje cuando es de tipo rango
	 * @param fieldMandatoryValues
	 * @return
	 */
	private String formatRangeMsg(List<FieldMandatoryValue> fieldMandatoryValues) {
		var msgBuilded = new StringBuilder();
		fieldMandatoryValues.stream().forEach(fieldMandatory -> 
			msgBuilded.append(fieldMandatory.getValueName())
			.append(Constants.EQUAL)
			.append(fieldMandatory.getValueDescription())
			.append(Constants.COMMA)
			.append(Constants.SPACE)
		);
		
		return msgBuilded.toString().replaceAll(",\\s$", "");
	}
	
}
