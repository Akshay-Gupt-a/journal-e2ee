package com.megeMind.journalEntry.cache;

import com.megeMind.journalEntry.Entity.ConfigJournalAppEntity;
import com.megeMind.journalEntry.Repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AppCache {
    public enum keys{
        WEATHER_API
    }
    public   Map<String ,String> appCacheMap ;
    private final ConfigJournalAppRepository configJournalAppRepository;
    public AppCache(ConfigJournalAppRepository configJournalAppRepository) {
        this.configJournalAppRepository = configJournalAppRepository;
    }
    @PostConstruct
    public void init(){
        appCacheMap = new ConcurrentHashMap<>();
        List<ConfigJournalAppEntity> configJournalAppEntities = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:configJournalAppEntities){
            appCacheMap.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
