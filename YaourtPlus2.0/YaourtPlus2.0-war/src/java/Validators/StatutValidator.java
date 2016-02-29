/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validators;

import Services.composites.ProfilServiceLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Olivier
 */
@FacesValidator(value = "statutValidator")
public class StatutValidator implements Validator{
    @EJB
    ProfilServiceLocal profilService;
    
    @Override
    public void validate(FacesContext fc, UIComponent cp, Object value) throws ValidatorException {
        try{
            String statut = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) cp.getAttributes()
                .get("inputFile");
        
        Part p = (Part)uiInputConfirmPassword.getSubmittedValue();
        
        throw new ValidatorException(new FacesMessage(
                    p.toString()));
        /*
        if(statut.equals((""))){
            throw new ValidatorException(new FacesMessage(
                    "Le statut ne peux pas être vide"));
        }
        */
        }catch(Exception e){
            throw new ValidatorException(new FacesMessage(
                    e.toString()));
        }
        
        
    }
}
