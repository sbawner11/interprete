package mx.gob.atdt.interprete.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("selectBooleanCheckboxValidator")
public class SelectBooleanCheckboxValidator implements Validator<Object> {
		
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value.equals(Boolean.FALSE)) {
			FacesMessage message = new FacesMessage("El campo es obligatorio.", "El campo es obligatorio.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(message);  
        }		
	}
}
