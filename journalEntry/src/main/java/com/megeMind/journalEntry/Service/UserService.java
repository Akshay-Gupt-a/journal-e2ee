package com.megeMind.journalEntry.Service;


import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Get
//    public List<User> getAllUser(){
//        return userRepository.findAll();
//    }

    //Get Particular User
    public Optional<User> getUser(String username) {

        return userRepository.findByUsername(username);
    }

    // Post

    public User saveUser(User user){
        String usernameold = user.getUsername();
        boolean exist = getUser(usernameold).isPresent();
        if(exist){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Username already exists");
        }
            user.setRoles(Arrays.asList("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return  userRepository.save(user);
    }

    // Put or Update Password
    public User updateUser(String usernameold,User user){

        User oldUser = getUser(usernameold).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found"));
        if( !user.getPassword().isEmpty()){

            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(!user.getUsername().isEmpty()  ){
            oldUser.setUsername(user.getUsername());
        }
        return  userRepository.save(oldUser);
    }
    public User save(User user) {

        return userRepository.save(user);
    }

   //Delete
    public void deleteUser(String name) {
        User dbUser = getUser(name).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        userRepository.deleteByUsername(name);
    }






}
