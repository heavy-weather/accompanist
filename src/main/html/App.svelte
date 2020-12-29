<script>
    import jQuery from 'jquery';
    import { onMount } from 'svelte';
    import notesMapStore from "./stores/notesMapStore";
    import activeNoteStore from "./stores/activeNoteStore";
    import audioContextStore from "./stores/audioContextStore";

    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const qualityMap = {
        Major: [0, 4, 7],
        Minor: [0, 3, 7],
        Diminished: [0, 3, 6],
        Augmented: [0, 4, 8]
    }
    const waveforms = ['sine', 'square', 'sawtooth', 'triangle'];

    let notesMap;
    let frequencyList;
    let noteOrder;
    let oscillators;
    let activeNote;
    let activeQuality;
    let activeWaveform = 'sawtooth';

    jQuery.ajax('/notes/TWELVE_440.json').done(retrievedNotes => {
        notesMap = retrievedNotes.notes;
        frequencyList = retrievedNotes.frequencyList;
        noteOrder = retrievedNotes.noteOrder;
        notesMapStore.set(retrievedNotes);
    });

    audioContextStore.set(audioContext);
    activeNoteStore.subscribe(note => {
        activeNote = note;
    });

    onMount(() => {
        document.addEventListener('keydown', event => {
            if (activeNote) {
                let key = event.key;
                if (key && typeof key === 'string') {
                    key = key.toUpperCase();
                    if (['A', 'B', 'C', 'D', 'E', 'F', 'G'].includes(key)) {
                        // Remove enharmonic, makes it possible to set natural
                        // note when enharmonic set
                        activeNoteStore.set(activeNote.replace(/[#b]/, '').replace(/[A-G]/, key));
                    }
                    if (['#', '@'].includes(key)) {
                        if (key === '#' && ['C', 'D', 'F', 'G', 'A'].includes(activeNote[0]) ||
                            key === '@' && ['D', 'E', 'G', 'A', 'B'].includes(activeNote[0])) {
                            key = key.replace('@', 'b');
                            if (activeNote.includes('#') || activeNote.includes('b')) {
                                activeNoteStore.set(activeNote.replace(/[#b]/, key));
                            } else {
                                activeNoteStore.set(activeNote.slice(0, 1) + key + activeNote.slice(1));
                            }
                        }
                    }
                    ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'].forEach(octave => {
                        if (key === octave) {
                            activeNoteStore.set(activeNote.replace(/[-\d]+$/, key));
                        }
                    })
                }
            }
        });
    })

    function createOscillators() {
        if (activeQuality) {
            const order = notesMap[activeNote].order;
            oscillators = [];
            for (const step of activeQuality) {
                const frequency = frequencyList[order + step]
                const oscillator = new OscillatorNode(audioContext, {type: activeWaveform, frequency});
                oscillator.connect(audioContext.destination);
                oscillators.push(oscillator);
            }
        }

        return oscillators;
    }

    function play() {
        if (this.dataset.playing === 'false') {
            createOscillators();
            oscillators.forEach(o => o.start());
            this.dataset.playing = 'true';
        } else {
            oscillators.forEach(o => {
                o.stop();
            });
            this.dataset.playing = 'false';
        }
    }
</script>

<style>
    @import "webjars/bootstrap/4.5.3/css/bootstrap.css";
</style>

<nav class="d-flex w-100 position-fixed justify-content-between align-items-center px-2" style="height:3rem;top:0;">
    <div class="d-flex align-items-center">
        {#if notesMap && frequencyList}
        <select class="form-control form-control-sm mr-2"
                style="max-width:5rem;"
                on:change={e => activeNoteStore.set(e.target.value)}
                value={activeNote}>
            {#each noteOrder as noteName}
                <option value={noteName}>{noteName}</option>
            {/each}
        </select>
        <select class="form-control form-control-sm mr-2" style="max-width:7rem;" bind:value={activeQuality}>
            {#each Object.keys(qualityMap) as qual}
                <option value={qualityMap[qual]}>{qual}</option>
            {/each}
        </select>
        <select class="form-control form-control-sm mr-2" style="max-width:7rem;" bind:value={activeWaveform}>
            {#each waveforms as waveform}
                <option>{waveform}</option>
            {/each}
        </select>
        <button id="play" class="btn btn-sm btn-success" data-playing="false" on:click={play}>Play/Pause</button>
        {/if}
    </div>
    <div class="d-flex align-items-center justify-content-end">

    </div>
</nav>
<div class="p-3 mt-5 flex-grow-1">
    <div class="d-flex align-items-center">

    </div>
</div>