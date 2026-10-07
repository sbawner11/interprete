-- ******************************************************************************************************************************
-- ******************************************************************************************************************************
-- ************************************** O F I C I N A     V I R T U A L *******************************************************
-- ******************************************************************************************************************************
-- ******************************************************************************************************************************

CREATE SCHEMA oficinavirtual; 

CREATE TABLE oficinavirtual.registros (
	id_registro bigserial NOT NULL, -- adecuar confore cada sistema
	id_funcionalidad int4 NOT NULL,
	id_estatus_oficina_virtual int2 NOT NULL,
	fecha_registro timestamp NOT NULL,
	id_usuario_llave_cdmx int8 NULL,
	curp varchar NULL,
	correo varchar NULL,
	enviado_oficinavirtual bool NOT NULL,
	actualizar_oficinavirtual bool NOT NULL,
	fecha_primer_envio timestamp NULL,
	fecha_ultima_actualizacion timestamp NULL,
	codigo_resultado_oficina_virtual int4 NULL,
	mensaje_resultado_oficina_virtual varchar NULL,
	CONSTRAINT registros_pk PRIMARY KEY (id_registro)
);

ALTER TABLE oficinavirtual.registros ALTER COLUMN id_registro DROP DEFAULT; -- Para que no use el id_registro como una sequence ya que lo que queremos es que sea el id del registro en este sistema

CREATE INDEX registros_actualizar_oficinavirtual_idx ON oficinavirtual.registros (actualizar_oficinavirtual);
CREATE INDEX registros_enviado_oficinavirtual_idx ON oficinavirtual.registros (enviado_oficinavirtual);

CREATE TABLE oficinavirtual.bit_errores_registros (
	id_error bigserial NOT NULL,
	id_registro bigserial NOT NULL, 
	message_text varchar NULL,
	pg_exception_detail varchar NULL,
	pg_exception_context varchar NULL,
	fecha_registro timestamp NOT NULL,
	CONSTRAINT bit_errores_registros_pk PRIMARY KEY (id_error)
);

CREATE TABLE oficinavirtual.bit_errores_envio_registros (
	id_error bigserial NOT NULL,
	id_registro bigserial NOT NULL, 
	message_text varchar NULL,
	pg_exception_detail varchar NULL,
	pg_exception_context varchar NULL,
	fecha_registro timestamp NOT NULL,
	CONSTRAINT bit_errores_envio_registros_pk PRIMARY KEY (id_error)
);


-- Función que usa el trigger que revisa nuevos inserts de solicitudes
CREATE or REPLACE FUNCTION oficinavirtual.inserta_oficina_virtual()
RETURNS TRIGGER 
LANGUAGE plpgsql 
as $funcion$ 
DECLARE 

    _id_funcionalidad int2 := 0; -- Se obtendrá mediante un query para obtener el id_funcionalidad registrado en la configuración del motor
    _id_estatus_oficina_virtual int2 :=  1; -- ID en el catálogo de estatus de la OV (id = 1 significa RECIBIDO en la OV)

    _correo_usuario varchar := null;
    _curp_usuario varchar := null;
   
    _excepcion_mensaje text := null;
    _excepcion_detalle text := null;
    _excepcion_contexto text := null;
begin

	select into _id_funcionalidad
	       id_funcionalidad_expediente 
	from motor_interprete.det_integracion_expediente_digital;

	if (_id_funcionalidad is null or _id_funcionalidad = 0) then 
		 INSERT INTO oficinavirtual.bit_errores_registros
			(id_registro, message_text, pg_exception_detail, pg_exception_context, fecha_registro)
			VALUES(-1, 'No se encuentra la configuración de Expediente Digital', 'No existe un id_funcionalidad para este proyecto', null, now()); 
		return null;
	end if;


	if new.id_estatus_tramite = 1 then -- Cuando es "En captura"
	    _id_estatus_oficina_virtual := 2; -- En proceso
	elsif new.id_estatus_tramite = 2 then -- Cuando es "Pendiente de Pago"
	    _id_estatus_oficina_virtual := 4; -- En espera de pago 
    elsif new.id_estatus_tramite = 3 then -- Cuando es "Enviado"
	    _id_estatus_oficina_virtual := 1; -- Recibido
	elsif new.id_estatus_tramite = 4 then -- Cuando es "En corrección"
	    _id_estatus_oficina_virtual := 2; -- En proceso
	elsif new.id_estatus_tramite = 5 then -- Cuando es "Corregido"
	    _id_estatus_oficina_virtual := 1; -- Recibido
	elsif new.id_estatus_tramite = 6 then -- Cuando es "Rechazado"
	    _id_estatus_oficina_virtual := 6; -- Rechazado
	elsif new.id_estatus_tramite = 7 then -- Cuando es "Aceptado"
	    _id_estatus_oficina_virtual := 7; -- Aprobado
	elsif new.id_estatus_tramite = 8 then -- Cuando es "Revisado"
	    _id_estatus_oficina_virtual := 8; -- Finalizado
	end if;

	select into _correo_usuario, _curp_usuario 
		  u.correo, u.curp
	from motor_interprete.usuario u
	where u.id_usuario_llave_cdmx = new.id_usuario_llave_cdmx;

	INSERT INTO oficinavirtual.registros -- personalizar por sistema
		  (id_registro, id_funcionalidad, id_estatus_oficina_virtual, fecha_registro, id_usuario_llave_cdmx, curp, correo, enviado_oficinavirtual, actualizar_oficinavirtual, fecha_primer_envio, fecha_ultima_actualizacion)
    VALUES(new.id_tramite, _id_funcionalidad, _id_estatus_oficina_virtual, new.fecha_creacion, new.id_usuario_llave_cdmx, _curp_usuario, _correo_usuario, false, false, null, null);
   
	return null;  -- result is ignored since this is an AFTER trigger
