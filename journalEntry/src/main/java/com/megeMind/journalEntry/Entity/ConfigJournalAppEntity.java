package com.megeMind.journalEntry.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="config_journal_app")
@Data
public class ConfigJournalAppEntity {
    @Id
    private ObjectId  id;

    private String key;

    private String value;

}
