package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.model.Usuario;

@Stateless
@LocalBean
public class UsuarioDAO extends IBaseService<UsuarioDTO, Long> {

	@Override
	public UsuarioDTO buscarPorId(Long id) {
		List<UsuarioDTO> lstEncontrados = em.createNamedQuery("Usuario.findById", UsuarioDTO.class)
				.setParameter("idUsuario", id)
				.getResultList();
		return lstEncontrados != null && !lstEncontrados.isEmpty() ? lstEncontrados.get(0) : null;
	}

	@Override
	public void actualizar(UsuarioDTO e) {
		Usuario usuario = em.getReference(Usuario.class, e.getIdUsuarioLlaveCdmx());

		usuario.setNombre(e.getNombre());
		usuario.setPrimerApellido(e.getPrimerApellido());
		usuario.setSegundoApellido(
				e.getSegundoApellido() != null && !e.getSegundoApellido().isEmpty() ? e.getSegundoApellido() : null);
		usuario.setCurp(e.getCurp());
		usuario.setTelefono(e.getTelefono() != null && !e.getTelefono().isEmpty() ? e.getTelefono() : null);
		usuario.setCorreo(e.getCorreo() != null ? e.getCorreo() : null);
		usuario.setSexo(e.getSexo());

		em.merge(usuario);
	}

	public void guardar(UsuarioDTO e) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuarioLlaveCdmx(e.getIdUsuarioLlaveCdmx());
		usuario.setNombre(e.getNombre());
		usuario.setPrimerApellido(e.getPrimerApellido());
		usuario.setSegundoApellido(
				e.getSegundoApellido() != null && !e.getSegundoApellido().isEmpty() ? e.getSegundoApellido() : null);
		usuario.setCurp(e.getCurp());
		usuario.setTelefono(e.getTelefono() != null && !e.getTelefono().isEmpty() ? e.getTelefono() : null);
		usuario.setCorreo(e.getCorreo() != null ? e.getCorreo() : null);
		usuario.setSexo(e.getSexo());

		em.persist(usuario);
	}

}
