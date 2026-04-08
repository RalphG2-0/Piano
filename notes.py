import argparse
from pathlib import Path

import numpy as np
from scipy.io.wavfile import write


def generate_note(frequency, duration=1.5, sample_rate=44100, amplitude=32767):
    """Generate a mono sine wave for the requested note."""
    t = np.linspace(0, duration, int(sample_rate * duration), False)
    waveform = amplitude * np.sin(2 * np.pi * frequency * t)
    return waveform.astype(np.int16)


DEFAULT_NOTES = {
    'C4': 261.63,
    'C#4': 277.18,
    'D4': 293.66,
    'D#4': 311.13,
    'E4': 329.63,
    'F4': 349.23,
    'F#4': 369.99,
    'G4': 392.00,
    'G#4': 415.30,
    'A4': 440.00,
    'A#4': 466.16,
    'B4': 493.88,
    'C5': 523.26,
    'C#5': 554.36,
    'D5': 587.32,
    'D#5': 622.26,
    'E5': 659.23,
    'F5': 698.46,
    'F#5': 739.99,
    'G5': 783.99,
    'G#5': 830.61,
    'A5': 880.00,
    'A#5': 932.33,
    'B5': 987.77,
}


def write_notes(notes, output_dir='.', duration=1.5, sample_rate=44100, amplitude=32767):
    output_dir = Path(output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    for name, frequency in notes.items():
        filename = output_dir / f'{name}.wav'
        data = generate_note(
            frequency,
            duration=duration,
            sample_rate=sample_rate,
            amplitude=amplitude,
        )
        write(filename, sample_rate, data)
        print(f'Generated {filename}')


def parse_args():
    parser = argparse.ArgumentParser(description='Generate WAV files for piano notes.')
    parser.add_argument('--duration', type=float, default=1.5, help='Duration of each note in seconds.')
    parser.add_argument('--sample-rate', type=int, default=44100, help='Sample rate in Hz.')
    parser.add_argument('--amplitude', type=int, default=32767, help='Waveform amplitude.')
    parser.add_argument('--output-dir', default='.', help='Output directory for WAV files.')
    return parser.parse_args()


def main():
    args = parse_args()
    write_notes(
        DEFAULT_NOTES,
        output_dir=args.output_dir,
        duration=args.duration,
        sample_rate=args.sample_rate,
        amplitude=args.amplitude,
    )


if __name__ == '__main__':
    main()
