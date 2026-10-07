-- SE AGREGA COLUMNA PARA LA NUEVA SECCION DE CONFIGURACIÓN DE CATÁLOGOS.
ALTER TABLE motor_interprete.proyecto
ADD COLUMN habilita_configuracion_catalogos bool NOT NULL DEFAULT false;