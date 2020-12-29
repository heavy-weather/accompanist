package com.anthonyguidotti.accompanist.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class NoteService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void generateNoteMaps() throws IOException {
        NToneEqualTemperament[] temperaments = NToneEqualTemperament.values();
        for (NToneEqualTemperament temperament : temperaments) {
            for (double baseFrequency : temperament.getBaseFrequencies()) {
                NoteMap noteMap = generateNotes(
                        temperament.getNoteNames(),
                        temperament.getOctaveSize(),
                        baseFrequency,
                        temperament.getBaseFrequencyPosition(),
                        temperament.getStartingOctave(),
                        temperament.getTotalGeneratedSteps()
                );
                String noteMapJSON = objectMapper.writeValueAsString(noteMap);

                // Write noteMap JSON to public directory
                String filename = temperament.toString() + "_" + (int)baseFrequency + ".json";
                File f = new File("src/main/resources/static/notes/" + filename);

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                    bw.write(noteMapJSON);
                } catch (IOException ioe) {
                    throw ioe;
                }
            }
        }
    }

    public NoteMap generateNotes(
            String[][] noteNames,
            int octaveSize,
            double baseFrequency,
            int baseFrequencyPosition,
            int startingOctave,
            int totalGeneratedSteps
    ) {
        NoteMap noteMap = new NoteMap();
        noteMap.setNotes(new HashMap<>());
        noteMap.setFrequencyList(new ArrayList<>());
        noteMap.setNoteOrder(new ArrayList<>());

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
                    noteMap.getNotes().put(nameWithOctave, note);
                    noteMap.getNoteOrder().add(nameWithOctave);
                }
                noteMap.getFrequencyList().add(roundedFrequency.doubleValue());

                order++;
                if (order == totalGeneratedSteps) {
                    break octaveLoop;
                }
            }
            bottomFrequency *= 2;
            currentOctave++;
        }

        return noteMap;
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
