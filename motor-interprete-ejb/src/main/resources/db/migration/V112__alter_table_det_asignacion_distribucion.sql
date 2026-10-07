--Se amplia la longitud del campo desc_elemento_asignado a 200 caracteres 
ALTER TABLE motor_interprete.det_asignacion_distribucion
  ALTER COLUMN desc_elemento_asignado TYPE VARCHAR(200);