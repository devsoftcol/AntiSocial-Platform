package com.antisocial.dto;

import com.antisocial.validator.PasswordsEqualConstraint;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@PasswordsEqualConstraint()
public class UserSettingsDTO {

    /*
    This DTO exists purely to not display user's password when user enters his/hers settings page.
     */

    public UserSettingsDTO() {
    }

    public UserSettingsDTO(String ssoId, String email, String name, String userBio) {
        this.ssoId = ssoId;
        this.email = email;
        this.name = name;
        this.userBio = userBio;
    }

    private String ssoId;

    @NotNull(message = "Email is required")
    @Size(min= 3, max = 100, message = "Email length min 3, max 100")
    @Email(message = "Not a valid e-mail.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#!.$%^&+=])(?=\\S+$).{8,100}$" , message =
            "Password must contain a lower case character, an upper case character, a number , a special character and must be at least 8 digits long.")
    private String password;

    private String passwordChange;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 26, message = "Full name must be between 3 and 26 digits.")
    private String name;


    @Size(min = 1, max = 240, message = "User bio must be between 1 and 240 digits.")
    private String userBio;

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(String passwordChange) {
        this.passwordChange = passwordChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }


}
