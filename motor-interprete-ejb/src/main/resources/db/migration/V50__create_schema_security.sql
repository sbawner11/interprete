--SE VERIFICA SI EXISTE ESQUEMA DE SEGURIDAD DE DOMINIO
DO
$do$
BEGIN
   IF NOT EXISTS (select from information_schema.tables t where t.table_schema='security_domain') THEN

	--SE GENERA NUEVO ESQUEMA PARA DATOS DE SEGURIDAD DE DOMINIO	
	CREATE SCHEMA security_domain;
	
	CREATE TABLE security_domain.sys_wildfly_user_roles (
		id_user_roles bigserial NOT NULL,
		username varchar(64) NULL,
		"role" varchar(32) NULL
	);
	
	CREATE TABLE security_domain.sys_wildfly_users (
		username varchar(64) NOT NULL,
		"password" varchar NULL,
		active bool NULL,
		CONSTRAINT sys_wildfly_users_pkey PRIMARY KEY (username)
	);
 END IF;
END
$do$
