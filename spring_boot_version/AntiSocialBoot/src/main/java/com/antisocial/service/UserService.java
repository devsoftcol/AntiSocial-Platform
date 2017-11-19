package com.antisocial.service;


import com.antisocial.model.Article;
import com.antisocial.model.User;
import com.antisocial.model.VerificationToken;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public List getUsers(boolean lazy);

    public User getUserById(Long ID);

    public User getUserBySsoId(String ssoId, boolean lazy);

    //Email is unique so we do not need a list
    public User getUserByEmail(String email, boolean lazy);

    public List getUserByName(String name, boolean lazy);

    //Get users who contains wildcard string in name or surname
    public List<User> getUserByWildcard(String wildcard);

    public boolean isUserOnline(String ssoId);

    public void kickUser(String ssoId);

    public String getPrincipal();

    public void saveUser(User user);

    public void deleteUser(Long ID);

    public boolean isUserExistSsoId(String ssoId);

    public boolean isUserExistEmail(String email);

    public void addArticle(Article article, Long ID);

    public void uploadStarterFiles(String ssoId) throws IOException;

    public void createVerificationToken(User user, String token);

    public VerificationToken getVerificationToken(String token);

    public void deleteVerificationToken(String token);
}
