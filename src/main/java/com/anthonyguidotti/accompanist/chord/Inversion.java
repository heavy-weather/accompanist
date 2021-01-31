package com.anthonyguidotti.accompanist.chord;

import org.bson.types.ObjectId;

public class Inversion {
    private ObjectId id;
    private String name;
    private int[] criteria;

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

    public int[] getCriteria() {
        return criteria;
    }

    public void setCriteria(int[] criteria) {
        this.criteria = criteria;
    }
}
