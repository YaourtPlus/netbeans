/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validators;

import Services.composites.ProfilServiceLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Olivier
 */

@FacesValidator(value = "loginValidator")
public class LoginValidator implements Validator{
    
    @EJB
    ProfilServiceLocal profilService;
    
    @Override
    public void validate(FacesContext fc, UIComponent cp, Object value) throws ValidatorException {
        String login = value.toString();

	  UIInput uiInputConfirmPassword = (UIInput) cp.getAttributes()
		.get("passWord");
	  String passWord = uiInputConfirmPassword.getSubmittedValue()
		.toString();
          
          if(profilService.connect(login, passWord) == -1){
              throw new ValidatorException(new FacesMessage(
			"Le login ou le mot de passe est incorrect"));
          }
    }
}
