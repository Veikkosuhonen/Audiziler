/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.ui.WindowSize;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author Veikko
 */
public class Areas extends Visualization {
    
    float[] heights;
    int length;
    int startingOffset;
    float width;
    float rootHeight;
    float centerX;
    float[] controls;
    int areaCount;
    int areaLength;
    public Areas(WindowSize windowSize)  {
        super(windowSize);
        length = 256;
        heights = new float[length];
        areaCount = 16;
        areaLength = length / areaCount;
        width = (float) canvas.getWidth() / areaLength / 2;
        rootHeight = 0.5f * (float) canvas.getHeight();
        centerX = 0.5f * (float) canvas.getWidth();
        controls = new float[8];
    }
    
    @Override
    public void update(float[] magnitudes) {
        if (!this.isVisible()) {
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
                1.0 - (controls[4] / 360) + ((controls[4] / 360) / areaCount) * j,
                controls[7]
            ));
            gc.setStroke(Color.hsb(
                (1 + 1.0 * j / areaCount) * controls[5] + controls[6],
                1.0,
                1.0
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
            for (int i = 0; i < areaLength; i++) {
                float x = centerX + width * i;
                float y = rootHeight - heights[i + j * areaLength] * heightMult;
                gc.lineTo(x, y);
            }
            gc.lineTo(centerX + width * length, rootHeight);
            gc.lineTo(centerX - width * length, rootHeight);
            gc.fill();
            gc.stroke();
            gc.closePath();
        }
    }
    
    private void updateControls() {
        controls[0] = (float) settings.get("threshold").getValue();
        controls[1] = (float) settings.get("acceleration").getValue();
        controls[2] = (float) settings.get("height").getValue();
        controls[3] = (float) settings.get("bloom").getValue();
        controls[4] = (float) (settings.get("magnitude color offset").getValue());
        controls[5] = (float) settings.get("frequency color offset").getValue();
        controls[6] = (float) settings.get("color offset").getValue() + low;
        controls[7] = (float) settings.get("opacity").getValue();
    }
    
    
    private float height(float magnitude) {
        float f = (float) Math.pow(magnitude + 90, 2.3) / 40;
        return f > 0 ? f : 0;
    }
    
    @Override
    public VisualizationType getType() {
        return VisualizationType.AREAS;
    }
}
