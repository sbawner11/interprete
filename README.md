motor-interprete-web: Motor interprete
==============================================================================================
Autor: ADYT

Tecnologías: JSF, CDI, EJB, REST, EAR, Maven.

Lenguaje: Java EE 7, Java SE 8.

Despliegue: JBoss WildFly 26.1.3.Final


Objetivo del Proyecto
-----------
Contar con un interprete para generación de nuevo sitio configurado en el Administrador del Motor Transaccional. 


Requerimientos del Sistema
-------------------

Para la construcción del proyecto se requiere Java 8.0 (Java SDK 1.8) y Maven 3.6.2.

La aplicación que produce este proyecto está diseñada para correr en JBoss WildFly 26.1.3.Final

En la configuración de WildFly se debe agregar:

a) Datasource para la Base de Datos PostgreSQL del proyecto motor-interprete-web.

 
Construir y desplegar la aplicación
-------------------------

NOTA: Los siguientes comandos asumen que se ha configurado Maven correctamente.

1. Asegurarse que se cuenta con Java 1.8 instalado y la variable JAVA_HOME asignada.

2. Abrir una línea de comandos y navegar al directorio raíz de este proyecto.

3. Ejecutar el siguiente comando para compilar y construir el proyecto:

    mvn clean install

a) El proyecto cuenta con distintos perfiles configurados para el empaquetado dependiendo del ambiente que se desea desplegar (dev,qa,prod). Por default el empaquetado se realiza con el perfil "local". Si se requiere realizar el empaquetado con un perfil diferente al default, es necesario indicar el perfil deseado en el comando, por ejemplo empaquetar con el perfil dev:
	
	 mvn clean install -P dev
    
4. Copiar el archivo que se genera: target/motor-interprete-web.ear dentro de la carpeta "deployment" de wildfly.


Configurar JBoss WildFly
---------------------


a) Configurar Datasource


El Datasource se conectará a la BD Postgres, por lo cual primero se debe configurar el Driver (JDBC) como un módulo de Jboss Wildfly y luego configurar la conexión. 

1. Primero, se descarga el JDBC de Postgres en su versión 42.7.4 desde el sitio oficial.
2. En la carpeta de instalación de Wildfly 26.1.3.Final, en la sub-carpeta "modules/system/layers/base/org" se crean las carpetas "/postgresql/main"
3. Dentro de la carpeta /postgresql/main se copia el JAR del JDBC.
4. Dentro de la carpeta /postgresql/main se crea el archivo module.xml con el siguiente contenido:

	<?xml version="1.0" encoding="UTF-8"?>
	<module name="org.postgresql" xmlns="urn:jboss:module:1.5">
	    <resources>
	        <resource-root path="postgresql-42.7.4.jar"/>
	    </resources>
	    <dependencies>
	        <module name="javax.api"/>
	        <module name="javax.transaction.api"/>
	    </dependencies>
	</module>

Luego, para configurar el datasource utilizando el driver, se realizan las siguientes acciones:

1. Se abre el archivo /standalone/configuration/standalone.xml
2. Se busca el subsistema <subsystem xmlns="urn:jboss:domain:datasources:5.0">
3. Dentro de dicho subsistema, existe un elemento <drivers> con la configuración por defecto con el Driver de la base de datos H2, por lo cual falta agregar la configuración del driver para Postgres, por lo tanto se agrega el driver de la siguiente manera:

	<drivers>
        <driver name="h2" module="com.h2database.h2">
            <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
        </driver>
        <driver name="postgresql" module="org.postgresql">
            <driver-class>org.postgresql.Driver</driver-class>
        </driver>
    </drivers>
    

