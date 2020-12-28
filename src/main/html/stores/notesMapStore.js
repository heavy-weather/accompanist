import { writable } from "svelte/store";
const { set, subscribe, unsubscribe } = writable();

function createNotesMapStore() {
    let notesMap;
    let stepFrequencies;

    function setWrapper(config) {
        notesMap = config.notesMap;
        stepFrequencies = config.stepFrequencies;
        set(config);
    }

    return {
        set: setWrapper,
        notesMap,
        stepFrequencies,
        subscribe,
        unsubscribe
    }
}

export default createNotesMapStore();