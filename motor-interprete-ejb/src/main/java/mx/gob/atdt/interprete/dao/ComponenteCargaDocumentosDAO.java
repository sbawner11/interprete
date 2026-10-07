package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.model.CatTamanioArchivos;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteCargaDocumentos;

@Stateless
@LocalBean
public class ComponenteCargaDocumentosDAO extends IBaseService<ComponenteCargaDocumentosDTO, Long> {

	@Override
	public ComponenteCargaDocumentosDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean buscarPorIdComponente(long idComponente) {
		List<ComponenteCargaDocumentos> listado = em
				.createNamedQuery("ComponenteCargaDocumentos.findByIdComponente", ComponenteCargaDocumentos.class)
				.setParameter("idComponente", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	public boolean buscarPorIdComponenteCarga(long idComponente) {
		List<ComponenteCargaDocumentos> listado = em
				.createNamedQuery("ComponenteCargaDocumentos.findByIdComponenteCarga", ComponenteCargaDocumentos.class)
				.setParameter("idComponenteCarga", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteCargaDocumentosDTO dto) {
		ComponenteCargaDocumentos componente = new ComponenteCargaDocumentos();
		componente.setIdComponenteCarga(dto.getIdComponenteCarga());
		componente.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		componente.setCatTamanioArchivos(em.getReference(CatTamanioArchivos.class, dto.getCatTamanioArchivosDTO().getIdTamanioArchivo()));
		componente.setDocumentoUnico(dto.isDocumentoUnico());
		em.merge(componente);
	}
	
}
