--Indica si en el proyecto pueden registrarse trámites nuevos
ALTER TABLE motor_interprete.proyecto ADD habilita_captura_tramites bool default true NOT null ;
