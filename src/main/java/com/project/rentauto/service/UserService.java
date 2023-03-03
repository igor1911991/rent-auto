package com.project.rentauto.service;

import com.project.rentauto.model.Role;
import com.project.rentauto.model.User;
import com.project.rentauto.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.rentauto.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user, String[] roles) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role> rolesList = roleRepository.findAll();
        List<Role> roless = new ArrayList<>();

        for (Role role : rolesList) {
            for (String st : roles) {
                if (st.equals(role.getRoleName())) {
                    roless.add(role);
                }
            }
        }
        user.setRoles(roless);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("Пользователь %s не найден", username));
        }
        return user;
    }
}