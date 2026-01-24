package com.megeMind.journalEntry.Controller;

import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Service.UserService;
import com.megeMind.journalEntry.Service.WeatherService;
import com.megeMind.journalEntry.response.WeatherResponse;
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
    private final WeatherService weatherService;
    public UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }


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
//For using as api call
    @GetMapping("/me/{city}")
    public ResponseEntity<?>provideUser(@PathVariable String city){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        WeatherResponse response = weatherService.getWeather(city);
        String greeting = "";
        if(response!=null){
            greeting = "Weather Feels like"+response.getCurrent().getFeelslike();
        }
        else{
            return   ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok( "Hi "+user +" "+greeting );

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
