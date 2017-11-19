package com.antisocial.validator;

import com.antisocial.dto.UserDTO;
import com.antisocial.dto.UserSettingsDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    /**
     * UserDTO and UserSettingsDTO Password Validator.
     * Checks if the given password repeat matches with the given password.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {

    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext constraintValidatorContext) {
        if( candidate instanceof UserDTO){
            UserDTO user = (UserDTO) candidate;
            return user.getPassword() != null && user.getPasswordRepeat() != null && user.getPassword().equals(user.getPasswordRepeat());
        }
        if( candidate instanceof UserSettingsDTO) {
            UserSettingsDTO user = (UserSettingsDTO) candidate;
            /*
            if(user.getPassword() == null && user.getPasswordChange() == null){
                return true;
            }
            else if(user.getPassword() == null || user.getPasswordChange() == null){
                return false;
            }else{
                return user.getPassword().equals(user.getPasswordChange());
            }
             */
            return user.getPassword() == null && user.getPasswordChange() == null || user.getPassword() != null && user.getPasswordChange() != null && user.getPassword().equals(user.getPasswordChange());
        }
        System.out.println("No instanceof error. Check line 24 : PasswordsEqualConstraintValidator.java ");
        return false;
    }

}