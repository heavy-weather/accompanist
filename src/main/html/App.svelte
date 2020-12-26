<script>
    import jQuery from 'jquery';

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
    let activeNote;
    let activeQuality;
    let activeWaveform;

    jQuery.ajax('/notes440.json').done(retrievedNotes => {
        notes = retrievedNotes;
        jQuery.ajax('/stepFrequencies440.json').done(retrievedStepFrequencies => {
            stepFrequencies = retrievedStepFrequencies;
        })
    })

    function createOscillators() {
        if (activeQuality) {
            const order = notes[activeNote].order;
            oscillators = [];
            for (const step of activeQuality) {
                const oscillator = audioContext.createOscillator();
                const frequency = stepFrequencies[order + step]
                oscillator.type = activeWaveform;
                oscillator.frequency.setValueAtTime(frequency, audioContext.currentTime);
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