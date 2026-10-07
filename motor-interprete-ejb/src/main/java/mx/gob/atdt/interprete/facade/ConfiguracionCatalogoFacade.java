package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dao.ConfiguracionCatalogoDAO;
import mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO;

@Stateless
@LocalBean
public class ConfiguracionCatalogoFacade {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionCatalogoFacade.class);

    @Inject
    private ConfiguracionCatalogoDAO configuracionCatalogoDAO;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarConfiguracionCatalogo(ConfiguracionCatalogoDTO configuracionCatalogo) {
        try {
            
        	// Actualizar la configuración principal
            configuracionCatalogoDAO.actualizar(configuracionCatalogo);
            
            // Actualizar descripción personalizada si existe
            configuracionCatalogoDAO.actualizarDescripcionPersonalizadaSiExiste(
                configuracionCatalogo.getIdProyecto(),
                configuracionCatalogo.getIdCatalogo(),
                configuracionCatalogo.getIdOpcionCatalogo()
            );
            
        } catch (Exception e) {
            LOGGER.error("Error en transacción ID: {}", 
                        configuracionCatalogo.getIdConfiguracionCatalogo(), e);
            throw e;
        }
    }
}