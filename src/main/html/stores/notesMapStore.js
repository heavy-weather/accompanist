import { writable } from "svelte/store";
const { set, subscribe } = writable();

function createNotesMapStore() {
    let notesMap;
    let frequencyList;
    let noteOrder;

    function setWrapper(config) {
        notesMap = config.notesMap;
        frequencyList = config.frequencyList;
        noteOrder = config.noteOrder;
        set(config);
    }

    return {
        set: setWrapper,
        notesMap,
        frequencyList,
        noteOrder,
        subscribe
    }
}

export default createNotesMapStore();