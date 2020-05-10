/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import java.io.File;
import java.net.MalformedURLException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * Handles the initialization and playback of a JavaFX <code>MediaPlayer</code>
 * @author vesuvesu
 */
public class MPlayer {
    MediaPlayer player;
    boolean isPlaying;
    /**
     * Creates a <code>Media</code>-object from the given file and constructs the <code>MediaPlayer</code> with the <code>Media</code>
     * @param file 
     */
    public MPlayer(File file) {
        isPlaying = false;
        try {
            Media media = new Media(file.toURI().toURL().toString());
            player = new MediaPlayer(media);
        } catch (MalformedURLException mue) {
            System.out.println(mue.getMessage());
        }
        player.setOnEndOfMedia(() -> {
            isPlaying = false;
        });
    }
    /**
     * Switches the <code>MediaPlayer</code> status between playing and paused
     * @return a Boolean indicating whether the <code>MediaPlayer</code> is playing after being toggled.
     */
    public boolean toggle() {
        if (isPlaying) {
            player.pause();
            isPlaying = false;
        } else {
            player.play();
            isPlaying = true;
        }
        return isPlaying;
    }
    /**
     * Stops and disposes the <code>MediaPlayer</code>
     */
    public void stop() {
        if (player.getStatus() == Status.PLAYING) {
            player.stop();
            isPlaying = false;
        }
        player.dispose();
    }
    /**
     * Invokes <code>seek(Duration.ZERO)</code> on the <code>MediaPlayer</code>
     */
    public void toStart() {
        player.seek(Duration.ZERO);
    }
    /**
     * Set the event handler of <code>MediaPlayer</code> on end of media.
     * @param e
     */
    public void setOnEndOfMedia(Runnable e) {
        player.setOnEndOfMedia(() -> {
            e.run();
            player.stop();
        });
    }
    /**
     * Set the listener and set the number of audio spectrum bands to 1024
     * @param listener 
     */
    public void setAudioSpectrumListener(AudioSpectrumListener listener) {
        player.setAudioSpectrumNumBands(1024);
        player.setAudioSpectrumListener(listener);
        
    }
    /**
     * Binds the the audio spectrum threshold to a Setting named "threshold" and Binds the spectrum update interval to the inverse of a Setting named "analyzer rate"
     * @param settings 
     */
    public void bindSettings(Settings settings) {
        player.audioSpectrumThresholdProperty().bind(settings.get("threshold").getProperty());
        player.audioSpectrumIntervalProperty().bind(new SimpleDoubleProperty(1).divide(settings.get("analyzer rate").getProperty()));
    }
}
