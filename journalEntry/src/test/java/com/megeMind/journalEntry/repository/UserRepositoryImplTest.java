package com.megeMind.journalEntry.repository;

import com.megeMind.journalEntry.Entity.User;

import com.megeMind.journalEntry.Repository.UserRepositoryImpl;
import com.megeMind.journalEntry.Service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @MockitoBean
    private WeatherService weatherService;
    @Test
    public void getUsers(){
        List<User>users = userRepositoryImpl.findUser();
        System.out.println(users);
    }
}
