package com.megeMind.journalEntry.Controller;


import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Repository.UserRepository;
import com.megeMind.journalEntry.Service.UserService;
import com.megeMind.journalEntry.cache.AppCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user/user-admin")
public class UserAdminController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AppCache appCache;
    public  UserAdminController(UserRepository userRepository, UserService userService, AppCache appCache) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.appCache = appCache;

    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<User> createAdmitUser(@RequestBody User user){
        user.setRoles(Arrays.asList("ADMIN"));
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }
    @GetMapping("app-cache-clear")
    public void clearCache(){
        appCache.init();
    }

}
