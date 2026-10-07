/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mx.gob.atdt.interprete.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import mx.gob.atdt.interprete.commons.utils.Constantes;





/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class WebResources {
	
	private static final String SCROLL_MESSAGES = "window.scrollTo({ top: 0, behavior: 'smooth' });";

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    public static String getBundleMsg(final String clave) {
    	return ResourceBundle.getBundle("messages.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(clave);
    }
    
    public static void addSuccessMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, getBundleMsg(claveMensaje), null));
    }
    
    public static void addValidationMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, getBundleMsg(claveMensaje), null));
    }
    
	public static void addValidationMessage(String claveMensaje, Object[] parametros, boolean... keepJsfMessage) {
        PrimeFaces.current().executeScript(SCROLL_MESSAGES);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(
				keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE
						: keepJsfMessage[0]);

		String mensaje = MessageFormat.format(getBundleMsg(claveMensaje), parametros);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null));
    }

    public static void addErrorMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, getBundleMsg(claveMensaje), null));
    }
    
    // Se integran métodos para los mensajes mostrados en las vistas sin el texto que indica el tipo de mensaje.
    public static void successMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, getBundleMsg(claveMensaje), null));
    }
    
    public static void validationMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, getBundleMsg(claveMensaje), null));
    }
    
    public static void errorMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	PrimeFaces.current().executeScript(SCROLL_MESSAGES);
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, getBundleMsg(claveMensaje), null));
    }
}