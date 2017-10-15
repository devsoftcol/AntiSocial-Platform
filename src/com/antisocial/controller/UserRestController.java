package com.antisocial.controller;


import com.antisocial.dto.UserDTO;
import com.antisocial.model.User;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

    /**
     * Rest Endpoint for User Controller and Login page.
     * Handles search bar user name search
     * Register page user and e-mail validation.
     *
     *
     * @author Ant Kaynak - Github/Exercon
     */



    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService){this.userService = userService;}


    //Parameter system used because UTF-8 characters do not get evaluated with URLs.
    @RequestMapping(value = "/search/user", method = RequestMethod.GET)
    public List<UserDTO> getWildcardUsers(@RequestParam("wildcard") String wildcard){
        if(wildcard.length() > 240 || wildcard.length() < 3){
                return null;
        }
        List<User> list = userService.getUserByWildcard(wildcard);
        if(list.isEmpty()){ return null; }
        List<UserDTO> dtoList = new ArrayList<>();
        for(User i : list){
            UserDTO userDTO = new UserDTO();
            userDTO.setName(i.getName());
            userDTO.setSsoId(i.getSsoId());
            dtoList.add(userDTO);
        }

        return dtoList;
    }

    @RequestMapping(value = "/user/validSsoId")
    public Boolean doesUserExistSsoId(@RequestParam("ssoId") String ssoId){
        return userService.isUserExistSsoId(ssoId);
    }

    @RequestMapping(value = "/user/validEmail")
    public Boolean doesUserExistEmail(@RequestParam("email") String email){
        return userService.isUserExistEmail(email);
    }


}
