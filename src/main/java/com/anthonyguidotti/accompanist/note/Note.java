package com.anthonyguidotti.accompanist.note;

public class Note {
    private final double frequency;
    private final int order;

    public Note(
            double frequency,
            int order
    ) {
        this.frequency = frequency;
        this.order = order;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getOrder() {
        return order;
    }
}