4. Dentro del mismo subsistema, se agrega el datasource de la siguiente manera:

	<datasource jndi-name="java:jboss/datasources/interprete-motor" pool-name="interprete-motor" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
        <!-- Modificar valores -->
        <connection-url>jdbc:postgresql://DOMINIO_O_IP:5432/MIBD</connection-url>
        
        <connection-property name="defaultRowPrefetch">20</connection-property>
        <driver>postgresql</driver>
        <pool>
        	   <!-- Para produccion, modificar valores -->
            <min-pool-size>1</min-pool-size>
            <max-pool-size>10</max-pool-size>
            <prefill>true</prefill>
        </pool>
        
        <!-- Modificar valores -->
        <security>
            <user-name>USUARIO_DE_BD</user-name>
            <password>PASSWORD_DE_BD</password>
        </security>
        <validation>
            <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
            <validate-on-match>false</validate-on-match>
            <background-validation>true</background-validation>
            <background-validation-millis>300000</background-validation-millis>
            <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
        </validation>
        <timeout>
            <blocking-timeout-millis>30000</blocking-timeout-millis>
            <idle-timeout-minutes>15</idle-timeout-minutes>
            <query-timeout>120</query-timeout>
            <allocation-retry>3</allocation-retry>
            <allocation-retry-wait-millis>10000</allocation-retry-wait-millis>
        </timeout>
    </datasource>


b) Configurar Security Domain

El Recurso REST que se publica cuenta con un dominio de seguridad para su acceso. Dicho dominio está definido en el web.xml y el jboss-web.xml. Para su correcto funcionamiento, se debe configurar en JBoss Wildfly lo siguiente:

1. En la BD del sistema se crean las siguientes 2 tablas para guardar los usuarios y roles del Dominio de Seguridad (El proyecto ya tiene incluida esta migración, solo se coloca de manera informativa)
      
        CREATE TABLE motor_interprete.sys_wildfly_users(username VARCHAR(64) PRIMARY KEY, password VARCHAR, active bool, unique(username))
        CREATE TABLE motor_interprete.sys_wildfly_user_roles(id_user_roles bigserial, username VARCHAR(64), role VARCHAR(32))
        
        ALTER TABLE motor_interprete.sys_wildfly_user_roles ADD CONSTRAINT sys_wildfly_user_roles_fk FOREIGN KEY (username) REFERENCES motor_interprete.sys_wildfly_users(username);
       
2. Realizar los siguientes cambios en el archivo standalone.xml:

   2.1. En el subsistema (subsystem xmlns="urn:jboss:domain:ejb3:9.0") agregar en (application-security-domains):

        <application-security-domain name="JBossWS" security-domain="wsSecurityDomain"/>

   Nótese:

   * El nombre del securityDomain debe ser "JBossWS" ya que así se tiene configurado en el código del sistema.


   2.2. En el subsistema (subsystem xmlns="urn:wildfly:elytron:15.1" final-providers="combined-providers" disallowed-providers="OracleUcrypto") agregar en (security-domains):

        <security-domain name="wsSecurityDomain" default-realm="wsJdbcRealm" permission-mapper="default-permission-mapper">
        	<realm name="wsJdbcRealm" role-decoder="groups-to-roles"/>
        </security-domain>


   2.3. En el subsistema (subsystem xmlns="urn:wildfly:elytron:15.1" final-providers="combined-providers" disallowed-providers="OracleUcrypto") agregar en (security-realms):

        <jdbc-realm name="wsJdbcRealm">
        	<principal-query sql="SELECT swu.password,swur.role FROM motor_interprete.sys_wildfly_users swu JOIN motor_interprete.sys_wildfly_user_roles swur ON swu.username=swur.username WHERE swu.active=true AND swu.username=?" data-source="interpreteMotorDS">
             <attribute-mapping>
             	<attribute to="groups" index="2"/>
             </attribute-mapping>
          	<simple-digest-mapper algorithm="simple-digest-sha-512" password-index="1" hash-encoding="hex"/>
        	</principal-query>
        </jdbc-realm>


   2.4. En el subsistema (subsystem xmlns="urn:wildfly:elytron:15.1" final-providers="combined-providers" disallowed-providers="OracleUcrypto") agregar en (http):

        <http-authentication-factory name="wsHttpAuth" security-domain="wsSecurityDomain" http-server-mechanism-factory="global">
        	<mechanism-configuration>
          	<mechanism mechanism-name="BASIC">
             	<mechanism-realm realm-name="wsSecurityDomain"/>
             </mechanism>
          </mechanism-configuration>
        </http-authentication-factory>


   2.5. En el subsistema (subsystem xmlns="urn:jboss:domain:undertow:12.0" default-server="default-server" default-virtual-host="default-host" default-servlet-container="default" default-security-domain="other" statistics-enabled="${wildfly.undertow.statistics-enabled:${wildfly.statistics-enabled:false}}") agregar en (application-security-domains):

        <application-security-domain name="JBossWS" http-authentication-factory="wsHttpAuth"/>


