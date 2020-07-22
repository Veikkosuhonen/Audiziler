/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import audiziler.media.visualizer.Areas;
import audiziler.media.visualizer.Bars;
import audiziler.media.visualizer.DynamicSetting;
import audiziler.media.visualizer.Particles;
import audiziler.media.visualizer.Symmetric;
import audiziler.media.visualizer.Visualization;
import audiziler.media.visualizer.VisualizationType;
import audiziler.ui.WindowSize;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A class for initializing and handling of <code>Visualization</code> objects
 * @author vesuvesu
 */
public class Visualizer {

    WindowSize windowSize;
    Pane visualizer;
    VisualizationType type;
    ArrayList<Visualization> visualizations;
    DynamicSetting[] dynamics;
    /**
     * Constructs the visualizer <code>Pane</code>-component
     * and constructs the <code>Visualization</code> objects with the given WindowSize
     * @param windowSize 
     */
    public Visualizer(WindowSize windowSize) {
        this.windowSize = windowSize;
        visualizer = new Pane();
        
        //visualizer.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        visualizer.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        visualizer.setViewOrder(10);
        
        visualizations = new ArrayList();
        visualizations.add(new Bars(windowSize));
        visualizations.add(new Particles(windowSize));
        visualizations.add(new Symmetric(windowSize));
        visualizations.add(new Areas(windowSize));
        
        visualizer.getChildren().addAll(visualizations);
        
        dynamics = new DynamicSetting[3];
        dynamics[0] = new DynamicSetting(1,5,50);
        dynamics[1] = new DynamicSetting(7, 24, 40);
        dynamics[2] = new DynamicSetting(30, 60, 30);
    }
    /**
     * Creates a listener which updates the visualizations with relevant data
     * @return a <code>Consumer</code> to be run by the <code>MediaPlayer</code>
     */
    public Consumer<float[]> createListener() {
        Consumer listener = (Consumer<float[]>) (float[] magnitudes) -> {
            for (DynamicSetting d : dynamics) d.update(magnitudes);
            visualizations.forEach(v -> {
                v.update(magnitudes);
                v.updateDynamics(dynamics);
            });
        };
        return listener;
    }
    public Pane getVisualizer() {
        return visualizer;
    }
    /**
     * Clear the visualizer Pane component
     */
    public void clear() {
        visualizer.getChildren().clear();
    }
    /**
     * Set the correct type of <code>Visualization</code> visible and hide the other
     * @param type 
     */
    public void setType(VisualizationType type) {
        this.type = type;
        visualizations.forEach(v -> {
            if (v.getType().equals(type)) {
                v.setVisible(true);
            } else {
                v.setVisible(false);
            }
        });
    }
    /**
     * Binds the Settings of the <code>Visualization</code>-objects to the given Settings
     * @param settings 
     */
    public void bindSettings(Settings settings) {
        visualizations.forEach(v -> v.setSettings(settings));
    }
}
