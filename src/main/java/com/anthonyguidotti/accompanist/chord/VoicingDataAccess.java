package com.anthonyguidotti.accompanist.chord;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class VoicingDataAccess {
    private final MongoCollection<ChordVoicing> collection;

    public VoicingDataAccess(
            MongoDatabase database
    ) {
        this.collection = database.getCollection("voicing", ChordVoicing.class);
    }
}
