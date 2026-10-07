
--SE ELIMINA TABLA PARA REGISTRO DE VALORES FRONTENDBASE.
drop table IF EXISTS motor_interprete.det_valores_frontendbase CASCADE;

--SE ELIMINA TABLA DE INTEGRACION CON EXPEDIENTE
drop table IF EXISTS motor_interprete.det_integracion_expediente_digital CASCADE;

--SE ELIMINA CAMPO EN PROYECTO
ALTER TABLE motor_interprete.proyecto DROP COLUMN habilita_integracion_expediente_digital;

--SE ELIMINA ESQUEMA DE OFICINA VIRTUAL
DROP SCHEMA oficinavirtual CASCADE;
