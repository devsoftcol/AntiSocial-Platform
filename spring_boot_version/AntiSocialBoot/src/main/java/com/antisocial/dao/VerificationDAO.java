package com.antisocial.dao;

import com.antisocial.model.User;
import com.antisocial.model.VerificationToken;

public interface VerificationDAO {

    public VerificationToken findByToken(String token);

    public VerificationToken findByUser(User user);

    public void saveVerificationToken(VerificationToken verificationToken);

    public void deleteVerificationToken(VerificationToken verificationToken);


}
