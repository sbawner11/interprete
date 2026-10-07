#################################################
# Configuración general
#################################################

-dontshrink
-dontoptimize
-dontobfuscate

#################################################
# Atributos importantes para Java EE / JPA / EJB
#################################################

-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,Exceptions

#################################################
# JPA: NO renombrar entidades
#################################################

-keep @javax.persistence.Entity class * { *; }
-keep @javax.persistence.Embeddable class * { *; }
-keep @javax.persistence.MappedSuperclass class * { *; }

#################################################
# Configuración de los EJB
#################################################

-keepnames @javax.ejb.Stateless class *
-keepclassmembers @javax.ejb.Stateless class * { public <methods>; }
-keepnames @javax.ejb.Singleton class *
-keepclassmembers @javax.ejb.Singleton class * { public <methods>; }
-keepnames @javax.ejb.Stateful class *
-keepclassmembers @javax.ejb.Stateful class * { public <methods>; }
-keepnames @javax.ejb.MessageDriven class *
-keepclassmembers @javax.ejb.MessageDriven class * { public <methods>; }

#################################################
# CDI / javax.inject
#################################################

-keepnames @javax.inject.Named class *
-keepclassmembers @javax.inject.Named class * { public <methods>; }

#################################################
# JAXB / JSON
#################################################

-keep @javax.xml.bind.annotation.XmlRootElement class * { *; }
-keepclassmembers class * {
    @com.fasterxml.jackson.annotation.JsonProperty *;
    @com.fasterxml.jackson.annotation.JsonFormat *;
    @com.fasterxml.jackson.databind.annotation.JsonSerialize *;
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize *;
    @com.google.gson.annotations.SerializedName *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# -keepnames class mx.gob.atdt.interprete.client.**
# -keepclassmembers class mx.gob.atdt.interprete.client.** { public <methods>; }

-keepnames class mx.gob.atdt.interprete.client.OAuth2CdmxClient
-keepclassmembers class mx.gob.atdt.interprete.client.OAuth2CdmxClient { 
	# public <methods>;
	public *** obtenerToken(...);
    public *** obtenerDatosUsuarioPorToken(...);
    public *** obtenerRolesUsuario(...);
    public *** obtenerPersonasMorales(...);
    public *** cerrarSesionConLlaveCDMX(...);
}
-keepnames class mx.gob.atdt.interprete.client.SituacionRolClient
-keepclassmembers class mx.gob.atdt.interprete.client.SituacionRolClient { 
	# public <methods>;
	public *** obtenerUsuariosRol(...); 
}

-keepnames class mx.gob.atdt.interprete.common.infra.Environment
-keepclassmembers class mx.gob.atdt.interprete.common.infra.Environment { 
	# public <methods>;
    public java.lang.String getUrlLoginCdmx();
    public java.lang.String getAppProfile();
    public java.lang.String getPathPlantillasClientePdf();
    public java.lang.String getPathClienteDocumentos();
    public java.lang.String getPathArchivosTemporales();
    public java.lang.String getPathFileServerMotor();
    public java.lang.String getUrlFileServerMotor();
    public java.lang.String getUrlConsultaTramite(); 
}

-keepnames class mx.gob.atdt.interprete.common.util.BeanUtils
-keepclassmembers class mx.gob.atdt.interprete.common.util.BeanUtils { 
	# public <methods>;
	public static boolean isNull(...);
    public static boolean isNotNull(...);
    public static boolean isEmpty(...);
    public static boolean isNotEmpty(...);
    public static boolean isFalse(...);
    public static boolean isDiferent(...);
    public static java.lang.String convertirDateStringDiaMesAnio(...);
    public static java.lang.String convertirDateStringDiaMesAnioHora(...);
    public static java.lang.String convertirDateStringAnioMesDia(...);
    public static java.util.Date sumarDiasFecha(...);
    public static boolean habilitaFirmadoTramites(...);
    public static java.lang.String desencriptarPassword(...);
}
-keepnames class mx.gob.atdt.interprete.common.util.JerseyUtil
-keepclassmembers class mx.gob.atdt.interprete.common.util.JerseyUtil { 
	# public <methods>;
	public static *** getInstance();
    public *** getClientSDKCdmxWithAuth();
    public *** getClientFirmaWithAuth();
    public *** getClientCURPWithAuth();
    public *** getClientLineaCapturaAuth();
    public *** getClientWebhook();
}

-keepnames class mx.gob.atdt.interprete.common.formatos.FormatoRespuestaPDF
-keepclassmembers class mx.gob.atdt.interprete.common.formatos.FormatoRespuestaPDF { public <methods>; }
-keepnames class mx.gob.atdt.interprete.common.formatos.FormatoTramiteFinalizadoPDF
-keepclassmembers class mx.gob.atdt.interprete.common.formatos.FormatoTramiteFinalizadoPDF { 
	# public <methods>;
	public *** generarDocumento(...); 
}

-keepnames class mx.gob.atdt.client.CurpRESTClient
-keepclassmembers class mx.gob.atdt.client.CurpRESTClient { 
	# public <methods>;
	public *** obtenerDatosCurp(...); 
}

-keepnames class mx.gob.atdt.motor.client.SincronizacionProyectoRESTClient
-keepclassmembers class mx.gob.atdt.motor.client.SincronizacionProyectoRESTClient { 
	# public <methods>;
	public *** obtenerEstatusProyecto(...);
    public *** validarSincronizacion(...);
    public *** iniciarSincronizacion(...); 
}
-keepnames class mx.gob.atdt.motor.client.BitacoraProyectoRESTClient
-keepclassmembers class mx.gob.atdt.motor.client.BitacoraProyectoRESTClient { 
	# public <methods>;
	public *** actualizarBitacoraProyecto(...); 
}

-keepnames class mx.gob.atdt.interprete.linea.captura.client.EstatusLineaCapturaClient
-keepclassmembers class mx.gob.atdt.interprete.linea.captura.client.EstatusLineaCapturaClient { 
	# public <methods>;
	public *** consultaEstatusLC(...); 
}

-keepnames class mx.gob.atdt.interprete.lineacaptura.client.GeneraLineaCapturaClient
-keepclassmembers class mx.gob.atdt.interprete.lineacaptura.client.GeneraLineaCapturaClient { public <methods>; }

-keepnames class mx.gob.atdt.firma.client.FirmaRESTClient
-keepclassmembers class mx.gob.atdt.firma.client.FirmaRESTClient { 
	# public <methods>;
	public *** firmarRespuesta(...);
    public *** consultaFirmado(...); 
}

-keepnames class mx.gob.atdt.interprete.dao.**

-keepnames class mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO
-keepnames class mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO
-keepnames class mx.gob.atdt.interprete.estructura.formulario.dao.AsignarPermisosBDDAO

-keepnames class mx.gob.atdt.interprete.formulario.dao.FormularioDAO

-keepnames class mx.gob.atdt.interprete.facade.**

-keepnames class mx.gob.atdt.interprete.**.facade.**

-keepnames class mx.gob.atdt.commons.db.FlywayIntegrator