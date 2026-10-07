--Se amplia el tamaño para almacenar el nombre del proyecto
ALTER TABLE motor_interprete.proyecto
ALTER COLUMN nombre_proyecto TYPE VARCHAR(200);