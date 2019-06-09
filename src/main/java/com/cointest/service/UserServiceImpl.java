package com.cointest.service;

import com.cointest.model.User;
import com.cointest.repo.RoleRepository;
import com.cointest.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String email) {return userRepository.findByEmail(email);}


	@Override
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return findByUsername(user.getEmail())!=null;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		  //return userRepository.findAll();
		return null;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

}
