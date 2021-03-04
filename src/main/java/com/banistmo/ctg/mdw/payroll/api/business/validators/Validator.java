package com.banistmo.ctg.mdw.payroll.api.business.validators;

import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;

public interface Validator {
	
	/**
	 * Valida que el campo requiera la validación
	 * @param fieldFile
	 * @return
	 */
	public boolean apply(FieldFile fieldFile);

	/**
	 * Realiza la validación del campo
	 * @param fieldFile
	 * @return
	 */
	public boolean isValid(FieldFile fieldFile);
	
	/**
	 * Obtiene el mensaje de error para la validación
	 * @param label
	 * @return
	 */
	public String getErrorMsg(FieldFile fieldFile);
}
