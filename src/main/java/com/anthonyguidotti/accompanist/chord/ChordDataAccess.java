package com.anthonyguidotti.accompanist.chord;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class ChordDataAccess {
    private final MongoCollection<Chord> collection;

    public ChordDataAccess(
            MongoDatabase database
    ) {
        this.collection = database.getCollection("chord", Chord.class);
    }
}
