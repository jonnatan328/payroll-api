package com.banistmo.ctg.mdw.payroll.api.dbmodel;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CTG_BATCH_TYPE")
public class BatchType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int batchTypeId;
	private String batchTypeDescription;
	@OneToMany(mappedBy = "batchType")
	private List<FieldsConfiguration> fieldsConfigurations;
}
