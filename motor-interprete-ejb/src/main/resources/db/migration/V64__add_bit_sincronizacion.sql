	              --SE AGREGA NUEVA TABLA PARA REGISTRO DE BITACORAS DE SICRONIZACIONES
CREATE TABLE motor_interprete.bit_sincronizacion (
	id_sincronizacion bigserial NOT NULL,
	fecha_sincronizacion timestamp NOT NULL,
	id_usuario_sinc int4 NOT NULL,
	CONSTRAINT bit_sincronizaciones_pk PRIMARY KEY (id_sincronizacion),
	CONSTRAINT bit_sincronizacioness_usuario_fk FOREIGN KEY (id_usuario_sinc) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx)
);