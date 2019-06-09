package com.cointest.service;

import com.cointest.model.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by edward on 7/1/17.
 */
@Service
public interface PasswordResetService {

    void save(PasswordResetToken passwordResetToken);

    PasswordResetToken findByToken(String token);

    //
    PasswordResetToken deleteById(Integer id);
    PasswordResetToken findById(String id);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryLessThan(Date now);


    List<PasswordResetToken> findAll();
}
