package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CrcCargaDocumentosTipoArchivoDTO;
import mx.gob.atdt.interprete.model.CatTipoArchivo;
import mx.gob.atdt.interprete.model.ComponenteCargaDocumentos;
import mx.gob.atdt.interprete.model.CrcCargaDocumentosTipoArchivo;

@Stateless
@LocalBean
public class CrcCargaDocumentosTipoArchivoDAO extends IBaseService<CrcCargaDocumentosTipoArchivoDTO, Long> {

	@Override
	public CrcCargaDocumentosTipoArchivoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(CrcCargaDocumentosTipoArchivoDTO dto) {

		CrcCargaDocumentosTipoArchivo crcCargaDocumentosTipoArchivo = new CrcCargaDocumentosTipoArchivo();
		crcCargaDocumentosTipoArchivo.setIdCargaDocumentoTipoArchivo(dto.getIdCargaDocumentoTipoArchivo());
		crcCargaDocumentosTipoArchivo.setComponenteCargaDocumentos(em.getReference(ComponenteCargaDocumentos.class,
				dto.getComponenteCargaDocumentosDTO().getIdComponenteCarga()));
		crcCargaDocumentosTipoArchivo.setCatTipoArchivo(
				em.getReference(CatTipoArchivo.class, dto.getCatTipoArchivoDTO().getIdTipoArchivo()));
		crcCargaDocumentosTipoArchivo.setActivo(dto.getActivo());

		em.merge(crcCargaDocumentosTipoArchivo);

	}

	public boolean buscarPorIdComponenteCarga(Long idComponenteCarga) {
		List<CrcCargaDocumentosTipoArchivoDTO> listado = em
				.createNamedQuery("CrcCargaDocumentosTipoArchivo.findByIdComponenteCarga",
						CrcCargaDocumentosTipoArchivoDTO.class)
				.setParameter("idComponenteCarga", idComponenteCarga).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}

}