package com.banistmo.ctg.mdw.payroll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banistmo.ctg.mdw.payroll.api.dbmodel.BatchType;

@Repository
public interface FileTypeRepository extends JpaRepository<BatchType, Integer> {
	
	BatchType findByBatchTypeId(int id);

}
