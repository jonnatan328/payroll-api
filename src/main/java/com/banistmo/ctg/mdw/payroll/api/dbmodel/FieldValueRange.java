package com.banistmo.ctg.mdw.payroll.api.dbmodel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CTG_FIELD_VALUE_RANGE")
public class FieldValueRange {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fieldValueRangeId;
	private String fieldValue;
	private String fieldValueDescription;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_CONFIGURATION")
	private BatchType fieldConfiguration;
}
