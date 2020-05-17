/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import audiziler.media.visualizer.Areas;
import audiziler.media.visualizer.Bars;
import audiziler.media.visualizer.Particles;
import audiziler.media.visualizer.Symmetric;
import audiziler.media.visualizer.Visualization;
import audiziler.media.visualizer.VisualizationType;
import audiziler.ui.WindowSize;
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
    
    Visualization flame;
    Visualization bars;
    Visualization symmetric;
    Visualization areas;
    /**
     * Constructs the visualizer <code>Pane</code>-component
     * and constructs the <code>Visualization</code> objects with the given WindowSize
     * @param windowSize 
     */
    public Visualizer(WindowSize windowSize) {
        this.windowSize = windowSize;
        visualizer = new Pane();
        visualizer.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        visualizer.setViewOrder(10);
        
        flame = new Particles(windowSize);
    
        bars = new Bars(windowSize);
        
        symmetric = new Symmetric(windowSize);
        
        areas = new Areas(windowSize);
        
        visualizer.getChildren().addAll(bars.getVisualization(), flame.getVisualization(), symmetric.getVisualization(), areas.getVisualization());
    }
    /**
     * Creates a listener which updates the visualizations with relevant data
     * @return a <code>Consumer</code> to be run by the <code>MediaPlayer</code>
     */
    public Consumer<float[]> createListener() {
        Consumer listener = new Consumer<float[]>() {
            @Override
            public void accept(float[] magnitudes) {
                flame.update(magnitudes);
                bars.update(magnitudes);
                symmetric.update(magnitudes);
                areas.update(magnitudes);
            }
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
        bars.setVisible(false);
        flame.setVisible(false);
        symmetric.setVisible(false);
        areas.setVisible(false);
        switch (type) {
            case BARS: 
                bars.setVisible(true);
                break;
            case FLAME: 
                flame.setVisible(true);
                break;
            case SYMMETRY:
                symmetric.setVisible(true);
                break;
            case AREAS:
                areas.setVisible(true);
        }
    }
    /**
     * Binds the Settings of the <code>Visualization</code>-objects to the given Settings
     * @param settings 
     */
    public void bindSettings(Settings settings) {
        flame.setSettings(settings);
        bars.setSettings(settings);
        symmetric.setSettings(settings);
        areas.setSettings(settings);
    }
}
