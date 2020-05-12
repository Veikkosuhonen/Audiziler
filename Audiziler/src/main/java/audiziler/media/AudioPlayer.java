/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import javafx.scene.media.AudioSpectrumListener;

/**
 *
 * @author Veikko
 */
public interface AudioPlayer {
    public void play();
    public void pause();
    public void stop();
    public void toStart();
    public boolean isPlaying();
    public void setOnEndOfMedia(Runnable r);
    public void setAudioSpectrumListener(AudioSpectrumListener listener);
    public void bindSettings(Settings settings);
}
