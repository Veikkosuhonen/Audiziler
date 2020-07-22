/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import java.io.File;
import java.net.MalformedURLException;
import java.util.function.Consumer;
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
public class MPlayer implements AudiPlayer{
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
    @Override
    public void play() {
        player.play();
    }
    @Override
    public void pause() {
        player.pause();
    }
    /**
     * Stops and disposes the <code>MediaPlayer</code>
     */
    @Override
    public void stopPlayer() {
        if (isPlaying()) {
            player.stop();
            isPlaying = false;
        }
        player.dispose();
    }
    
    @Override
    public boolean isPlaying() {
        return player.getStatus() == Status.PLAYING;
    }
    
    /**
     * Invokes <code>seek(Duration.ZERO)</code> on the <code>MediaPlayer</code>
     */
    @Override
    public void toStart() {
        player.seek(Duration.ZERO);
    }
    /**
     * Set the event handler of <code>MediaPlayer</code> on end of media.
     * @param e
     */
    @Override
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
    @Override
    public void setAudioSpectrumListener(Consumer<float[]> listener) {
        player.setAudioSpectrumNumBands(1024);
        player.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double d, double d1, float[] floats, float[] floats1) {
                listener.accept(floats);
            }
            
        });
    }
    /**
     * Binds the the audio spectrum threshold to a Setting named "threshold" and Binds the spectrum update interval to the inverse of a Setting named "analyzer rate"
     * @param settings 
     */
    @Override
    public void bindSettings(Settings settings) {
        player.audioSpectrumThresholdProperty().bind(settings.get("threshold").getProperty());
        player.setAudioSpectrumInterval(1.0/60);
    }
}
