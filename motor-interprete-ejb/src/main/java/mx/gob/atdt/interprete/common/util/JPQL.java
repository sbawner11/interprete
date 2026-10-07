package mx.gob.atdt.interprete.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.utils.Constantes;

public class JPQL implements Serializable {

	private static final long serialVersionUID = 961703738988388954L;
	
	private final  StringBuilder jpqlQuery;
    private final  HashMap<String, Object> params;

    public JPQL() {
        jpqlQuery = new StringBuilder();
        params = new HashMap<>();
    }

    public JPQL(String jpql) {
        this.jpqlQuery = new StringBuilder(jpql);
        params = new HashMap<>();
    }

    public JPQL append(boolean expression, String jpqlFragment, String paramName, Object paramValue) {
        if (expression) {
            append(jpqlFragment, paramName, paramValue);
        }
        return this;
    }

    public JPQL append(String jpqlFragment, String paramName, Object paramValue) {
        jpqlQuery.append(Constantes.ESPACIO + jpqlFragment + Constantes.ESPACIO);
        params.put(paramName, paramValue);
        return this;
    }

    public JPQL append(String string) {
        jpqlQuery.append(string);
        return this;
    }

    public void setParameters(Query query) {
    	for (Map.Entry<String,Object> entry : params.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
    }    

    public static <T> T getSingleResult(List<T> result, T defaultValue) {
        if (result == Constantes.OBJETO_NULO || result.isEmpty()) {
            return defaultValue;
        }
        return result.get(Constantes.FIRST_INDEX_LIST);
    }

    public static <T> T getSingleResult(List<T> result) {
        return getSingleResult(result, null);
    }
    
    @Override
    public String toString() {
        return jpqlQuery.toString();
    }
}