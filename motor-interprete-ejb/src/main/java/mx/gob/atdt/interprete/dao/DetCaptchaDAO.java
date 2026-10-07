package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetCaptchaDTO;
import mx.gob.atdt.interprete.model.DetCaptcha;
import mx.gob.atdt.interprete.model.Proyecto;


@Stateless
@LocalBean
public class DetCaptchaDAO extends IBaseService<DetCaptchaDTO, Long> {

	@Override
	public DetCaptchaDTO buscarPorId(Long id) {
		return null;
	}	
	
	@Override
	public void actualizar(DetCaptchaDTO e) {
		DetCaptcha detCaptcha = new DetCaptcha();
		detCaptcha.setIdDetalleCaptcha(e.getIdDetalleCaptcha());
		detCaptcha.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		detCaptcha.setLlavePublica(e.getLlavePublica());
		detCaptcha.setLlavePrivada(e.getLlavePrivada());
		detCaptcha.setFechaCreacion(e.getFechaCreacion());
		detCaptcha.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		detCaptcha.setActivo(e.isActivo());
		detCaptcha.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(detCaptcha);		
	}

}