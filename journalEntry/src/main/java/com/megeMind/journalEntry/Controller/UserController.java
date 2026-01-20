package com.megeMind.journalEntry.Controller;

import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User saveUser = userService.saveUser(user);
        return ResponseEntity.created(URI.create("/user/"+saveUser.getUsername())).body(saveUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        User dbUser = userService.getUser(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));
        return ResponseEntity.ok(dbUser);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user){
        User updateUser=userService.updateUser(username,user);
        return ResponseEntity.ok(updateUser);
    }
}
