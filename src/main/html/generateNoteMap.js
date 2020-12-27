function getStep(freq, step) {
    return freq * (2 ** (step/12));
}

const notes = [["C"],["C#","Db"],["D"],["D#","Eb"],["E"],["F"],["F#","Gb"],["G"],["G#","Ab"],["A"],["A#","Bb"],["B"]];

const cn1 = getStep(440, -69);

let order = 0;
let freq = cn1;
const notesMap = {};

// Starting with C-1, calculate all notes up to
for (let i = -1; i < 12; i++) {
    for (let j = 0; j < 12; j++) {
        notes[j].forEach(note => {
            if (Object.keys(notesMap).length === 181) {
                return;
            }
            notesMap[`${note}${i}`] = {
                order, frequency: getStep(freq, j)
            }
        });
        order++;
    }
    freq = getStep(freq, 12);
}

const freqList = [];
Object.keys(notesMap).forEach(n => {
    freqList[notesMap[n].order] = notesMap[n].frequency;
});

