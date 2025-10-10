package com.akshay.journalApplication.controller;

import com.akshay.journalApplication.entity.JournalEntry;

import com.akshay.journalApplication.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping
    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryService.getAllJournalEntries();
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getJournalEntryById(myId).orElse(null);
    }
    @PostMapping
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry){
        journalEntryService.putJournalEntry(journalEntry);
        return journalEntry;
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myjournalEntry){
        journalEntryService.updateJournalEntry(myId,myjournalEntry);
        return myjournalEntry;
    }
    @DeleteMapping("/id/{myid}")
    public void deleteJournalEntry(@PathVariable ObjectId myid){
        journalEntryService.deleteJournalEntry(myid);
    }
}
