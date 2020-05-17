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
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;

/**
 *
 * @author Veikko
 */
public class Areas implements Visualization {
    
    WindowSize windowSize;
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    Reflection reflection;
    boolean visible;
    Settings settings;
    float[] heights;
    int length;
    int startingOffset;
    float width;
    float rootHeight;
    float centerX;
    float[] controls;
    int areaCount;
    int areaLength;
    
    public Areas(WindowSize windowSize) {
        
        canvas = new Canvas();
        canvas.widthProperty().setValue(1920);
        canvas.heightProperty().setValue(1080);
        canvas.setCache(true);
        gc = canvas.getGraphicsContext2D();
        //gc.setGlobalBlendMode(BlendMode.SOFT_LIGHT);
        bloom = new Bloom();
        reflection = new Reflection();
        reflection.setInput(bloom);
        canvas.setEffect(reflection);
        visible = false;
        length = 256;
        this.windowSize = windowSize;
        heights = new float[length];
        areaCount = 16;
        areaLength = length / areaCount;
        width = (float) canvas.getWidth() / areaLength / 2;
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        reflection.setFraction(0.9);
        centerX = 0.5f * (float) canvas.getWidth();
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(canvas.getWidth()).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(canvas.getHeight()).divide(2));
        controls = new float[7];
        group = new Group(canvas);
    }
    
    @Override
    public void update(float[] magnitudes) {
        if (!visible) {
            return;
        }
        updateControls();
        bloom.setThreshold(controls[3]);
        float heightMult = controls[2] / 2;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        for ( int j = 0; j < areaCount - 2; j++ ) {
            gc.setFill(Color.hsb(
                (1 + 1.0 * j / areaCount) * controls[5] + controls[6],
                1.0,
                1.0 - (controls[4] / 360) + ((controls[4] / 360) / areaCount) * j
            ));
            gc.beginPath();
            for (int i = areaLength - 1; i >= 0; i--) {
                float oldHeight = heights[i + j * areaLength];
                float newHeight = oldHeight - (oldHeight - (height(magnitudes[i + j * areaLength]))) / controls[1];
                heights[i + j * areaLength] = newHeight;
                float x = centerX - width * i;
                float y = rootHeight - newHeight * heightMult;
                gc.lineTo(x, y);
            }
            gc.lineTo(centerX, rootHeight);
            for (int i = 0; i < areaLength; i++) {
                float x = centerX + width * i;
                float y = rootHeight - heights[i + j * areaLength] * heightMult;
                gc.lineTo(x, y);
            }
            gc.lineTo(centerX + width * length, rootHeight);
            gc.lineTo(centerX - width * length, rootHeight);
            gc.fill();
            gc.closePath();
        }
    }

    @Override
    public Group getVisualization() {
        return group;
    }

    @Override
    public void setVisible(boolean visible) {
        group.setVisible(visible);
        this.visible = visible;
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    private void updateControls() {
        controls[0] = (float) settings.get("threshold").getValue();
        controls[1] = (float) settings.get("acceleration").getValue();
        controls[2] = (float) settings.get("height").getValue();
        controls[3] = (float) settings.get("bloom").getValue();
        controls[4] = (float) settings.get("magnitude color offset").getValue();
        controls[5] = (float) settings.get("frequency color offset").getValue();
        controls[6] = (float) settings.get("color offset").getValue();
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
    
}
