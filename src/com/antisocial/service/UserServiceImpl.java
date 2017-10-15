package com.antisocial.service;


import com.antisocial.dao.UserDAO;
import com.antisocial.dao.VerificationDAO;
import com.antisocial.model.Article;
import com.antisocial.model.User;
import com.antisocial.model.VerificationToken;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;


import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private VerificationDAO verificationDAO;

    @Autowired
    private ServletContext servletContext;


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List getUsers(boolean lazy) {
        return userDAO.getUsers(lazy);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public User getUserById(Long ID) {
        return userDAO.getUserById(ID);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserBySsoId(String ssoId, boolean lazy) {
        return userDAO.getUserBySsoId(ssoId, lazy);
    }


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public User getUserByEmail(String email, boolean lazy) {
        return userDAO.getUserByEmail(email, lazy);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List getUserByName(String name, boolean lazy) {
        return userDAO.getUserByName(name, lazy);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<User> getUserByWildcard(String wildcard) {
        return userDAO.getUserByWildcard(wildcard);
    }

    @Override
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public boolean isUserOnline(String ssoId){
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (final Object principal : allPrincipals) {
            if (principal instanceof UserDetails && ((UserDetails) principal).getUsername().equals(ssoId)) {
                List<SessionInformation> activeUserSessions =
                        sessionRegistry.getAllSessions(principal, false);
                if (!activeUserSessions.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void kickUser(String ssoId){
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (final Object principal : allPrincipals) {
            if (principal instanceof UserDetails && ((UserDetails) principal).getUsername().equals(ssoId)) {
                List<SessionInformation> activeUserSessions =
                        sessionRegistry.getAllSessions(principal, false);
                if (!activeUserSessions.isEmpty()) {
                    for(SessionInformation i : activeUserSessions){
                        i.expireNow();
                    }
                }
            }
        }

    }

    @Override
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        }
        return userName;
    }

    @Override
    @Transactional
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public void saveUser(User user){
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(Long ID) {
        userDAO.deleteUser(ID);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public boolean isUserExistSsoId(String ssoId){
        return getUserBySsoId(ssoId, true) != null;
    }

    //Does user exist
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public boolean isUserExistEmail(String email){
        return getUserByEmail(email, true) != null;
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public void addArticle(Article article, Long ID){
        User user = userDAO.getUserById(ID);
        List<Article> articleList = user.getArticleList();
        if(articleList == null){
            articleList = new ArrayList<>();
        }
        articleList.add(article);
        article.setUserID(user);
    }


    @Override
    @PreAuthorize("isAnonymous()")
    public void uploadStarterFiles(String ssoId) throws IOException {
        String PP_DOMAIN_LOCATION = servletContext.getRealPath("/resources/") + "pic/def.jpg";
        String PP_UPLOAD_LOCATION = servletContext.getRealPath("/resources/") + "pic/" + ssoId + ".jpg";
        File fileToCopy = new File(PP_DOMAIN_LOCATION);
        File newFile = new File(PP_UPLOAD_LOCATION);
        FileUtils.copyFile(fileToCopy, newFile);
        String BG_DOMAIN_LOCATION = servletContext.getRealPath("/resources/") + "pic/bg/def.jpg";
        String BG_UPLOAD_LOCATION = servletContext.getRealPath("/resources/") + "pic/bg/" + ssoId + ".jpg";
        fileToCopy = new File(BG_DOMAIN_LOCATION);
        newFile = new File(BG_UPLOAD_LOCATION);
        FileUtils.copyFile(fileToCopy, newFile);
    }

    @Override
    @Transactional
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token , user);
        verificationDAO.saveVerificationToken(verificationToken);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public VerificationToken getVerificationToken(String token) {
        return verificationDAO.findByToken(token);
    }

    @Override
    @Transactional
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public void deleteVerificationToken(String token) {
        verificationDAO.deleteVerificationToken(verificationDAO.findByToken(token));
    }
}
