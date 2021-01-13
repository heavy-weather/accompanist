package com.anthonyguidotti.accompanist.chord;

import org.bson.types.ObjectId;

import java.util.List;

public class Chord {
    private ObjectId id;
    private String name;
    private int[] requiredSteps;
    private int[] optionalSteps;
    private List<ChordType> types;

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

    public int[] getRequiredSteps() {
        return requiredSteps;
    }

    public void setRequiredSteps(int[] requiredSteps) {
        this.requiredSteps = requiredSteps;
    }

    public int[] getOptionalSteps() {
        return optionalSteps;
    }

    public void setOptionalSteps(int[] optionalSteps) {
        this.optionalSteps = optionalSteps;
    }

    public List<ChordType> getTypes() {
        return types;
    }

    public void setTypes(List<ChordType> types) {
        this.types = types;
    }
}