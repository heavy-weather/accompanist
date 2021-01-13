package com.anthonyguidotti.accompanist.chord;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class VoicingDataAccess {
    private final MongoCollection<Voicing> collection;

    public VoicingDataAccess(
            MongoDatabase database
    ) {
        this.collection = database.getCollection("voicing", Voicing.class);
    }
}
