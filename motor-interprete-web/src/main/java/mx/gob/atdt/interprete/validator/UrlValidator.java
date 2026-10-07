package mx.gob.atdt.interprete.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.atdt.interprete.commons.utils.Constantes;


@FacesValidator("urlValidator")
public class UrlValidator implements Validator<String> {

	private static final Pattern URL_PATTERN = Pattern.compile(Constantes.EXPRESION_URL_PATTERN);
	
	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		if(value != null) {
			Matcher matcher = URL_PATTERN.matcher(value.toString());
	        if(!matcher.matches()) {
	        	FacesMessage message = new FacesMessage("URL inválida", "URL inválida");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);	        	
	        }
		}		
	}
}
