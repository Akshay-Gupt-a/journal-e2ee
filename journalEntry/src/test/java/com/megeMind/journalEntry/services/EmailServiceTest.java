package com.megeMind.journalEntry.services;
import com.megeMind.journalEntry.Repository.JournalEntryRepository;
import com.megeMind.journalEntry.Repository.UserRepository;
import com.megeMind.journalEntry.Service.EmailService;
import com.megeMind.journalEntry.Service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@SpringBootTest
public class EmailServiceTest
{
    @Autowired

    private EmailService emailService;
    @MockitoBean // This creates a fake bean and adds it to the context
    private WeatherService weatherService;
    @MockitoBean
    private com.megeMind.journalEntry.cache.AppCache appCache;

    @MockitoBean
    private com.megeMind.journalEntry.Repository.ConfigJournalAppRepository configJournalAppRepository;

    @Test
    public void sendEmail()
    {
        String to = "akshaygupta88406911@gmail.com";
        String subject = "Email Service";
        String body = "This is a test email service";
        emailService.sendEmail(to, subject, body);
    }
}
