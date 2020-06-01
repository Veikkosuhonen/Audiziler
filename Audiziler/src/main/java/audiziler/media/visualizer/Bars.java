/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.ui.WindowSize;
import javafx.scene.paint.Color;

/**
 *
 * @author vesuvesu
 */

public class Bars extends Visualization {
    Bar[] bars;
    int length;
    float rootHeight;
    float[] controls;
    public Bars(WindowSize windowSize) {
        super(windowSize);
        length = 128;
        this.windowSize = windowSize;
        bars = new Bar[length];
        float width = (float) canvas.getWidth() / length;
        float barWidth = width * 0.75f;
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setFraction(0.9);
        for (int i = 0; i < length; i++) {
            bars[i] = new Bar(
                    new Vector2D(width * i, rootHeight),
                    new Vector2D(barWidth, 0)
            );
        }
        controls = new float[7];
    }
    @Override
    public void update(float[] magnitudes) {
        if (!this.isVisible()) {
            return;
        }
        updateControls();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        for (int i = 0; i < length; i++) {
            float mag = magnitudes[i];
            float oldHeight = bars[i].getSize().y;
            float newHeight = oldHeight - (oldHeight - height(mag)) / controls[1];
            bars[i].setHeight(newHeight);
            float normalHeight = normalized(newHeight);
            newHeight *= controls[2] / 2;
            bloom.setThreshold(controls[3]);
            gc.setFill(
                    Color.hsb(
                            i * 1.0 / length * controls[4] + 
                            controls[5] * normalHeight +
                            controls[6]
                            , 1.0, normalHeight));
            float x = bars[i].getPos().x;
            float y = rootHeight - newHeight;
            gc.fillRect(x, y, bars[i].getSize().x, newHeight);
        }
    }
    private float normalized(float f) {
        float n = f / 170;
        if (n > 0.99f) {
            n = 0.99f;
        }
        return n;
    }
    private float height(float magnitude) {
        float f = (float) Math.pow(magnitude + 90, 2.3) / 40;
        return f > 0 ? f : 0;
    }
    
    private void updateControls() {
        controls[0] = (float) settings.get("threshold").getValue();
        controls[1] = (float) settings.get("acceleration").getValue();
        controls[2] = (float) settings.get("height").getValue();
        controls[3] = (float) settings.get("bloom").getValue();
        controls[4] = (float) settings.get("frequency color offset").getValue();
        controls[5] = (float) settings.get("magnitude color offset").getValue();
        controls[6] = (float) settings.get("color offset").getValue();
    }
    
    @Override
    public VisualizationType getType() {
        return VisualizationType.BARS;
    }
}
