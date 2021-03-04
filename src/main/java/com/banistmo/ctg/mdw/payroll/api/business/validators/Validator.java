package com.banistmo.ctg.mdw.payroll.api.business.validators;

import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;

public interface Validator {
	
	/**
	 * Valida que el campo requiera la validaci�n
	 * @param fieldFile
	 * @return
	 */
	public boolean apply(FieldFile fieldFile);

	/**
	 * Realiza la validaci�n del campo
	 * @param fieldFile
	 * @return
	 */
	public boolean isValid(FieldFile fieldFile);
	
	/**
	 * Obtiene el mensaje de error para la validaci�n
	 * @param label
	 * @return
	 */
	public String getErrorMsg(FieldFile fieldFile);
}
