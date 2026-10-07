--Se amplia la longitud del campo descripcion_elemento a 200 caracteres 
ALTER TABLE motor_interprete.det_elementos_menu
  ALTER COLUMN descripcion_elemento TYPE VARCHAR(200),
    ALTER COLUMN descripcion_elemento SET NOT NULL;