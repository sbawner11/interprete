--Se agrega campo para habilitar la revocación de trámites tipo aviso
ALTER TABLE motor_interprete.proyecto ADD habilita_revocacion_aviso boolean NOT NULL default false;