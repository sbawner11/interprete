package mx.gob.atdt.interprete.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.atdt.interprete.commons.utils.Constantes;


@FacesValidator("codigoPostalValidator")
public class CodigoPostalValidator implements Validator<Object> {
		
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if(value.toString().length() != Constantes.LONGITUD_CODIGO_POSTAL) {
				FacesMessage message = new FacesMessage("El código postal ingresado no es válido.", "El código postal ingresado no es válido.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				
				throw new ValidatorException(message);	
			} 
        }		
	}
}