EXCEPTION 
	when others then
	  	GET STACKED DIAGNOSTICS _excepcion_mensaje = MESSAGE_TEXT,
	                          _excepcion_detalle = PG_EXCEPTION_DETAIL,
	                          _excepcion_contexto := PG_EXCEPTION_CONTEXT;	  	
		
        INSERT INTO oficinavirtual.bit_errores_registros
			(id_registro, message_text, pg_exception_detail, pg_exception_context, fecha_registro)
			VALUES(new.id_tramite, _excepcion_mensaje, _excepcion_detalle, _excepcion_contexto, now()); 	                         
	                         
	  	raise NOTICE 'Ocurrio una excepcion al insertar un registro en la tabla del expediente: %', _excepcion_mensaje || ', Contexto: ' ||_excepcion_contexto; 
		return null;	-- Si marca cualquier error que no cancele la transacción de negocio del sistema, solo que la bitacoree para analizar que pasó y volver a reprocesar esos registros.			
end;
$funcion$;



-- TRIGGER Insert
CREATE TRIGGER detecta_insert_solicitud
	AFTER INSERT ON motor_interprete.tramites  --Personalizar por sistema
    FOR EACH row
    when (new.id_usuario_llave_cdmx is not null) -- No se manda si no tiene id_usuario_llave_cdmx ya que significa que el proyecto en el motor no pide autenticación con Llave, por ende no tiene sentido enviar esos registros al expediente digital
    EXECUTE procedure oficinavirtual.inserta_oficina_virtual();
   

-- Función que usa el trigger que revisa updates de estatus de solicitudes
CREATE or REPLACE FUNCTION oficinavirtual.actualiza_oficina_virtual()
RETURNS TRIGGER 
LANGUAGE plpgsql 
as $funcion$ 
DECLARE 
    
    _id_estatus_nuevo int2 :=  3; -- Esto no debería de pasar. Se le pone cualquier otro estatus que no se use para poder identificarlo y corregirlo
    
    _excepcion_mensaje text := null;
    _excepcion_detalle text := null;
    _excepcion_contexto text := null;
begin
	
	
	if new.id_estatus_tramite = 1 then -- Cuando es "En captura"
	    _id_estatus_nuevo := 2; -- En proceso
    elsif new.id_estatus_tramite = 2 then -- Cuando es "Pendiente de pago"
	    _id_estatus_nuevo := 4; -- En espera de pago
	elsif new.id_estatus_tramite = 3 then -- Cuando es "Enviado"
	    _id_estatus_nuevo := 1; -- Recibido
	elsif new.id_estatus_tramite = 4 then -- Cuando es "En corrección"
	    _id_estatus_nuevo := 2; -- En proceso
	elsif new.id_estatus_tramite = 5 then -- Cuando es "Corregido"
	    _id_estatus_nuevo := 1; -- Recibido
	elsif new.id_estatus_tramite = 6 then -- Cuando es "Rechazado"
	    _id_estatus_nuevo := 6; -- Rechazado
	elsif new.id_estatus_tramite = 7 then -- Cuando es "Aceptado"
	    _id_estatus_nuevo := 7; -- Aprobado
	elsif new.id_estatus_tramite = 8 then -- Cuando es "Revisado"
	    _id_estatus_nuevo := 8; -- Finalizado
	end if;


	update oficinavirtual.registros 
	set id_estatus_oficina_virtual = _id_estatus_nuevo, 
	    fecha_ultima_actualizacion = now(),
	    actualizar_oficinavirtual = true
	where id_registro = old.id_tramite; -- personalizar por sistema
	
	return null;  -- result is ignored since this is an AFTER trigger
	
EXCEPTION 
	when others then
	  	GET STACKED DIAGNOSTICS _excepcion_mensaje = MESSAGE_TEXT,
	                          _excepcion_detalle = PG_EXCEPTION_DETAIL,
	                          _excepcion_contexto := PG_EXCEPTION_CONTEXT;	  	
		
        INSERT INTO oficinavirtual.bit_errores_registros
			(id_registro, message_text, pg_exception_detail, pg_exception_context, fecha_registro)
			VALUES(old.id_tramite, _excepcion_mensaje, _excepcion_detalle, _excepcion_contexto, now()); 	                         
	                         
	  	raise NOTICE 'Ocurrio una excepcion al actualizar un registro en la tabla del expediente: %', _excepcion_mensaje || ', Contexto: ' ||_excepcion_contexto; 
		return null;	-- Si marca cualquier error que no cancele la transacción de negocio del sistema, solo que la bitacoree para analizar que pasó y volver a reprocesar esos registros.	
end;
$funcion$; 
   

-- TRIGGER update   
CREATE TRIGGER detecta_update_estatus_solicitud
	AFTER UPDATE of id_estatus_tramite ON motor_interprete.tramites
    FOR EACH row
    when (new.id_usuario_llave_cdmx is not null ) -- No se manda si no tiene id_usuario_llave_cdmx ya que significa que el proyecto en el motor no pide autenticación con Llave, por ende no tiene sentido enviar esos registros al expediente digital
    EXECUTE procedure oficinavirtual.actualiza_oficina_virtual();
   



