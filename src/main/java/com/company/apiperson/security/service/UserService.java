package com.company.apiperson.security.service;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.security.model.entity.Role;
import com.company.apiperson.security.model.entity.User;
import com.company.apiperson.security.model.response.UserResponse;
import com.company.apiperson.security.repository.RoleRepository;
import com.company.apiperson.security.repository.UserRepository;
import com.company.apiperson.service.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Mapper mapper;

    public UserDetails loadUserByUsername(String userName) {
        final User retrievedUser = userRepository.findByName(userName);
        if (retrievedUser == null) {
            throw new UsernameNotFoundException(String.format("User with name: '%s' not found", userName));
        }

        return mapper.buildUserDetails(retrievedUser);
    }

    public UserResponse getUser(long id) {
        if(userRepository.findById(id).isPresent()){
            return mapper.userToUserResponse(userRepository.findById(id).get());
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND, String.format("User with id: '%s' not found", id));
        }
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> mapper.userToUserResponse(u))
                .collect(Collectors.toList());
    }

    public User save(User user, Set<String> rolesRequested) {
        Set<Role> roles = new HashSet<>();
        for (String role: rolesRequested) {
            Role userRole = roleRepository.findByName(role);
            roles.add(userRole);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserResponse saveUser(User user, Set<String> rolesRequested) {
        return mapper.userToUserResponse(save(user, rolesRequested));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}