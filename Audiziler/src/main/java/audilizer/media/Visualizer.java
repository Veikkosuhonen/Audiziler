/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import audilizer.domain.Settings;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;

/**
 *
 * @author vesuvesu
 */
public class Visualizer {
    BarGroup bars;
    LightCube cube;
    AudioSpectrumListener listener;
    Pane visualizer;
    Type type;
    Bloom bloom;
    Reflection reflection;
    public Visualizer(Settings settings) {
        
        bloom = new Bloom();
        type = Type.BARS;
        visualizer = new Pane();
        visualizer.setEffect(bloom);
        visualizer.setViewOrder(10);
        bars = new BarGroup(225, visualizer.widthProperty().divide(2).subtract(512), visualizer.heightProperty().add(0), settings);
        cube = new LightCube(visualizer.widthProperty().add(0), visualizer.heightProperty().add(0));
        System.out.println("visualizer constructed");
        
        bloom.thresholdProperty().bind(settings.get("bloom").getProperty());
    }
    public AudioSpectrumListener createListener(int bands) {
        listener = new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                switch (type) {
                    case BARS: {
                        visualizer.getChildren().clear();
                        visualizer.getChildren().addAll(bars.update(magnitudes));
                        break;
                    }
                    case CUBE: {
                        visualizer.getChildren().clear();
                        visualizer.getChildren().addAll(cube.update(magnitudes));
                        break;
                    }
                }
            }
        };
        return listener;
    }
    public Pane getVisualizer() {
        return visualizer;
    }
    public void clear() {
        visualizer.getChildren().clear();
    }
    public void setType(Type type) {
        this.type = type;
    }
    enum Type {
        BARS,
        CUBE
    }
}
