--SE CREA FUNCION PARA OTORGAR PERMISOS A UNA CUENTA DE USUARIO A LAS TABLAS CREADAS DE MANERA DINAMICA EN EL CLIENTE
--LAS TABLAS DINAMICAS TIENEN EL PREFIJO seccion_

CREATE OR REPLACE FUNCTION motor_interprete.otorgar_permisos(cuentausuario text) RETURNS TEXT LANGUAGE plpgsql volatile AS $function$
BEGIN
	PERFORM motor_interprete.exec('GRANT SELECT ON TABLE ' ||  (s.nspname)  || '.' ||
        	(s.relname) || ' TO ' || cuentausuario)
    	FROM (SELECT nspname, relname
             	FROM pg_class c JOIN pg_namespace n ON (c.relnamespace = n.oid) 
            	WHERE nspname like 'motor_interprete' and relname like 'seccion\_%'
                AND  relkind IN ('r') ORDER BY relname) s;
    return ('PERMISOS OTORGADOS');
END;
$function$;