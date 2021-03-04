package com.banistmo.ctg.mdw.payroll.api.util;

import java.util.Map;

import com.banistmo.ctg.mdw.payroll.api.enums.DataTypesEnum;

public class Constants {

	private Constants() {}
	
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String PIPE = "|";
	public static final String QUOTATION_MARK = "\"";
	public static final String SLASH = "/";
	public static final String SCORE = "-";
	public static final String POINT = ".";
	public static final String SPACE = " ";
	public static final String EQUAL = "=";
	public static final String LINE_BREAK = "\r\n";
	
	public static final int ERROR_GENERAL_CODE = 400;
	public static final int SUCCESS_CODE = 200;
	
	public static final String ERROR_MSG = "ERROR";
	public static final String SUCCESS_MSG = "OK";
	
	public static final String ERROR_LABEL_LINE_NUMBER = "Línea #: ";
	public static final String ERROR_MSG_REQUIRED_FIELD = ", es un valor requerido";
	public static final String ERROR_MSG_MAX_LENGTH_FIELD = ", la longitud del campo no es válida";
	public static final String ERROR_MSG_MIN_AMOUNT_FIELDS = "NO debe tener menos de {} campos por registro, valores opcionales espacios en blanco";
	public static final String ERROR_MSG_MAX_AMOUNT_FIELDS = "NO debe tener mas de {} campos por registro";
	public static final String ERROR_MSG_ICORRECT_SEPARATOR = "El separador utilizado no es permitido";
	public static final String ERROR_MSG_EMPTY_RECORD = "No debe contener líneas en blanco";
	
	// Mensajes de errores en validaci�n de tipo de dato
    private static final Map<DataTypesEnum, String> MSG_ERROR_DATA_TYPE_MAP;
    static {
    	MSG_ERROR_DATA_TYPE_MAP = Map.of(DataTypesEnum.ALPHANUMERIC, ", debe ser un valor alfanumérico sin caracteres especiales solamente espacios en blanco",
    			DataTypesEnum.NUMERIC, ", es un valor requerido numérico",
    			DataTypesEnum.DECIMAL, ", es un valor decimal, es permitido (2 decimales)",
    			DataTypesEnum.EMAIL, ", debe ser un valor con formato de email válido",
    			DataTypesEnum.RANGE, ", valores permitidos: {}");
    }
    
    private static final Map<DataTypesEnum, String> REGEX_DATA_TYPE_MAP;
    static {
    	REGEX_DATA_TYPE_MAP = Map.of(DataTypesEnum.ALPHANUMERIC, "[A-Za-z0-9\\s]+",
    			DataTypesEnum.NUMERIC, "[0-9]+",
    			DataTypesEnum.DECIMAL, "\\d+(\\.\\d{2})?",
    			DataTypesEnum.EMAIL, "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+\n" +
    			           ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*");
    }
    
    public static Map<DataTypesEnum, String> getMsgErrorDataTypeMap() {
        return MSG_ERROR_DATA_TYPE_MAP;
    }
    
    public static Map<DataTypesEnum, String> getRegexDataTypeMap() {
        return REGEX_DATA_TYPE_MAP;
    }
}
