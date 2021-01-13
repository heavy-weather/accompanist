package com.anthonyguidotti.accompanist.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfig {
    // TODO: Read more about mongodb configuration. Figure out what setting may or may not need to
    // be set, e.g. write / read concern, should set up a replica set, sharded cluster, etc. For
    // now, just set up connection to localhost to get started.
    private MongoDBPropertiesConfig mongoDBPropertiesConfig;

    public MongoDBConfig(
            MongoDBPropertiesConfig mongoDBPropertiesConfig
    ) {
        this.mongoDBPropertiesConfig = mongoDBPropertiesConfig;
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                defaultCodecRegistry,
                CodecRegistries.fromCodecs(
                        new NToneEqualTemperamentCodec(),
                        new TuningCodec()
                )
        );
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        return mongoClient.getDatabase("accompanist");
    }
}
