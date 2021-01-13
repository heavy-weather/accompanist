package com.anthonyguidotti.accompanist.tuning;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuningService {
    private final TuningDataAccess tuningDataAccess;

    public TuningService(
            TuningDataAccess tuningDataAccess
    ) {
        this.tuningDataAccess = tuningDataAccess;
    }

    public Tuning get(String name) {
        return tuningDataAccess.read(name);
    }

    public void generateTunings() {
        NToneEqualTemperament[] temperaments = NToneEqualTemperament.values();

        for (NToneEqualTemperament temperament : temperaments) {
            for (double baseFrequency : temperament.getBaseFrequencies()) {

                Tuning tuning = generateTuning(
                        temperament,
                        temperament.getNoteNames(),
                        temperament.getOctaveSize(),
                        baseFrequency,
                        temperament.getBaseFrequencyPosition(),
                        temperament.getStartingOctave(),
                        temperament.getTotalGeneratedSteps()
                );

                tuningDataAccess.update(tuning);
            }
        }
    }

    public Tuning generateTuning(
            NToneEqualTemperament temperament,
            String[][] noteNames,
            int octaveSize,
            double baseFrequency,
            int baseFrequencyPosition,
            int startingOctave,
            int totalGeneratedSteps
    ) {
        Tuning tuning = new Tuning();
        tuning.setName(temperament.getName(baseFrequency));
        tuning.setTemperament(temperament);
        tuning.setNotes(new HashMap<>());
        tuning.setFrequencyList(new ArrayList<>());
        tuning.setNoteOrder(new ArrayList<>());

        int order = 0;
        int currentOctave = startingOctave;
        // Bottom frequency of octave
        double bottomFrequency = getStep(baseFrequency, baseFrequencyPosition * -1, octaveSize);

        octaveLoop:
        while (true) {
            for (int i = 0; i < octaveSize; i++) {

                double frequency = getStep(bottomFrequency, i, octaveSize);

                // There is a precision issue with the getStep method, so round off the
                // imprecision until fixed.
                BigDecimal roundedFrequency = new BigDecimal(frequency).setScale(6, RoundingMode.HALF_UP);

                for (String noteName : noteNames[i]) {
                    Note note = new Note(roundedFrequency.doubleValue(), order);
                    String nameWithOctave = noteName + currentOctave;
                    tuning.getNotes().put(nameWithOctave, note);
                    tuning.getNoteOrder().add(nameWithOctave);
                }
                tuning.getFrequencyList().add(roundedFrequency.doubleValue());

                order++;
                if (order == totalGeneratedSteps) {
                    break octaveLoop;
                }
            }
            bottomFrequency *= 2;
            currentOctave++;
        }

        return tuning;
    }

    // TODO: Fix precision issue in this method. E.g. A4 == 439.99999994 (Should be 440).
    private double getStep(
            double frequency,
            int step,
            int temperamentSteps
    ) {
        return frequency * Math.pow(2.0f, (double) step / (double) temperamentSteps);
    }
}
