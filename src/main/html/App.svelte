<script>
    import jQuery from 'jquery';
    import { onMount } from 'svelte';

    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const qualityMap = {
        Major: [0, 4, 7],
        Minor: [0, 3, 7],
        Diminished: [0, 3, 6],
        Augmented: [0, 4, 8]
    }
    const waveforms = ['sine', 'square', 'sawtooth', 'triangle'];

    let notes;
    let stepFrequencies;
    let oscillators;
    let activeNote = 'C4';
    let activeQuality;
    let activeWaveform = 'sawtooth';

    jQuery.ajax('/notes440.json').done(retrievedNotes => {
        notes = retrievedNotes;
    });
    jQuery.ajax('/stepFrequencies440.json').done(retrievedStepFrequencies => {
        stepFrequencies = retrievedStepFrequencies;
    });

    onMount(() => {
        document.addEventListener('keydown', event => {
            if (activeNote) {
                let key = event.key.toUpperCase();
                if (key && typeof key === 'string') {
                    if (['A', 'B', 'C', 'D', 'E', 'F', 'G'].includes(key)) {
                        // Remove enharmonic, set active note
                        activeNote = activeNote.replace(/[#b]/, '').replace(/[A-G]/, key);
                    }
                    if (['#', '@'].includes(key)) {
                        if (key === '#' && ['C', 'D', 'F', 'G', 'A'].includes(activeNote[0]) ||
                            key === '@' && ['D', 'E', 'G', 'A', 'B'].includes(activeNote[0])) {
                            key = key.replace('@', 'b');
                            if (activeNote.includes('#') || activeNote.includes('b')) {
                                activeNote = activeNote.replace(/[#b]/, key);
                            } else {
                                activeNote = activeNote.slice(0, 1) + key + activeNote.slice(1);
                            }
                        }
                    }
                    ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'].forEach(octave => {
                        if (key === octave) {
                            activeNote = activeNote.replace(/[-\d]+$/, key);
                        }
                    })
                }
            }
        });
    })

    function createOscillators() {
        if (activeQuality) {
            const order = notes[activeNote].order;
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

{#if notes && stepFrequencies}
<select bind:value={activeNote}>
    {#each Object.keys(notes) as noteName}
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