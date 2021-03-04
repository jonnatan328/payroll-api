package com.banistmo.ctg.mdw.payroll.api.business;

import com.banistmo.ctg.mdw.payroll.api.domain.Response;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRq;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRs;

public interface PayrollBusiness {

	/**
	 * Valida estructura de archivo de lotes de planilla
	 * @param validatePayrollFileRq
	 * @return
	 */
    Response<ValidatePayrollFileRs> validatePayrollFile(ValidatePayrollFileRq validatePayrollFileRq);

}
