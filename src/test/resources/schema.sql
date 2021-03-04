CREATE SCHEMA IF NOT EXISTS CTG_BLE_PAYROLL_DB;

CREATE TABLE IF NOT EXISTS CTG_BLE_PAYROLL_DB.CTG_BATCH_TYPE (
    BATCH_TYPE_ID NUMBER(8,2) NOT NULL,
    BATCH_TYPE_DESCRIPTION VARCHAR(50) NOT NULL,
    CONSTRAINT CTG_BATCH_TYPE_PK PRIMARY KEY (BATCH_TYPE_ID)
);

CREATE TABLE IF NOT EXISTS CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION (
    FIELDS_CONFIGURATION_ID NUMBER(8,2) NOT NULL,
    FIELD_NAME VARCHAR(80) NOT NULL,
    FIELD_POSITION NUMBER(8,2) NOT NULL,
    DATA_TYPE VARCHAR(30) NOT NULL,
    IS_MANDATORY CHAR(1) NOT NULL,
    MAX_LENGTH NUMBER(8,2) NOT NULL,
    BATCH_TYPE NUMBER(8,2) NOT NULL,
    CONSTRAINT CTG_FIELDS_CONFIGURATION_PK PRIMARY KEY (FIELDS_CONFIGURATION_ID),
    CONSTRAINT BATCH_TYPE_FK FOREIGN KEY (BATCH_TYPE) REFERENCES CTG_BATCH_TYPE(BATCH_TYPE_ID)
);

CREATE TABLE IF NOT EXISTS CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE (
    FIELD_VALUE_RANGE_ID NUMBER(8,2) NOT NULL,
    FIELD_VALUE VARCHAR(50) NOT NULL,
    FIELD_CONFIGURATION NUMBER(8,2) NOT NULL,
    FIELD_VALUE_DESCRIPTION VARCHAR(50) NOT NULL,
    CONSTRAINT CTG_FIELD_VALUE_RANGE_PK PRIMARY KEY (FIELD_VALUE_RANGE_ID),
    CONSTRAINT CTG_FIELD_VALUE_RANGE_FK FOREIGN KEY (FIELD_CONFIGURATION) REFERENCES CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID)
);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_BATCH_TYPE(BATCH_TYPE_ID, BATCH_TYPE_DESCRIPTION) VALUES(1, 'PLANILLA');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_BATCH_TYPE(BATCH_TYPE_ID, BATCH_TYPE_DESCRIPTION) VALUES(2, 'ACH Y BANISTMO');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_BATCH_TYPE(BATCH_TYPE_ID, BATCH_TYPE_DESCRIPTION) VALUES(3, 'TRANSFERENCIAS INTERNACIONALES');

-- Planilla
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(1, 'Número de Cuenta Beneficiario', 1, 'NUMERIC', 1, 17, 1);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(2, 'Monto de Transacción', 2, 'DECIMAL', 1, 12, 1);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(3, 'Descripción de la Transacción', 3, 'ALPHANUMERIC', 0, 30, 1);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(4, 'Nombre de Beneficiario', 4, 'ALPHANUMERIC', 1, 40, 1);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(5, 'Email del Beneficiario', 5, 'EMAIL', 0, 50, 1);


-- ACH
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(6, 'Referencia de Pago', 1, 'ALPHANUMERIC', 1, 18, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(7, 'Nombre de Beneficiario', 2, 'ALPHANUMERIC', 1, 40, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(8, 'Número de Cuenta Beneficiario', 3, 'NUMERIC', 1, 17, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(9, 'Banco Beneficiario', 4, 'NUMERIC', 1, 8, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(10, 'Monto de Transacción', 5, 'DECIMAL', 1, 10, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(11, 'Producto Beneficiario', 6, 'RANGE', 1, 2, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(12, 'Descripción de la Transacción', 7, 'ALPHANUMERIC', 1, 40, 2);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(13, 'Email del Beneficiario', 8, 'EMAIL', 0, 50, 2);


--Transferencias internacionales
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(14, 'Monto de Transacción', 1, 'DECIMAL', 1, 14, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(15, 'Número de Cuenta Beneficiario', 2, 'ALPHANUMERIC', 1, 34, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(16, 'Nombre de Beneficiario', 3, 'ALPHANUMERIC', 1, 35, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(17, 'Domicilio 1', 4, 'ALPHANUMERIC', 1, 35, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(18, 'Domicilio 2', 5, 'ALPHANUMERIC', 0, 35, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(19, 'Domicilio 3', 6, 'ALPHANUMERIC', 0, 35, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(20, 'Selector de Swift o ABA Banco Beneficiario', 7, 'ALPHANUMERIC', 1, 1, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(21, 'Código de Banco Beneficiario', 8, 'ALPHANUMERIC', 1, 50, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(22, 'Selector de Swift o ABA Banco Intermediario', 9, 'RANGE', 0, 1, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(23, 'Banco Intermediario', 10, 'ALPHANUMERIC', 0, 50, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(24, 'Comisión OUR o SHA', 11, 'RANGE', 1, 3, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(25, 'Detalle del Pago', 12, 'ALPHANUMERIC', 0, 140, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(26, 'Descripción débito', 13, 'ALPHANUMERIC', 1, 40, 3);

INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELDS_CONFIGURATION(FIELDS_CONFIGURATION_ID, FIELD_NAME, FIELD_POSITION, DATA_TYPE, IS_MANDATORY, MAX_LENGTH, BATCH_TYPE)
VALUES(27, 'Email del Beneficiario', 14, 'ALPHANUMERIC', 0, 50, 3);


--Producto beneficiario
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(1, '22', 11, 'Corriente');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(2, '32', 11, 'Ahorros');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(3, '52', 11, 'Préstamo');

--Selector swift o ABA
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(4, 'A', 20, 'Swift');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(5, 'B', 20, 'ABA');

--Comisión OUR o SHA
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(6, 'OUR', 24, 'OUR');
INSERT INTO CTG_BLE_PAYROLL_DB.CTG_FIELD_VALUE_RANGE(FIELD_VALUE_RANGE_ID, FIELD_VALUE, FIELD_CONFIGURATION, FIELD_VALUE_DESCRIPTION) VALUES(7, 'SHA', 24, 'SHA');

COMMIT;
