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

/**
 *
 * @author Olivier
 */
@FacesValidator(value = "inscriptionValidator")
public class InscriptionValidator implements Validator {

    @EJB
    ProfilServiceLocal profilService;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String login = o.toString();

        UIInput uiInputConfirmPassword = (UIInput) uic.getAttributes()
                .get("passWord");
        UIInput uiInputConfirmMail = (UIInput) uic.getAttributes()
                .get("mail");
        UIInput uiInputConfirmNom = (UIInput) uic.getAttributes()
                .get("nom");
        UIInput uiInputConfirmPrenom = (UIInput) uic.getAttributes()
                .get("prenom");
        UIInput uiInputConfirmAge = (UIInput) uic.getAttributes()
                .get("age");
        String passWord = uiInputConfirmPassword.getSubmittedValue()
                .toString();
        String mail = uiInputConfirmMail.getSubmittedValue()
                .toString();
        String nom = uiInputConfirmNom.getSubmittedValue()
                .toString();
        String prenom = uiInputConfirmPrenom.getSubmittedValue()
                .toString();
        String age = uiInputConfirmAge.getSubmittedValue()
                .toString();

        
        
        
        
        if(!login.matches("[a-zA-Z0-9]+")){
            throw new ValidatorException(new FacesMessage(
                    "Le login ne doit être constitué que de lettres et de chiffres"));
        }
        
        if(profilService.exists(login)){
            throw new ValidatorException(new FacesMessage(
                    "Le login existe déjà"));
        }
        
        if (passWord.length() < 3) {
            throw new ValidatorException(new FacesMessage(
                    "Le mot de passe doit faire au moins 4 caractères."));
        }

        if (mail.length() < 1) {
            throw new ValidatorException(new FacesMessage(
                    "Vous devez renseigner un mail."));
        }
        
        if(!age.equals("")){
            if(!age.matches("[0-9]*")){
                throw new ValidatorException(new FacesMessage(
                    "Age non valide : vous devez rentrer un nombre."));
            }
        }
        else{
            throw new ValidatorException(new FacesMessage(
                    "Vous devez rentrer un age."));
        }
        
        if(!mail.matches(".+@.+[\\.].+")){
            throw new ValidatorException(new FacesMessage(
                    "mail non valide : doit matcher ****@****.**"));
        }
        if (nom.length() < 1) {
            throw new ValidatorException(new FacesMessage(
                    "Vous devez renseigner un nom."));
        }
        if (prenom.length() < 1) {
            throw new ValidatorException(new FacesMessage(
                    "Vous devez renseigner un prénom."));
        }

    }

}
