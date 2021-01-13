package com.anthonyguidotti.accompanist.tuning;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Component
public class TuningDataAccess {
    private final MongoCollection<Tuning> collection;

    public TuningDataAccess(
            MongoDatabase database
    ) {
        this.collection = database.getCollection("tuning", Tuning.class);
    }

    public List<Tuning> read() {
        return collection.find().into(new ArrayList<>());
    }

    public Tuning read(String name) {
        return collection.find(eq("name", name)).first();
    }

    public void update(Tuning tuning) {
        ReplaceOptions replaceOptions = new ReplaceOptions();
        replaceOptions.upsert(true);
        collection.replaceOne(
                eq("name", tuning.getName()),
                tuning,
                replaceOptions
        );
    }
}
