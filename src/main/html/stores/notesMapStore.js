import { writable } from "svelte/store";
const { set, subscribe } = writable();

function createNotesMapStore() {
    let notesMap;
    let frequencyList;

    function setWrapper(config) {
        notesMap = config.notesMap;
        frequencyList = config.frequencyList;
        set(config);
    }

    return {
        set: setWrapper,
        notesMap,
        frequencyList,
        subscribe
    }
}

export default createNotesMapStore();