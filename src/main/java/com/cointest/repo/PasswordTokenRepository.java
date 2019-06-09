package com.cointest.repo;

import com.cointest.model.PasswordResetToken;
import com.cointest.model.customer.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by edward on 6/28/17.
 */
public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findById(Integer id);
//
   PasswordResetToken deleteById(Integer id);
//
    @Query("SELECT o FROM passreset o where o.expiryDate <= ?1")
    List<PasswordResetToken> findByDateBelow(Date now);
//
//    // PasswordResetToken deleteByToken(String token);
//    //void delete(PasswordResetToken passwordResetToken);//call repo directly for now
//    //  List<User> findByUserId(User user);
   PasswordResetToken findByCustomer(Customer customer);

    @Modifying
    @Query("delete from passreset t where t.expiryDate<=?1")
    void deleteAllExpiredSince(Date now);
}
