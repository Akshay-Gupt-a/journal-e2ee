package com.megeMind.journalEntry.Service;

import com.megeMind.journalEntry.Entity.JournalEntry;
import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Repository.JournalEntryRepository;

import org.bson.types.ObjectId;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


@Service
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;
    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository=journalEntryRepository;
        this.userService=userService;
    }


    //Utility Method
    //User
    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUser(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
    //Entry exits in user
    private JournalEntry getUserOwnedEntry(User user, ObjectId id) {
        return user.getJournalEntries().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your journal entry"));
    }

    //Get
    public List<JournalEntry> getAllJournalEntries(){
        User user = getLoggedInUser();
            return user.getJournalEntries();
        }

    //Get One JournalEntry
    public JournalEntry getJournalEntryById(ObjectId id){
       User user = getLoggedInUser();
       return getUserOwnedEntry(user,id);
    }



//Post
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry){
        try{
            User user = getLoggedInUser();
            journalEntry.setDate(LocalDate.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            userService.save(user);
            return  savedJournalEntry;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    // Delete
    @Transactional
    public boolean deleteJournalEntryById(ObjectId id){
        User userdb = getLoggedInUser();
        boolean deleted = false;

        JournalEntry entry = getUserOwnedEntry(userdb, id);
       deleted = userdb.getJournalEntries().removeIf((x)->x.getId().equals(id));
        userService.save(userdb);
        journalEntryRepository.delete(entry);
        return deleted;
    }

    // Update
    public JournalEntry update(JournalEntry newjournalEntry , ObjectId myid){
    User user = getLoggedInUser();
        JournalEntry oldJournalEntry = getUserOwnedEntry(user,myid);
        if(newjournalEntry.getTitle()!=null && !newjournalEntry.getTitle().isBlank()){
            oldJournalEntry.setTitle(newjournalEntry.getTitle());
        }
        if(newjournalEntry.getContent()!=null && !newjournalEntry.getContent().isBlank()){
            oldJournalEntry.setContent(newjournalEntry.getContent());
        }

        return journalEntryRepository.save(oldJournalEntry);
    }


}
