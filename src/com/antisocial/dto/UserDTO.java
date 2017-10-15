package com.antisocial.dto;


import com.antisocial.validator.PasswordsEqualConstraint;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordsEqualConstraint()
public class UserDTO {

    public UserDTO() {
    }


    /**
     There is no need for UserDetailDTO because
     User and UserDetail Entities are bounded together with
     OneToOne annotation. So one DTO can do the job
     for both of the entities.
      */



    //ssoId is username.
    @NotNull(message = "Username is required")
    @Size(min = 6, max = 14, message = "Username must be between 6 and 14 digits.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only characters allowed in the username.")
    private String ssoId;

    @NotNull(message = "Email is required")
    @Size(min= 3, max = 100, message = "Email length min 3, max 100")
    @Email(message = "Not a valid e-mail.")
    private String email;

    //Regex
    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#!.$%^&+=])(?=\\S+$).{8,100}$" , message =
            "Password must contain a lower case character, an upper case character, a number , a special character and must be at least 8 digits long.")
    private String password;

    @NotNull(message = "Password repetition required")
    private String passwordRepeat;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 26, message = "Full name must be between 3 and 26 digits.")
    private String name;

    private String state;

    private String type;

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

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "ssoId='" + ssoId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", userBio='" + userBio + '\'' +
                '}';
    }
}
