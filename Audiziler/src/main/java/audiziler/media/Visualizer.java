/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media;

import audiziler.domain.Settings;
import audiziler.media.visualizer.Bars;
import audiziler.media.visualizer.FlameParticle;
import audiziler.media.visualizer.Phases;
import audiziler.media.visualizer.Visualization;
import audiziler.media.visualizer.VisualizationType;
import audiziler.ui.WindowSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;

/**
 *
 * @author vesuvesu
 */
public class Visualizer {

    WindowSize windowSize;
    AudioSpectrumListener listener;
    Pane visualizer;
    VisualizationType type;
    
    Visualization flame;
    Visualization bars;
    Visualization phase;
    
    public Visualizer(WindowSize windowSize) {
        this.windowSize = windowSize;
        visualizer = new Pane();
        visualizer.setViewOrder(10);
        
        flame = new FlameParticle(windowSize);
    
        bars = new Bars(windowSize);
       
        phase = new Phases(windowSize);
        
        
        visualizer.getChildren().addAll(bars.getVisualization(), flame.getVisualization(), phase.getVisualization());
    }
    public AudioSpectrumListener createListener(int bands) {
        listener = new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                flame.update(magnitudes);
                bars.update(magnitudes);
                phase.update(magnitudes, phases);
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
    public void setType(VisualizationType type) {
        this.type = type;
        bars.setVisible(false);
        flame.setVisible(false);
        phase.setVisible(false);
        switch (type) {
            case BARS: 
                bars.setVisible(true);
                break;
            case FLAME: 
                flame.setVisible(true);
                break;
            case PHASE: 
                phase.setVisible(true);
        }
    }
    public void bindSettings(Settings settings) {
        flame.setSettings(settings);
        bars.setSettings(settings);
        phase.setSettings(settings);
    }
}
