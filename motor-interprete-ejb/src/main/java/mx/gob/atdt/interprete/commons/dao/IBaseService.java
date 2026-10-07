package mx.gob.atdt.interprete.commons.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class IBaseService<E, ID> {
	
	@Inject
	protected EntityManager em;
	
	public abstract E buscarPorId(ID id);
	public abstract void actualizar(E e);
	
}

