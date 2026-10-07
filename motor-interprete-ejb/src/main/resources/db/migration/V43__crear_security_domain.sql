--Se agregan tablas para registro del security domain


CREATE TABLE motor_interprete.sys_wildfly_users(username VARCHAR(64) PRIMARY KEY, password VARCHAR, active bool, unique(username));

CREATE TABLE motor_interprete.sys_wildfly_user_roles(id_user_roles bigserial, username VARCHAR(64), role VARCHAR(32));
    
ALTER TABLE motor_interprete.sys_wildfly_user_roles ADD CONSTRAINT sys_wildfly_user_roles_fk FOREIGN KEY (username) REFERENCES motor_interprete.sys_wildfly_users(username);