package com.banistmo.ctg.mdw.payroll.api.util;

import java.util.UUID;

public final class Utilities {

    private Utilities() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Valida si un String es nulo o vacio
     * 
     * @param valor
     *            String
     * @return <code>true</code> si es nulo o vacio
     */
    public static boolean isNullEmpty(String value) {
        return value == null || value.isBlank();
    }

   
    /**
     * Convierte un String UTF-8 a ISO-8859-1
     * 
     * @param uft8String
     *            String UFT-8
     * @return String en ISO-8859-1
     */
    public static String utf8ToIso(String uft8String) {
        return uft8String
            .replace("Ã¡", "&#225;")
            .replace("Ã�", "&#193;")
            .replace("Ã©", "&#233;")
            .replace("Ã‰", "&#201;")
            .replace("Ã­", "&#237;")
            .replace("Ã�", "&#205;")
            .replace("Ã³", "&#243;")
            .replace("Ã“", "&#211;")
            .replace("Ãº", "&#250;")
            .replace("Ãš", "&#218;")
            .replace("Ã±", "&#241;")
            .replace("Ã‘", "&#209;");
    }
    
    public static String generateUuid() {
    	return UUID.randomUUID().toString();
    }
}
