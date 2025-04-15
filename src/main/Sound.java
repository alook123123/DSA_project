package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    // Method to load a sound file and play it
    public void playSound(String fileName) {
        try {
            File soundFile = new File("sound/" + fileName); // Path to the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to loop background music
    public void playLoopedSound(String fileName) {
        try {
            File soundFile = new File("sound/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop indefinitely
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop sound
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to set the volume of the sound
    public void setVolume(float volume) {
        if (clip != null) {
            try {
                // Retrieve the volume control from the clip
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);  // Set the volume level (in dB)
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