3. En la BD se inserta un usuario y password (hasheado en SHA-512 y su respectivo(s) rol(es):
 
        INSERT into motor_interprete.sys_wildfly_users (username, password, active) values('admin','c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', true);
        
        INSERT into motor_interprete.sys_wildfly_user_roles (username, role) values('admin','consulta');
 
 Con lo anterior, se tendrá configurado un usuario llamado "admin", que tiene una contraseña para pruebas "admin" y su rol es de "consulta".
 NOTA: En un ambiente de producción se deben establecer nombres de usuario para cada sistema que vaya a hacer uso de los WebServices de Motor, así como declarar contraseñas seguras. 


c) Configurar logging para deshabilitar el registro de mensajes hibernate

En el subsistema (urn:jboss:domain:logging:8.0) agregar la siguiente configuracion:

	<!-- Sileciar hibernate especificamente-->
	<logger category="org.hibernate">
		<level name="OFF"/>
	</logger>


d) Habilitar configuración en Wildfly para permitir carga de archivos de hasta 20MB.

En el subsistema (urn:jboss:domain:undertow:12.0) agregar parámetro "max-post-size" en "http-listener name="default"" quedando de la siguiente manera:

	<http-listener name="default" socket-binding="http" max-post-size="20971520" redirect-socket="https" enable-http2="true"/>


Configurar valores para consumir recursos estáticos
---------------------

Configurar las siguientes propiedades en el archivo pom del module ejb, para que pueda consumir los recursos que expone el Sistema Motor Transaccional, los valores dependerán de la configuración que sea realizada en el proyecto Motor transaccional.

        <url.fileserver.motoradmin>https://dev-motortrans-admin.infotec.mx//file-server</url.fileserver.motoradmin>				
        <path.fileserver.motor>/srv/wildfly/proyectos/public</path.fileserver.motor>

Definir el path o directorio para almacenamiento de recursos locales
---------------------

Configurar la siguiente propiedad en el archivo pom del module ejb, en la que la aplicación estará realizando el guardado de recursos, se deberá asegurar que se cuente con los permisos necesarios (lectura y escritura) de dicho directorio.

        <path.documentos.cliente.motor>/srv/cliente_motor/cliente/documentos/</path.documentos.cliente.motor>


Agregar plantillas utilizadas por la aplicación
---------------------

Configurar la siguiente propiedad en el archivo pom del module ejb, en la que la aplicación encontrará las plantillas que son utilizadas en el proyecto, se deberá asegurar que las plantillas sean colocadas al iniciar la aplicación y que se cuente con los permisos necesarios (lectura y escritura) en dicho directorio.

        <path.plantillas.cliente.pdf>/srv/cliente_motor/plantillas/</path.plantillas.cliente.pdf>
        

Configurar valores para consumo de servicio de CURP
---------------------

Colocar los valores con los que la aplicación podrá realizar el consumo del servicio de consulta de CURP, se deberá asegurar que tanto el recurso configurado como credenciales (usuario y password) sean correctas y no tengan problemas de acceso.

    <services.cdmx.curp>https://curp.cdmx.gob.mx/curp/rest/curp/</services.cdmx.curp>
	<services.cdmx.curp.user>usuario_consulta</services.cdmx.curp.user>
	<services.cdmx.curp.password>password_usuario</services.cdmx.curp.password>		
		

Iniciar JBoss WildFly con un perfil Web
---------------------

Después de compilar el proyecto con Java 1.8 y haber copiado el .EAR a la carpeta "deployment" de WildFly, se debe abrir una línea de comandos y ejecutar desde la carpeta /bin:


        Para Linux y MacOs:   JBOSS_HOME/bin/standalone.sh 
        Para Windows: JBOSS_HOME\bin\standalone.bat


Ingresar a la aplicación y hacer uso de sus servicios
---------------------

La aplicación estará ejecutándose en la siguiente URL: http://localhost:8080/
