/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioSpectrumListener;

/**
 *
 * @author vesuvesu
 */
public class Visualizer {
    BarGroup bars;
    AudioSpectrumListener listener;
    HBox barGroup;
    public Visualizer() {
        bars = new BarGroup(256);
        barGroup = new HBox();
        barGroup.setAlignment(Pos.CENTER);
        barGroup.setTranslateX(-100);
        barGroup.setSpacing(1);
        System.out.println("visualizer constructed");
    }
    public AudioSpectrumListener createListener(int bands) {
        listener = new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                barGroup.getChildren().clear();
                barGroup.getChildren().addAll(bars.update(magnitudes));
            }
        };
        return listener;
    }
    public HBox getBarGroup() {
        return barGroup;
    }
}
