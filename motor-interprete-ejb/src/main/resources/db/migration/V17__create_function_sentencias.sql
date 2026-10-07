--SE CREA FUNCION PARA EJECUCION DE SENTENCIAS QUE SEAN ENVIADAS POR SQL

CREATE FUNCTION motor_interprete.exec(text) returns text language plpgsql volatile
  AS $f$
    BEGIN
      EXECUTE $1;
      RETURN $1;
    END;
$f$;   