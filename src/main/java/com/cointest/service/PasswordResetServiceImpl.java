package com.cointest.service;

import com.cointest.model.PasswordResetToken;
import com.cointest.repo.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by edward on 7/1/17.
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

//    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Override
    public void save(PasswordResetToken passwordResetToken) {
        passwordTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken deleteById(Integer id) {
        return null;
    }

    @Override
    public PasswordResetToken findById(String id) {
        return null;
    }


//    @Override
//    public PasswordResetToken findByUserId(Integer id) {
//        return passwordTokenRepository.findById(id);
//    }


    @Override
    public Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now) {
        return null;
    }

    @Override
    public void deleteByExpiryLessThan(Date now) {

    }

    @Override
    public List<PasswordResetToken> findAll() {
        return null;
    }
}
