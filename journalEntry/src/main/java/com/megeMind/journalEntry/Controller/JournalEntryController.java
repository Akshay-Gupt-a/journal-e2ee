package com.megeMind.journalEntry.Controller;


import com.megeMind.journalEntry.Entity.JournalEntry;
import com.megeMind.journalEntry.Service.JournalEntryService;

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
    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(){
        List<JournalEntry>li = journalEntryService.getAllJournalEntries();
        return ResponseEntity.ok(li);
    }
    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myid){
        JournalEntry journalEntry = journalEntryService.getJournalEntryById(myid);
        return ResponseEntity.ok(journalEntry);
    }
    @PostMapping()
    public ResponseEntity<JournalEntry> addingJournalEntry(@RequestBody JournalEntry journalEntry){

        JournalEntry result =  journalEntryService.saveEntry(journalEntry);
        return ResponseEntity.created(URI.create("/journal/"+result.getId())).body(result);
    }
    @DeleteMapping("/id/{myid}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable ObjectId myid ){

        boolean deleted =journalEntryService.deleteJournalEntryById(myid);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{myid}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@RequestBody JournalEntry journalEntry,@PathVariable ObjectId myid){
      JournalEntry result =  journalEntryService.update(journalEntry,myid);
      return  ResponseEntity.ok(result);
    }


}
