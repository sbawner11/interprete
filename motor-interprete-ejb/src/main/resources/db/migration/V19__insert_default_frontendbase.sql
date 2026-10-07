--SE INSERTA REGISTRO PARA FRONTENDBASE GENERICO PARA TODOS LOS CLIENTES
--SE VERIFICA, SI EXISTE REGISTRO DE PROYECTO EN LA BD SE AGREGA EL REGISTRO GENERICO
DO
$do$
BEGIN
   IF EXISTS (select from motor_interprete.proyecto) THEN
      INSERT INTO motor_interprete.det_valores_frontendbase 
	  (id_valor,id_proyecto,url_favicon,url_header,url_footer,url_resources_header,fecha_creacion,fecha_ultima_actualizacion,activo,seccion_sincronizada) 
	  VALUES (1,(select p.id_proyecto from motor_interprete.proyecto p),'https://frontendbase.cdmx.gob.mx/file-server/logotipos/gobcdmx/favico_gob_cdmx.ico','https://frontendbase.cdmx.gob.mx/public/header.xhtml?idSistema=16&amp;isLogged=true&amp;language=es','https://frontendbase.cdmx.gob.mx/public/footer.xhtml?idSistema=16&amp;language=es','https://frontendbase.cdmx.gob.mx/file-server/resources/header','2023-10-16 21:00:00.000','2023-10-16 21:00:00.000',true,true);   
   END IF;
END
$do$