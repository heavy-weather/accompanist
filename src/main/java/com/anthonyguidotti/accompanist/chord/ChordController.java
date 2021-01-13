package com.anthonyguidotti.accompanist.chord;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChordController {
    private final ChordService chordService;

    public ChordController(
            ChordService chordService
    ) {
        this.chordService = chordService;
    }
}
