--SE VERIFICA, SI EXISTE TABLA DE ATRIBUTOS DE COMPONENTES, SE ELIMINA PARA SER REGENERADA
DO
$do$
BEGIN
   IF EXISTS (select from information_schema.columns c where c.table_schema = 'motor_interprete' and c.table_name='cat_atributos_componentes') THEN
      DROP TABLE motor_interprete.cat_atributos_componentes;   
   END IF;
   
   --SE GENERA NUEVAMENTE CATALOGO ACTUALIZADO DE ELEMENTOS DE TOKEN
	CREATE TABLE motor_interprete.cat_atributos_componentes (
	    id_atributo_componente BIGSERIAL NOT NULL,                   
	    id_tipo_componente INT8 NOT NULL,  
	    nombre_atributo varchar(20) NOT NULL,
	    orden int4 NOT NULL,
	    CONSTRAINT cat_atributos_componentes_pk PRIMARY KEY (id_atributo_componente)
	);
	
	CREATE INDEX cat_atributos_componentes_idx ON motor_interprete.cat_atributos_componentes USING btree (id_tipo_componente);
	
	ALTER TABLE motor_interprete.cat_atributos_componentes ADD CONSTRAINT cat_atributos_componentes_fk FOREIGN KEY (id_tipo_componente) REFERENCES motor_interprete.cat_tipo_componente(id_tipo_componente);
	
	--INSERTS A LA TABLA cat_atributos_componentes
	-- COMPONENTE: Domicilio (id_tipo_componente = 10)
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Calle', 1);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Número ext', 2);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Número int', 3);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Código Postal', 4);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Estado', 5);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Municipio', 6);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (10, 'Localidad', 7);
	
	-- COMPONENTE: Datos Personales (id_tipo_componente = 11)
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'CURP', 1);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'Nombre', 2);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'Primer Apellido', 3);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'Segundo Apellido', 4);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'Teléfono', 5);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (11, 'Correo Electrónico', 6);
	
	-- COMPONENTE: Datos Personales con Llave (id_tipo_componente = 12)
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'CURP', 1);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Nombre', 2);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Primer Apellido', 3);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Segundo Apellido', 4);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Teléfono', 5);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Correo Electrónico', 6);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Fecha de Nacimiento', 7);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (12, 'Sexo', 8);
	
	-- COMPONENTE: Persona Moral (id_tipo_componente = 14)
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (14, 'RFC', 1);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (14, 'Persona Moral', 2);
	INSERT INTO motor_interprete.cat_atributos_componentes (id_tipo_componente, nombre_atributo, orden) VALUES (14, 'Fecha de Vigencia', 3);
   
	--SE VERIFICA, SI NO EXISTE CAMPO, SE AGREGA A TABLA ELEMENTOS TOKEN
    IF NOT EXISTS (select from information_schema.columns c where c.table_schema = 'motor_interprete' and c.table_name='det_elementos_token' and c.column_name = 'id_atributo_componente') THEN
	
	   	--SE AGREGA COLUMNA PARA EL GUARDADO DEL ID DE ATRIBUTOS DE COMPONENTE
		ALTER TABLE motor_interprete.det_elementos_token
		ADD COLUMN id_atributo_componente BIGINT;
	
 	END IF;
END
$do$