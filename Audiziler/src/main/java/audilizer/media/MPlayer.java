/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import java.io.File;
import java.net.MalformedURLException;
import javafx.event.Event;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author vesuvesu
 */
public class MPlayer {
    MediaPlayer player;
    boolean isPlaying;
    public MPlayer(File file) {
        isPlaying = false;
        try {
            player = new MediaPlayer(new Media(file.toURI().toURL().toString()));
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
            System.exit(-1);
        }
        player.setOnEndOfMedia(() -> {
            isPlaying = false;
        });
    }
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
    public void stop() {
        player.stop();
    }
    public void setOnEndOfMedia(Runnable e) {
        player.setOnEndOfMedia(() -> {
            e.run();
            player.seek(Duration.ZERO);
            player.pause();
        });
    }
    public int getBands() {
        return player.getAudioSpectrumNumBands();
    }
    public void setAudioSpectrumListener(AudioSpectrumListener listener) {
        player.setAudioSpectrumNumBands(1024);
        player.setAudioSpectrumInterval(1.0 / 60);
        player.setAudioSpectrumThreshold(-100);
        player.setAudioSpectrumListener(listener);
    }
}
