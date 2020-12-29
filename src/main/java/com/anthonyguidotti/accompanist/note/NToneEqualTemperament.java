package com.anthonyguidotti.accompanist.note;

import org.springframework.util.Assert;

public enum NToneEqualTemperament {
        TWELVE(
            new String[][] {
                new String[] {"B#", "C", "Dbb"},
                new String[] {"C#", "Db"},
                new String[] {"Cx", "D", "Ebb"},
                new String[] {"D#", "Eb"},
                new String[] {"Dx", "E", "Fb"},
                new String[] {"E#", "F", "Gbb"},
                new String[] {"F#", "Gb"},
                new String[] {"Fx", "G", "Abb"},
                new String[] {"G#", "Ab"},
                new String[] {"Gx", "A", "Bbb"},
                new String[] {"A#", "Bb"},
                new String[] {"Ax", "B", "Cb"}
            },
                12,
            new double[] {440},
            69,
            -1,
            128
    );

    private final String[][] noteNames;
    private final double[] baseFrequencies;
    private final int baseFrequencyPosition;
    private final int startingOctave;
    private final int totalGeneratedSteps;
    private final int octaveSize;

    NToneEqualTemperament(
            String[][] noteNames,
            int octaveSize,
            double[] baseFrequencies,
            int baseFrequencyPosition,
            int startingOctave,
            int totalGeneratedSteps
    ) {
        Assert.isTrue(
                noteNames.length == octaveSize,
                "Must specify a list of enharmonic note names equal in length to total number steps in temperament"
        );
        this.noteNames = noteNames;
        this.octaveSize = octaveSize;
        this.baseFrequencies = baseFrequencies;
        this.baseFrequencyPosition = baseFrequencyPosition;
        this.startingOctave = startingOctave;
        this.totalGeneratedSteps = totalGeneratedSteps;
    }

    public String[][] getNoteNames() {
        return noteNames;
    }

    public int getOctaveSize() {
        return octaveSize;
    }

    public double[] getBaseFrequencies() {
        return baseFrequencies;
    }

    public int getBaseFrequencyPosition() {
        return baseFrequencyPosition;
    }

    public int getStartingOctave() {
        return startingOctave;
    }

    public int getTotalGeneratedSteps() {
        return totalGeneratedSteps;
    }
}
