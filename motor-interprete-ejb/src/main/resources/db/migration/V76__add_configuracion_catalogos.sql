DO
$do$
BEGIN
   -- Verificar si existe la tabla catalogos y eliminarla si existe
   IF EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'motor_interprete' AND table_name = 'catalogos') THEN
      DROP TABLE motor_interprete.catalogos;   
   END IF;
   
   -- Crear tabla de catálogos
   CREATE TABLE motor_interprete.catalogos (
      id_catalogo int4 NOT NULL,
      nombre varchar(60) NOT NULL,
      comentarios varchar(60) NULL,
      CONSTRAINT catalogos_pk PRIMARY KEY (id_catalogo)
   );

   -- Insertar datos en la tabla catalogos
   INSERT INTO motor_interprete.catalogos (id_catalogo, nombre, comentarios) VALUES(1, 'Estatus de trámites', 'Se muestran estatus de trámites/avisos');

   -- Verificar si existe la tabla opciones_catalogo y eliminarla si existe
   IF EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'motor_interprete' AND table_name = 'opciones_catalogo') THEN
      DROP TABLE motor_interprete.opciones_catalogo;   
   END IF;
   
   -- Crear tabla de opciones de catálogo
   CREATE TABLE motor_interprete.opciones_catalogo (
      id_opcion_catalogo int4 NOT NULL,
      id_catalogo int4 NOT NULL,
      descripcion_opcion varchar(60) NULL,
      CONSTRAINT opciones_catalogo_pk PRIMARY KEY (id_opcion_catalogo)
   );

   -- Agregar llave foránea a opciones_catalogo
   ALTER TABLE motor_interprete.opciones_catalogo ADD CONSTRAINT opciones_catalogo_catalogos_fk FOREIGN KEY (id_catalogo) REFERENCES motor_interprete.catalogos(id_catalogo);

   -- Insertar datos en la tabla opciones_catalogo
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(1, 1, 'En captura / En captura');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(2, 1, 'Pendiente de pago / Pendiente de pago');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(3, 1, 'Enviado / Enviado');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(4, 1, 'En corrección / En corrección');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(5, 1, 'Corregido / Corregido');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(6, 1, 'Rechazado / Revocado');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(7, 1, 'Aceptado / Presentado');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(8, 1, 'Revisado / Revisado');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(9, 1, 'Conclusión positiva / N/A');
   INSERT INTO motor_interprete.opciones_catalogo (id_opcion_catalogo, id_catalogo, descripcion_opcion) VALUES(10, 1, 'Conclusión negativa / Conclusión negativa');

   -- Verificar si existe la tabla configuracion_catalogo y eliminarla si existe
   IF EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'motor_interprete' AND table_name = 'configuracion_catalogo') THEN
      DROP TABLE motor_interprete.configuracion_catalogo;   
   END IF;
   
   -- Crear tabla de configuración de catálogo
   CREATE TABLE motor_interprete.configuracion_catalogo (
      id_configuracion_catalogo bigserial,
      id_proyecto bigint NOT NULL,
      id_catalogo int4 NOT NULL,
      id_opcion_catalogo int4 NOT NULL,
      descripcion_usuario VARCHAR(60) NOT NULL,
      id_usuario_cambio int4 NOT NULL,
      fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      activo bool NOT NULL DEFAULT true,
      seccion_sincronizada bool NOT NULL DEFAULT false,
      CONSTRAINT configuracion_catalogo_pk PRIMARY KEY (id_configuracion_catalogo)
   );

   -- Agregar llaves foráneas a configuracion_catalogo
   ALTER TABLE motor_interprete.configuracion_catalogo ADD CONSTRAINT configuracion_catalogo_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);
   ALTER TABLE motor_interprete.configuracion_catalogo ADD CONSTRAINT configuracion_catalogo_catalogo_fk FOREIGN KEY (id_catalogo) REFERENCES motor_interprete.catalogos(id_catalogo);
   ALTER TABLE motor_interprete.configuracion_catalogo ADD CONSTRAINT configuracion_catalogo_opciones_catalogo_fk FOREIGN KEY (id_opcion_catalogo) REFERENCES motor_interprete.opciones_catalogo(id_opcion_catalogo);
   
   -- Se agrega columna en tabla cat_estatus_tramite para proyectar si existe una descripcion personalizada.
   ALTER TABLE motor_interprete.cat_estatus_tramite ADD descripcion_personalizada varchar(60) NULL;

END
$do$;