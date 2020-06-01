/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import audiziler.ui.WindowSize;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;

/**
 *
 * @author Veikko
 */
public abstract class Visualization extends Group {
    WindowSize windowSize;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    Reflection reflection;
    Settings settings;
    
    public Visualization(WindowSize windowSize) {
        super();
        this.windowSize = windowSize;
        canvas = new Canvas();
        canvas.widthProperty().setValue(1920);
        canvas.heightProperty().setValue(1080);
        canvas.setCache(true);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        reflection = new Reflection();
        reflection.setInput(bloom);
        canvas.setEffect(reflection);
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(canvas.getWidth()).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(canvas.getHeight()).divide(2));
        super.getChildren().add(canvas);
    }
    
    public abstract void update(float[] magnitudes);
    public abstract VisualizationType getType();
    
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
