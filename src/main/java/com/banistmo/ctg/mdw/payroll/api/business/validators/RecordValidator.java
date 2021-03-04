package com.banistmo.ctg.mdw.payroll.api.business.validators;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldsConfiguration;
import com.banistmo.ctg.mdw.payroll.api.domain.LineInfo;

public interface RecordValidator {

	/**
	 * Verifica que una linea tenga la estructura correcta
	 * @param lineNumber
	 * @param lineContent
	 * @param separator
	 * @param totalAmounts
	 * @param errorsWriter
	 * @param fieldsConfigurations
	 * @param validators
	 * @return
	 */
	public boolean checkLine(LineInfo lineInfo, List<BigDecimal> totalAmounts,
			BufferedWriter errorsWriter, List<FieldsConfiguration> fieldsConfigurations, List<Validator> validators) throws IOException;
}
