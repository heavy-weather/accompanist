/*
https://pages.mtu.edu/~suits/notefreqs.html
Run this block in browser console to generate a JSON of note frequencies,
if additional frequency objects are needed (i.e. for A438, A444, etc.)
.. Assuming the page isn't updated
 */

const notes = {};
let i = 0;
for (let child of document.querySelector('center > table:first-child > tbody').children) {
    let noteName = [child.children[0].textContent.trim()];
    if (noteName[0] !== 'Note') {
        if (noteName[0].includes('/')) {
            noteName = noteName[0].split('/');
        }
        for (let name of noteName) {
            const note = {};
            const freq = child.children[1].textContent.trim();
            note.frequency = parseFloat(freq);
            note.order = i;
            notes[name] = note;
        }
        i++;
    }
}
console.log(JSON.stringify(notes));

const stepFrequencies = [];
for (let name of Object.keys(notes)) {
    stepFrequencies[notes[name].order] = notes[name].frequency;
}
console.log(JSON.stringify(stepFrequencies));