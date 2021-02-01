package com.anthonyguidotti.accompanist.chord;

import org.bson.types.ObjectId;

public class ChordVoicing {
    private ObjectId id;
    private ObjectId inversionId;
    private String chordName;
    private String inversionName;
    private String instrument;
    private int[] voices;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getInversionId() {
        return inversionId;
    }

    public void setInversionId(ObjectId inversionId) {
        this.inversionId = inversionId;
    }

    public String getChordName() {
        return chordName;
    }

    public void setChordName(String chordName) {
        this.chordName = chordName;
    }

    public String getInversionName() {
        return inversionName;
    }

    public void setInversionName(String inversionName) {
        this.inversionName = inversionName;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int[] getVoices() {
        return voices;
    }

    public void setVoices(int[] voices) {
        this.voices = voices;
    }
}
