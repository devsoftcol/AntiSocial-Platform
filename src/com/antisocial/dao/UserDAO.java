package com.antisocial.dao;


import com.antisocial.model.Article;
import com.antisocial.model.State;
import com.antisocial.model.User;
import org.springframework.web.util.NestedServletException;

import java.util.HashSet;
import java.util.List;

public interface UserDAO {

    public List getUsers(boolean lazy);

    public User getUserById(Long ID);

    public User getUserBySsoId(String ssoId, boolean lazy);

    //Email is unique so we do not need a list
    public User getUserByEmail(String email, boolean lazy);

    public List getUserByName(String name, boolean lazy);

    //Get users who contains wildcard string in their name
    public List<User> getUserByWildcard(String wildcard);

    public void saveUser(User user);

    public void deleteUser(Long ID);




}
