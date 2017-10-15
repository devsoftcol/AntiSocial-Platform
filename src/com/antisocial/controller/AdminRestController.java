package com.antisocial.controller;

import com.antisocial.dto.UserDTO;
import com.antisocial.model.User;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    /**
     * Rest EndPoint for admin panel.
     * It only handles user data.
     *
     * @author Ant Kaynak - Github/Exercon
     */

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService){this.userService = userService;}

    @RequestMapping(value = "/search/user/{ssoId}", method = RequestMethod.GET)
    public UserDTO getWildcardUsers(@PathVariable("ssoId") String ssoId){
        User user = userService.getUserBySsoId(ssoId, true);
        if(user == null){ return null; }
        UserDTO userDTO = new UserDTO();
        userDTO.setSsoId(user.getSsoId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setState(user.getState());
        userDTO.setType(user.getUserDetail().getType());
        return userDTO;
    }
}
