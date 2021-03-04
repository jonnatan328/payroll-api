package com.banistmo.ctg.mdw.payroll.api.dbmodel;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CTG_FIELDS_CONFIGURATION")
public class FieldsConfiguration {
	
	@Id
	private int fieldsConfigurationId;
	private String fieldName;
	private int fieldPosition;
	private String dataType;
	private boolean isMandatory;
	private int maxLength;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BATCH_TYPE")
	private BatchType batchType;
	@OneToMany(mappedBy = "fieldConfiguration")
	private List<FieldValueRange> fieldValueRanges;
}
