package com.anthonyguidotti.accompanist.note;

import java.util.List;
import java.util.Map;

public class NoteMap {
    private Map<String, Note> notes;
    private List<Double> frequencyList;

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
}
