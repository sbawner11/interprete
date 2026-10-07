--ACTUALIZAR URL PARA SOLO INDICAR ID DE SISTEMA 
DO
$do$
BEGIN
   IF EXISTS (select from motor_interprete.det_valores_frontendbase) then
      update motor_interprete.det_valores_frontendbase set url_header = 'https://frontendbase.cdmx.gob.mx/public/header.xhtml?idSistema=16',
      url_footer = 'https://frontendbase.cdmx.gob.mx/public/footer.xhtml?idSistema=16', 
      url_menu = 'https://frontendbase.cdmx.gob.mx/public/menu.xhtml?idSistema=16';   
   END IF;
END
$do$