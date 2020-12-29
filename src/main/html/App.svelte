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
    let showHelpModal = false;

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
                        activeNoteStore.set(activeNote.replace(/[#bx]+/, '').replace(/[A-G]/, key));
                    }
                    if (['#', '@', 'X', '!'].includes(key)) {
                        if (key === '#' || key === '@' ||
                            (key === 'X' && ['C', 'D', 'F', 'G', 'A'].includes(activeNote[0])) ||
                            (key === '!' && ['D', 'E', 'G', 'A', 'B'].includes(activeNote[0]))) {
                            key = key.replace('@', 'b').replace('!', 'bb').replace('X', 'x');
                            if (activeNote.includes('#') || activeNote.includes('b') || activeNote.includes('x')) {
                                activeNoteStore.set(activeNote.replace(/[#bx]+/, key));
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
                    console.debug(activeNote);
                }
            }
        });
    })()

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
    :global(.cursor-pointer) {
        cursor: pointer;
    }
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
        <i class="fas fa-question-circle text-dark mr-2 cursor-pointer"
           style="font-size:1.3rem;"
           on:click={() => showHelpModal = true}></i>
    </div>
</nav>

<div class="p-3 mt-5 flex-grow-1">
    <div class="d-flex align-items-center">

    </div>
</div>

{#if showHelpModal}
<div class="position-absolute w-100 h-100 d-flex p-3" style="background:rgba(0,0,0,0.3);">
    <div class="flex-grow-1 bg-white p-4">
        <div class="d-flex justify-content-between w-100">
            <h4>Help</h4>
            <i class="fas fa-times-circle text-dark cursor-pointer"
               style="font-size:1.3rem;"
               on:click={() => showHelpModal = false}></i>
        </div>
        <h5>Keyboard Shortcuts:</h5>
        <table class="table table-sm table-striped w-50">
            <tbody>
            {#each [
                ['a, b, c, d, e, f', 'Change active note'],
                ['0, 1, 2, 3, 4, 5, 6, 7, 8, 9', 'Change active octave'],
                ['#', 'Sharp active note'],
                ['@', 'Flat active note (b)'],
                ['x', 'Double sharp active note'],
                ['!', 'Double flat active note (bb)']
            ] as instruction}
                <tr>
                    <td class="font-weight-bold">{instruction[0]}</td>
                    <td>{instruction[1]}</td>
                </tr>
            {/each}
            </tbody>
        </table>
    </div>
</div>
{/if}