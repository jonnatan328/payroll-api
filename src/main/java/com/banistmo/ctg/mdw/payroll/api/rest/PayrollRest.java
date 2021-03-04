package com.banistmo.ctg.mdw.payroll.api.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.tinylog.Logger;

import com.banistmo.ctg.mdw.payroll.api.business.PayrollBusiness;
import com.banistmo.ctg.mdw.payroll.api.domain.Response;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRq;
import com.banistmo.ctg.mdw.payroll.api.domain.ValidatePayrollFileRs;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Payroll", produces = "application/json")
@CrossOrigin
public class PayrollRest {
  
	private final PayrollBusiness payrollBusiness;
	
	@Autowired
    public PayrollRest(PayrollBusiness payrollBusiness) {
        this.payrollBusiness = payrollBusiness;
    }

    @ResponseBody
    @GetMapping("/health")
    public Response<Body> health() {
    	Logger.info("Ingreso Ok");
        return new Response<>(HttpStatus.OK, "OK");
    } 

    @ResponseBody
    @ApiOperation(value = "Validar la estructura de un archivo de planilla")
    @PostMapping("/validation-file")
    public Response<ValidatePayrollFileRs> validatePayrollFile(@RequestBody ValidatePayrollFileRq body) {

        return payrollBusiness.validatePayrollFile(body);
    }
}