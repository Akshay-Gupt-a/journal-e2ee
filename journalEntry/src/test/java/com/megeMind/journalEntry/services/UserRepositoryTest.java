package com.megeMind.journalEntry.services;

import com.megeMind.journalEntry.Repository.JournalEntryRepository;
import com.megeMind.journalEntry.Repository.UserRepository;

import com.megeMind.journalEntry.Service.WeatherService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class UserRepositoryTest {

    @Autowired // Spring will handle the injection here
    private UserRepository userRepository;
    @MockitoBean // This creates a fake bean and adds it to the context
    private WeatherService weatherService;
    // Remove the constructor entirely

    @MockitoBean
    private JournalEntryRepository journalEntryRepository;

    @Test
    @Disabled
    public void testAdd(){
        assertEquals(4, 2 + 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Radha", "Krishna", "Rahul", "Aman","Suman"})
    public void findUser(String userName){
        assertTrue(userRepository.findByUsername(userName).isPresent(),
                "User " + userName + " should exist in DB");
    }
}