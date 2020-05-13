/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import java.util.function.Consumer;

/**
 *
 * @author Veikko
 */
public interface AudiPlayer {
    public void play();
    public void pause();
    public void stopPlayer();
    public void toStart();
    public boolean isPlaying();
    public void setOnEndOfMedia(Runnable r);
    public void setAudioSpectrumListener(Consumer<float[]> listener);
    public void bindSettings(Settings settings);
}
