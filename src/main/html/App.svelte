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
    let stepFrequencies;
    let oscillators;
    let activeNote;
    let activeQuality;
    let activeWaveform = 'sawtooth';

    jQuery.ajax('/notes440.json').done(retrievedNotes => {
        notesMap = retrievedNotes;
    });
    jQuery.ajax('/stepFrequencies440.json').done(retrievedStepFrequencies => {
        stepFrequencies = retrievedStepFrequencies;
    });

    audioContextStore.set(audioContext);
    activeNoteStore.subscribe(note => {
        activeNote = note;
    })
    $: (function() {
        if (notesMap && stepFrequencies) {
            notesMapStore.set({
                notesMap,
                stepFrequencies
            })
        }
    })();

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
                const frequency = stepFrequencies[order + step]
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

{#if notesMap && stepFrequencies}
<select on:change={e => activeNoteStore.set(e.target.value)} value={activeNote}>
    {#each Object.keys(notesMap) as noteName}
    <option value={noteName}>{noteName}</option>
    {/each}
</select>
<select bind:value={activeQuality}>
    {#each Object.keys(qualityMap) as qual}
    <option value={qualityMap[qual]}>{qual}</option>
    {/each}
</select>
<select bind:value={activeWaveform}>
    {#each waveforms as waveform}
    <option>{waveform}</option>
    {/each}
</select>
<button id="play" data-playing="false" on:click={play}>Play/Pause</button>
{/if}