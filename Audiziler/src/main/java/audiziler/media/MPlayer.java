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
 *
 * @author vesuvesu
 */
public class MPlayer implements AudioPlayer{
    MediaPlayer player;
    boolean isPlaying;
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
    @Override
    public void stop() {
        if (isPlaying()) {
            player.stop();
        }
        player.dispose();
    }
    @Override
    public void toStart() {
        player.seek(Duration.ZERO);
    }
    @Override
    public boolean isPlaying() {
        return player.getStatus() == Status.PLAYING;
    }
    @Override
    public void setOnEndOfMedia(Runnable e) {
        player.setOnEndOfMedia(() -> {
            e.run();
            player.stop();
        });
    }
    @Override
    public void setAudioSpectrumListener(AudioSpectrumListener listener) {
        player.setAudioSpectrumNumBands(1024);
        player.setAudioSpectrumListener(listener);
    }
    @Override
    public void bindSettings(Settings settings) {
        player.audioSpectrumThresholdProperty().bind(settings.get("threshold").getProperty());
        player.audioSpectrumIntervalProperty().bind(new SimpleDoubleProperty(1).divide(settings.get("analyzer rate").getProperty()));
    }
}
