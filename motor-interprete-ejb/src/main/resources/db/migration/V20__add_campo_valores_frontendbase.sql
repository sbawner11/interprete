--SE AGREGA NUEVA COLUMNA url_menu

ALTER TABLE motor_interprete.det_valores_frontendbase ADD url_menu varchar(150) NOT NULL DEFAULT 'https://frontendbase.cdmx.gob.mx/public/menu.xhtml?idSistema=16&amp;isLogged=false';