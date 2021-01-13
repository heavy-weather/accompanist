package com.anthonyguidotti.accompanist.chord;

import org.springframework.stereotype.Component;

@Component
public class ChordService {
    private final ChordDataAccess chordDataAccess;
    private final VoicingDataAccess voicingDataAccess;

    public ChordService(
            ChordDataAccess chordDataAccess,
            VoicingDataAccess voicingDataAccess
    ) {
        this.chordDataAccess = chordDataAccess;
        this.voicingDataAccess = voicingDataAccess;
    }
}
