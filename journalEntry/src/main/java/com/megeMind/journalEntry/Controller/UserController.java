package com.megeMind.journalEntry.Controller;

import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUser(){
//        List<User> users = userService.getAllUser();
//        return ResponseEntity.ok(users);
//    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String dbUsername = auth.getName();
         if(!dbUsername.equals(username)){
             return  ResponseEntity.badRequest().build();
         }
        User dbUser = userService.getUser(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));
        return ResponseEntity.ok(dbUser);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateUser( @RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User updateUser=userService.updateUser(username,user);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser(auth.getName());
        return ResponseEntity.noContent().build();
    }
}
