package com.banistmo.ctg.mdw.payroll.api.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.banistmo.ctg.mdw.payroll.api.dbmodel.FieldsConfiguration;
import com.banistmo.ctg.mdw.payroll.api.domain.FieldFile;
import com.banistmo.ctg.mdw.payroll.api.domain.FieldMandatoryValue;


@Component
public class ObjectMapper {

	/**
	 * Mapea la configuración de los campos a un field info
	 * @param fieldsConfigurations
	 * @param position
	 * @param fieldContent
	 * @return
	 */
	public FieldFile getFileInfoFromFieldConfiguration(FieldsConfiguration fieldConfiguration, String fieldContent) {
		
		var fieldInfo = new FieldFile();
		
		fieldInfo.setFileId(fieldConfiguration.getFieldsConfigurationId());
		fieldInfo.setNameField(fieldConfiguration.getFieldName());
		fieldInfo.setMaxLength(fieldConfiguration.getMaxLength());
		fieldInfo.setRequired(fieldConfiguration.isMandatory());
		fieldInfo.setTypeField(fieldConfiguration.getDataType());
		fieldInfo.setContent(fieldContent.trim());

		List<FieldMandatoryValue> fieldsMandatories = fieldConfiguration.getFieldValueRanges().stream()
				.map(fieldValue -> new  FieldMandatoryValue(fieldValue.getFieldValue(), fieldValue.getFieldValueDescription()))
				.collect(Collectors.toList());
		
		fieldInfo.setFieldMandatoryValues(fieldsMandatories);
		
		return fieldInfo;
	}
}
