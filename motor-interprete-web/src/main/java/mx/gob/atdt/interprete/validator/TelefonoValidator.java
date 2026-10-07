package mx.gob.atdt.interprete.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.atdt.interprete.commons.utils.Constantes;

@FacesValidator("telefonoValidator")
public class TelefonoValidator implements Validator<Object> {
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if(value.toString().length() != Constantes.LONGITUD_TELEFONO) {
				FacesMessage message = new FacesMessage("La longitud del número de teléfono debe ser de 10 dígitos.", "La longitud del número de teléfono debe ser de 10 dígitos.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				
				throw new ValidatorException(message);	
			} 
        }		
	}

}
