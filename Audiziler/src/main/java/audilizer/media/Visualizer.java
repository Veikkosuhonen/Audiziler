/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import javafx.beans.property.DoubleProperty;
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
    public Visualizer() {
        type = Type.BARS;
        visualizer = new Pane();
        visualizer.setViewOrder(10);
        bars = new BarGroup(256, visualizer.widthProperty().divide(2).subtract(512), visualizer.heightProperty().divide(4));
        cube = new LightCube(visualizer.widthProperty().add(0), visualizer.heightProperty().add(0));
        System.out.println("visualizer constructed");
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
