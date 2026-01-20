package com.megeMind.journalEntry.Service;

import com.megeMind.journalEntry.Entity.JournalEntry;
import com.megeMind.journalEntry.Entity.User;
import com.megeMind.journalEntry.Repository.JournalEntryRepository;

import org.bson.types.ObjectId;

import org.springframework.http.HttpStatus;
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

    //Get
    public List<JournalEntry> getAllJournalEntries(String username){
        Optional<User> user = userService.getUser(username);
        if(user.isPresent()){
            return user.get().getJournalEntries();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        }
    }
    //Get One JournalEntry
    public JournalEntry getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Journal Entry not Found"));
    }
    //Post
    @Transactional
    public JournalEntry saveEntry(String username,JournalEntry journalEntry){
        try{
            User user = userService.getUser(username).orElseThrow( ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found"));
            journalEntry.setDate(LocalDate.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            userService.save(null);
            userService.save(user);
            return  savedJournalEntry;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }


    }
    // Delete
    public void deleteJournalEntryById(ObjectId id,String username){
        User userdb = userService.getUser(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found"));
        userdb.getJournalEntries().removeIf((x)->x.getId().equals(id));
        userService.save(userdb);
        journalEntryRepository.deleteById(id);
    }

    // Update
    public JournalEntry update(JournalEntry newjournalEntry , ObjectId myid){
        JournalEntry oldJournalEntry = getJournalEntryById(myid);
        if(newjournalEntry.getTitle()!=null && !newjournalEntry.getTitle().isBlank()){
            oldJournalEntry.setTitle(newjournalEntry.getTitle());
        }
        if(newjournalEntry.getContent()!=null && !newjournalEntry.getContent().isBlank()){
            oldJournalEntry.setContent(newjournalEntry.getContent());
        }

        return journalEntryRepository.save(oldJournalEntry);
    }


}
