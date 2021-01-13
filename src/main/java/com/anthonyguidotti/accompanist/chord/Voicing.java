package com.anthonyguidotti.accompanist.chord;

import org.bson.types.ObjectId;

public class Voicing {
    private ObjectId id;
    private ObjectId chordTypeId;
    private Instrument instrument;
    private int[] voices;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getChordTypeId() {
        return chordTypeId;
    }

    public void setChordTypeId(ObjectId chordTypeId) {
        this.chordTypeId = chordTypeId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int[] getVoices() {
        return voices;
    }

    public void setVoices(int[] voices) {
        this.voices = voices;
    }
}
