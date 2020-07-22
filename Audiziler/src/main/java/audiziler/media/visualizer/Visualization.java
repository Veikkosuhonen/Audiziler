/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import audiziler.ui.WindowSize;
import java.util.function.BiFunction;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    DisplacementMap map;
    Settings settings;
    float low;
    float mid;
    float high;
    public Visualization(WindowSize windowSize) {
        super();
        this.windowSize = windowSize;
        canvas = new Canvas();
        //canvas.widthProperty().bind(windowSize.widthProperty());
        //canvas.heightProperty().bind(windowSize.heightProperty());
        canvas.setWidth(2560);
        canvas.setHeight(1440);
        canvas.setCache(true);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        canvas.setEffect(bloom);
        reflection = new Reflection();
        //reflection.topOffsetProperty().bind(canvas.heightProperty().divide(2));
        //reflection.setTopOffset(canvas.getHeight() / -2);
        reflection.setTopOffset(-canvas.getHeight());
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        reflection.setFraction(0.9);
        map = displacementMap();
        map.setInput(reflection);
        this.setEffect(map);
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(canvas.getWidth()).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(canvas.getHeight()).divide(2));
        
        Image image = new Image(getClass().getResource("/images/sunset.jpg").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setCache(true);
        imageView.setSmooth(true);
        imageView.fitHeightProperty().bind(windowSize.heightProperty().divide(2));
        imageView.fitWidthProperty().bind(windowSize.widthProperty());
        
        super.getChildren().addAll(imageView, canvas);
    }
    
    public abstract void update(float[] magnitudes);
    public abstract VisualizationType getType();
    
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    public void updateDynamics(DynamicSetting[] dynamics) {
        low = dynamics[0].getValue();
        mid = dynamics[1].getValue();
        high = dynamics[2].getValue();
    }
    
    private DisplacementMap displacementMap() {
        
        PerlinNoise noise = new PerlinNoise(69,1,0.005,1,8);
        
        FloatMap floatMap = new FloatMap((int)this.windowSize.getWidth(), (int)this.windowSize.getHeight());
        for (int y = (int)this.windowSize.getHeight()/2; y < (int)this.windowSize.getHeight(); y++) {
            for (int x = 0; x < (int)this.windowSize.getWidth(); x++) {
                double h = noise.getHeight(x,y);
                int mult = 8;
                float dx = (float) (Math.cos(h) / this.windowSize.getWidth()) * mult;
                float dy = (float) (Math.sin(h) / (this.windowSize.getHeight() / 2))* mult;
                floatMap.setSamples(x, y, dx, dy);
            }
        }
        return new DisplacementMap(floatMap);
    }
    
}
