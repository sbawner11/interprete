-- SE AGREGAN COLUMNAS PARA ALMACENAR LA RUTA DE LOS DOCUMENTOS DE RESOLUCION POSTIVA Y NEGATIVA.
ALTER TABLE motor_interprete.tramites
ADD COLUMN ruta_resolucion_positiva varchar(500) NULL,
ADD COLUMN ruta_resolucion_negativa varchar(500) NULL;
