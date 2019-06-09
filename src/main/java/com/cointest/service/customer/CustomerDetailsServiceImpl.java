//package com.cointest.service.customer;
//
//import com.cointest.model.Role;
//import com.cointest.model.User;
//import com.cointest.model.customer.Customer;
//import com.cointest.repo.CustomerRepository;
//import com.cointest.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class CustomerDetailsServiceImpl implements UserDetailsService{
//    @Autowired
//    private CustomerRepository userRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer user = userRepository.findByEmail(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority("User"));
//      //  }
//
//        return new org.springframework.security.core.userdetails.User("m", "m", grantedAuthorities);
//    }
//}
