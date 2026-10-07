--Se modifica campo de correo para que no sea obligatorio

ALTER TABLE motor_interprete.usuario ALTER COLUMN correo DROP NOT NULL;