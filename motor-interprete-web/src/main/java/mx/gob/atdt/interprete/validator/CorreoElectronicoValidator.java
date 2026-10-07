package mx.gob.atdt.interprete.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class CorreoElectronicoValidator implements Validator<Object> {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if (!compararPatron(value.toString())) {
				FacesMessage message = new FacesMessage("El email ingresado no es correcto.",
						"El email ingresado no es correcto.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			}
		}
	}

	private boolean compararPatron(String value) {
		String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
