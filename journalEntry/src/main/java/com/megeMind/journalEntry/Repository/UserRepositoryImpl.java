package com.megeMind.journalEntry.Repository;

import com.megeMind.journalEntry.Entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryImpl {
    private final MongoTemplate mongoTemplate;
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public List<User> findUser(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9\\.+-]+@[a-zA-Z0-9\\.+-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
                return mongoTemplate.find(query, User.class);
    }
}
