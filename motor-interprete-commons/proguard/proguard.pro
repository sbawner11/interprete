#################################################
# Configuración general
#################################################

-dontshrink
-dontoptimize
-dontobfuscate

#################################################
# Anotaciones
#################################################

# Conserva las anotaciones
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,Exceptions

#################################################
# Reglas de ofuscación
#################################################

# Conserva clases JAXB
-keep @javax.xml.bind.annotation.XmlRootElement class * {
    *;
}

# Conserva clases usadas por Jackson/Gson con anotaciones
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

#################################################
# JAX-RS
#################################################

-keepnames @javax.ws.rs.Path class *
-keepnames @javax.ws.rs.ext.Provider class *

-keepclassmembers class * {
    @javax.ws.rs.Path <methods>;
    @javax.ws.rs.GET <methods>;
    @javax.ws.rs.POST <methods>;
    @javax.ws.rs.PUT <methods>;
    @javax.ws.rs.DELETE <methods>;
    @javax.ws.rs.Produces <methods>;
    @javax.ws.rs.Consumes <methods>;
}

# DTO utilizado como respuesta JSON en sincronización
-keep class mx.gob.atdt.interprete.dto.ResponseDTO { *; }
-keep class mx.gob.atdt.interprete.dto.DatosDocumentoJsonDTO { *; }
-keep class mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO { *; }

-keepnames class mx.gob.atdt.interprete.dto.**
-keepclassmembers class  mx.gob.atdt.interprete.dto.** { public <methods>;	 }

-keepnames class mx.gob.atdt.interprete.commons.dto.**
-keepclassmembers class  mx.gob.atdt.interprete.commons.dto.** { public <methods>; }

-keepnames class mx.gob.atdt.interprete.linea.captura.dto.**
-keepclassmembers class mx.gob.atdt.interprete.linea.captura.dto.** { public <methods>; }

-keep class mx.gob.atdt.interprete.commons.utils.Constantes { *; }
-keepnames class mx.gob.atdt.interprete.commons.utils.Utils
-keepclassmembers class mx.gob.atdt.interprete.commons.utils.Utils { public <methods>; }

-keep class mx.gob.atdt.interprete.commons.enums.** { *; }

-keep class mx.gob.atdt.interprete.exception.** { *; }
