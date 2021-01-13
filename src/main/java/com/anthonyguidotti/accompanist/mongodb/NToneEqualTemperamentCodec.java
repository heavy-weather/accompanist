package com.anthonyguidotti.accompanist.mongodb;

import com.anthonyguidotti.accompanist.tuning.NToneEqualTemperament;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class NToneEqualTemperamentCodec implements Codec<NToneEqualTemperament> {
    @Override
    public NToneEqualTemperament decode(BsonReader reader, DecoderContext decoderContext) {
        return NToneEqualTemperament.valueOf(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, NToneEqualTemperament value, EncoderContext encoderContext) {
        writer.writeString(value.toString());
    }

    @Override
    public Class<NToneEqualTemperament> getEncoderClass() {
        return NToneEqualTemperament.class;
    }
}
