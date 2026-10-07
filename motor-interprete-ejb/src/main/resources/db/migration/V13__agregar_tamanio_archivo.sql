--SE AGREGA NUEVA OPCION PARA MAXIMO PERMITIDO PARA CARGA DE ARCHIVOS
INSERT INTO motor_interprete.cat_tamanio_archivos VALUES(6, '10 MB', true);

--AGREGAR NUEVO CAMPO ORDEN PARA MOSTRAR ORDENADOS LOS TAMANIOS DE ARCHIVOS DEL CATALOGO--
ALTER TABLE motor_interprete.cat_tamanio_archivos ADD orden int4 NOT NULL DEFAULT 1;

--SE ORDENAN ELEMENTOS POR PESO.
update motor_interprete.cat_tamanio_archivos set orden = 1 where id_tamanio_archivo = 1;
update motor_interprete.cat_tamanio_archivos set orden = 2 where id_tamanio_archivo = 2;
update motor_interprete.cat_tamanio_archivos set orden = 3 where id_tamanio_archivo = 3;
update motor_interprete.cat_tamanio_archivos set orden = 4 where id_tamanio_archivo = 4;
update motor_interprete.cat_tamanio_archivos set orden = 6 where id_tamanio_archivo = 5;
update motor_interprete.cat_tamanio_archivos set orden = 5 where id_tamanio_archivo = 6;