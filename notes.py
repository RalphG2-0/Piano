import numpy as np
from scipy.io.wavfile  import write
def generer_note(frequence, duree=1.5, sample_rate=44100, amplitude=32767):
  t=np.linspace(0,duree,int(sample_rate*duree), False)
  onde = amplitude * np.sin(2 * np.pi * frequence * t)
  return onde.astype(np.int16)
notes={
           'C4': 261.63,
           'C4#': 277.18,
           'D4': 293.66,
           'D4#': 311.13,
           'E4' : 329.63 ,
           'F4' : 349.23 ,
           'F#4' : 369.99,
           'G4 ': 392.00,
           'G#4' : 415.30 ,
           'A4 ': 440.00 ,
           'A4#' : 466.16 ,
           'B4' : 493.88 ,
           'C5' : 523.26,
           'C5#': 554.36,
           'D5': 587.32,
           'D5#': 622.26,
           'E5' : 659.23,
           'F5' : 698.46,
           'F5#': 739.99,
           'G5' : 783.99,
           'G5#': 830.61,
           'A5' : 880.00, 
           'A5#': 932.33,
           'B5' : 987.77,
       }
for nom, freq in notes.items():
  data = generer_note(freq)
  write(f'{nom}.wav', 44100, data)
  print(f'Fichier {nom}.wav genere.')