package com.anthonyguidotti.accompanist.note;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(
            NoteService noteService
    ) {
        this.noteService = noteService;
    }

    @GetMapping(
            value = "/notes",
            produces = "application/json"
    )
    public NoteMap getNotes() {
        NToneEqualTemperament t = NToneEqualTemperament.TWELVE;
        return noteService.generateNotes(
                t.getNoteNames(),
                t.getOctaveSize(),
                t.getBaseFrequencies()[0],
                t.getBaseFrequencyPosition(),
                t.getStartingOctave(),
                t.getTotalGeneratedSteps()
        );
    }

}
