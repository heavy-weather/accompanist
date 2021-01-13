package com.anthonyguidotti.accompanist.mongodb;

import com.anthonyguidotti.accompanist.tuning.NToneEqualTemperament;
import com.anthonyguidotti.accompanist.tuning.Note;
import com.anthonyguidotti.accompanist.tuning.Tuning;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuningCodec implements Codec<Tuning> {

    @Override
    public Tuning decode(BsonReader reader, DecoderContext decoderContext) {
        Tuning tuning = new Tuning();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String name = reader.readName();

            if ("_id".equals(name)) {
                tuning.setId(reader.readObjectId());
            } else if ("name".equals(name)) {
                tuning.setName(reader.readString());
            } else if ("temperament".equals(name)) {
                tuning.setTemperament(NToneEqualTemperament.valueOf(reader.readString()));
            } else if ("notes".equals(name)) {
                tuning.setNotes(new HashMap<>());

                reader.readStartDocument();
                while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    String noteName = reader.readName();
                    double frequency = 0d;
                    int order = 0;

                    reader.readStartDocument();
                    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                        String notePropertyName = reader.readName();
                        if ("frequency".equals(notePropertyName)) {
                            frequency = reader.readDouble();
                        } else if ("order".equals(notePropertyName)) {
                            order = reader.readInt32();
                        }
                    }
                    reader.readEndDocument();
                    tuning.getNotes().put(noteName, new Note(frequency, order));
                }
                reader.readEndDocument();
            } else if ("frequencyList".equals(name)) {
                tuning.setFrequencyList(new ArrayList<>());

                reader.readStartArray();
                while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    tuning.getFrequencyList().add(reader.readDouble());
                }
                reader.readEndArray();
            } else if ("noteOrder".equals(name)) {
                tuning.setNoteOrder(new ArrayList<>());

                reader.readStartArray();
                while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    tuning.getNoteOrder().add(reader.readString());
                }
                reader.readEndArray();
            }
        }
        reader.readEndDocument();

        return tuning;
    }

    @Override
    public void encode(BsonWriter writer, Tuning value, EncoderContext encoderContext) {
        writer.writeStartDocument();
            writer.writeName("name");
            writer.writeString(value.getName());
            writer.writeName("temperament");
            writer.writeString(value.getTemperament().toString());
            writer.writeName("notes");
            writer.writeStartDocument();
            for (Map.Entry<String, Note> entry : value.getNotes().entrySet()) {
                writer.writeName(entry.getKey());
                writer.writeStartDocument();
                    writer.writeName("frequency");
                    writer.writeDouble(entry.getValue().getFrequency());
                    writer.writeName("order");
                    writer.writeInt32(entry.getValue().getOrder());
                writer.writeEndDocument();
            }
            writer.writeEndDocument();
            writer.writeName("frequencyList");
            writer.writeStartArray();
            for (Double frequency : value.getFrequencyList()) {
                writer.writeDouble(frequency);
            }
            writer.writeEndArray();
            writer.writeName("noteOrder");
            writer.writeStartArray();
            for (String noteName : value.getNoteOrder()) {
                writer.writeString(noteName);
            }
            writer.writeEndArray();
        writer.writeEndDocument();
    }

    @Override
    public Class<Tuning> getEncoderClass() {
        return Tuning.class;
    }
}
