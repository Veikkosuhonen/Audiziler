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

public class Symmetric extends Visualization {
    float[] heights;
    float[] lazyHeights;
    float[] offsetter;
    int length;
    float width;
    float rootHeight;
    float centerX;
    float[] controls;
    
    public Symmetric(WindowSize windowSize) {
        super(windowSize);
        length = 256;
        offsetter = offsettingMap(length);
        heights = new float[length];
        lazyHeights = new float[length];
        width = 4;
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        reflection.setFraction(0.9);
        centerX = 0.5f * (float) canvas.getWidth();
        controls = new float[6];
    }
    
    @Override
    public void update(float[] magnitudes) {
        if (!this.isVisible()) {
            return;
        }
        updateControls();
        bloom.setThreshold(controls[3]);
        double heightMult = controls[2] / 2;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        float avg = normalized( (float) avg(Arrays.copyOf(magnitudes, length)));
        
        gc.setStroke(Color.hsb(
                avg * controls[4] + controls[5],
                1.0,
                1.0
        ));
        gc.setLineWidth(avg * 6);
        
        gc.beginPath();
        for (int i = length - 1; i >= 0; i--) {
            float oldHeight = heights[i];
            float newHeight = oldHeight - (oldHeight - (height(magnitudes[i]))) / controls[1];
            heights[i] = newHeight;
            lazyHeights[i] = newHeight > lazyHeights[i] ? newHeight : lazyHeights[i] * 0.99f;
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
        
        gc.setStroke(Color.hsb(
                avg * controls[4] * 0.9 + controls[5],
                1.0,
                0.3
        ));
        gc.setLineWidth(avg * 1);
        
        gc.beginPath();
        for (int i = length - 1; i >= 0; i--) {
            float x = centerX - width * offsetter[i];
            float y = rootHeight - lazyHeights[i] * (float) heightMult;
            gc.lineTo(x, y);
        }
        for (int i = 0; i < length; i++) {
            float x = centerX + width * offsetter[i];
            float y = rootHeight - lazyHeights[i] * (float) heightMult;
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
    
    private void updateControls() {
        controls[0] = (float) settings.get("threshold").getValue();
        controls[1] = (float) settings.get("acceleration").getValue();
        controls[2] = (float) settings.get("height").getValue();
        controls[3] = (float) settings.get("bloom").getValue();
        controls[4] = (float) settings.get("magnitude color offset").getValue();
        controls[5] = (float) settings.get("color offset").getValue();
    }

    @Override
    public VisualizationType getType() {
        return VisualizationType.SYMMETRIC;
    }
}
