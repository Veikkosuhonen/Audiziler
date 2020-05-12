/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import audiziler.ui.WindowSize;
import java.util.Arrays;
import java.util.stream.IntStream;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;

/**
 *
 * @author vesuvesu
 */

public class Symmetric implements Visualization {
    WindowSize windowSize;
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    Reflection reflection;
    boolean visible;
    Settings settings;
    float[] heights;
    float[] offsetter;
    int length;
    float width;
    float rootHeight;
    float centerX;
    
    public Symmetric(WindowSize windowSize) {
        canvas = new Canvas();
        canvas.widthProperty().setValue(1920);
        canvas.heightProperty().setValue(1080);
        group = new Group(canvas);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        reflection = new Reflection();
        reflection.setInput(bloom);
        canvas.setEffect(reflection);
        visible = false;
        length = 256;
        offsetter = offsettingMap(length);
        this.windowSize = windowSize;
        heights = new float[length];
        width = 4;
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        centerX = 0.5f * (float) canvas.getWidth();
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(canvas.getWidth()).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(canvas.getHeight()).divide(2));
    }
    @Override
    public void update(float[] magnitudes) {
        if (!visible) {
            return;
        }
        bloom.setThreshold(settings.get("bloom").getValue());
        double heightMult = settings.get("height").getValue() / 2;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        gc.setStroke(Color.hsb(
                normalized( (float) avg(Arrays.copyOf(magnitudes, length))) * settings.get("magnitude color offset").getValue() + settings.get("color offset").getValue(),
                1.0,
                1.0
        ));
        gc.beginPath();
        for (int i = length - 1; i >= 0; i--) {
            float oldHeight = heights[i];
            float newHeight = oldHeight - (oldHeight - height(magnitudes[i])) / (float) settings.get("acceleration").getValue();
            heights[i] = newHeight;
            newHeight *= heightMult;
            float x = centerX - width * offsetter[i];
            float y = rootHeight - newHeight;
            gc.lineTo(x, y);
        }
        for (int i = 0; i < length; i++) {
            float x = centerX + width * offsetter[i];
            float y = rootHeight - heights[i] * (float) heightMult;
            gc.lineTo(x, y);
        }
        gc.stroke();
        gc.closePath();
    }
    private float normalized(float f) {
        float n = f / 100;
        if (n > 0.99f) {
            n = 0.99f;
        }
        return n;
    }
    private float height(float magnitude) {
        float f = (float) Math.pow(magnitude + 90, 2.3) / 40;
        return f > 0 ? f : 0;
    }
    
    private double avg(float[] array) {
        return IntStream.range(0, array.length).mapToDouble(i -> array[i]).parallel().reduce(0.0, (a, b) -> a + b) / array.length; 
    }
    
    private float[] offsettingMap(int length) {
        float[] map = new float[length];
        for (int i = 0; i < length; i++) {
            //made in geogebra:
            map[i] = i * ((float) Math.pow((i - 128), 2)/ 10_000.0f + 1);
        }
        return map;
    }

    @Override
    public Canvas getVisualization() {
        return canvas;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        canvas.setVisible(visible);
    }
    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
