--Se agregan campos para manejo de Resolución Positiva y Negativa en trámites
ALTER TABLE motor_interprete.det_gestion_usuario ADD habilita_resolucion bool DEFAULT false NOT null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD perfil_supervisor_resolucion bool DEFAULT false NOT null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD perfil_operador_resolucion bool DEFAULT false NOT null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD resolucion_positiva_obligatoria bool DEFAULT false NOT null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD resolucion_negativa_obligatoria bool DEFAULT false NOT null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD correo_resolucion_positiva text null;
ALTER TABLE motor_interprete.det_gestion_usuario ADD correo_resolucion_negativa text null;