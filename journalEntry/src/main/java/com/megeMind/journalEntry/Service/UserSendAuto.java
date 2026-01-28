package com.megeMind.journalEntry.Service;

import com.megeMind.journalEntry.Entity.JournalEntry;
import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Repository.UserRepositoryImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSendAuto {
    private UserRepositoryImpl userRepositoryImpl;
    private EmailService emailService;
    private SentimentAnaysisService sentimentAnaysisService;
    public UserSendAuto(UserRepositoryImpl userRepositoryImpl, EmailService emailService,SentimentAnaysisService sentimentAnaysisService) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.emailService = emailService;
        this.sentimentAnaysisService = sentimentAnaysisService;
    }
    @Scheduled(cron = "0 0 9 * * SUN")
    public void sendMailToUser(){
        List<User> users = userRepositoryImpl.findUser();

        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String>filterJournal = journalEntries.stream().filter(x->x.getDate().isAfter(ChronoLocalDate.from(LocalDateTime.now().minusDays(7)))).map(JournalEntry::getContent).toList();
            String contents = String.join(" ",filterJournal);
            String sentiment = sentimentAnaysisService.getSentiment(contents);
            emailService.sendEmail(user.getEmail(),"Sentiment for Last 7 days",sentiment);
        }

    }
}
