package com.anthonyguidotti.accompanist.tuning;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class Tuning {
    private ObjectId id;
    private String name;
    private NToneEqualTemperament temperament;
    private Map<String, Note> notes;
    private List<Double> frequencyList;
    private List<String> noteOrder;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NToneEqualTemperament getTemperament() {
        return temperament;
    }

    public void setTemperament(NToneEqualTemperament temperament) {
        this.temperament = temperament;
    }

    public Map<String, Note> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, Note> notes) {
        this.notes = notes;
    }

    public List<Double> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<Double> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public List<String> getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(List<String> noteOrder) {
        this.noteOrder = noteOrder;
    }
}
