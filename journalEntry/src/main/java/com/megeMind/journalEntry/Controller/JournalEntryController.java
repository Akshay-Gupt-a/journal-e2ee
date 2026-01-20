package com.megeMind.journalEntry.Controller;


import com.megeMind.journalEntry.Entity.JournalEntry;
import com.megeMind.journalEntry.Service.JournalEntryService;
import com.megeMind.journalEntry.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService){
        this.journalEntryService = journalEntryService;
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(@PathVariable String username){
        List<JournalEntry>li = journalEntryService.getAllJournalEntries(username);
        return ResponseEntity.ok(li);
    }
    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myid){
        JournalEntry journalEntry = journalEntryService.getJournalEntryById(myid);
        return ResponseEntity.ok(journalEntry);
    }
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> addingJournalEntry(@PathVariable String username,@RequestBody JournalEntry journalEntry){
        JournalEntry result =  journalEntryService.saveEntry(username,journalEntry);
        return ResponseEntity.created(URI.create("/journal/"+result.getId())).body(result);
    }
    @DeleteMapping("/id/{myid}/username/{username}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable ObjectId myid ,@PathVariable String username){
        journalEntryService.deleteJournalEntryById(myid,username);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{myid}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@RequestBody JournalEntry journalEntry,@PathVariable ObjectId myid){
      JournalEntry result =  journalEntryService.update(journalEntry,myid);
      return  ResponseEntity.ok(result);
    }


}
