/**
 * Carga masiva a la tabla "registros" con trámites existentes, para que posteriormente sean enviados por el schedule de la aplicación al Expediente
 */   
insert into oficinavirtual.registros
select t.id_tramite as id_registro, 
	(select id_funcionalidad_expediente from motor_interprete.det_integracion_expediente_digital) as id_funcionalidad,
	case when t.id_estatus_tramite = 1 then 2 
		 when t.id_estatus_tramite = 2 then 4 
		 when t.id_estatus_tramite = 3 then 1
		 when t.id_estatus_tramite = 4 then 2
		 when t.id_estatus_tramite = 5 then 1
		 when t.id_estatus_tramite = 6 then 6 
		 when t.id_estatus_tramite = 7 then 7
		 when t.id_estatus_tramite = 8 then 8
		 else 3 -- un valor que no se use para identificarlo y corregirlo en caso de que suceda
    end as id_estatus_oficina_virtual,
    t.fecha_creacion as fecha_registro,
    t.id_usuario_llave_cdmx as id_usuario_llave_cdmx,
    u.curp as curp,
    u.correo as correo,
    false as enviado_oficinavirtual,
    false as actualizar_oficinavirtual,
    null as fecha_primer_envio,
    null as fecha_ultima_actualizacion
from motor_interprete.tramites t
	 inner join motor_interprete.usuario u on t.id_usuario_llave_cdmx = u.id_usuario_llave_cdmx
where t.id_usuario_llave_cdmx is not null 
	and not exists( select 1 from oficinavirtual.registros r where r.id_registro = t.id_tramite)