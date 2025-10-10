package com.akshay.journalApplication.service;

import com.akshay.journalApplication.entity.JournalEntry;
import com.akshay.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public  class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // PostMapping
    public void putJournalEntry( JournalEntry myjournalEntry){
        myjournalEntry.setDate(LocalDate.now());
        journalEntryRepository.save(myjournalEntry);
    }
   // GetMapping
    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }
    // GetByid
    public Optional<JournalEntry> getJournalEntryById(ObjectId myid){
        return journalEntryRepository.findById(myid);
    }
    // Put
    public void updateJournalEntry(ObjectId myid,JournalEntry myjournalEntry){
        JournalEntry oldEntry = journalEntryRepository.findById(myid).orElse(null);
        if(oldEntry!=null){
            String tempTitle = myjournalEntry.getTitle();
            String tempContent = myjournalEntry.getContent();
            oldEntry.setTitle(myjournalEntry.getTitle()!=null && !tempTitle.isEmpty()?tempTitle:oldEntry.getTitle());
            oldEntry.setContent(myjournalEntry.getContent()!=null && !tempContent.isEmpty()?tempContent:oldEntry.getContent());
            journalEntryRepository.save(oldEntry);
        }
        else{
            myjournalEntry.setDate(LocalDate.now());
            journalEntryRepository.save(myjournalEntry);
        }
    }
    //Delete by id
    public void deleteJournalEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
