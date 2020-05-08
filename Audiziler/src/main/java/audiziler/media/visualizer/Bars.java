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
import javafx.scene.paint.Color;

/**
 *
 * @author vesuvesu
 */

public class Bars implements Visualization {
    WindowSize windowSize;
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    boolean visible;
    Settings settings;
    Bar[] bars;
    int length;
    float rootHeight;
    public Bars(WindowSize windowSize) {
        canvas = new Canvas();
        canvas.widthProperty().setValue(1280);
        canvas.heightProperty().setValue(720);
        group = new Group(canvas);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        canvas.setEffect(bloom);
        visible = false;
        length = 128;
        this.windowSize = windowSize;
        bars = new Bar[length];
        float width = 8;
        float barWidth = 6;
        rootHeight = 0.8f * (float) canvas.getHeight();
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(width * length).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(720).divide(2));
        for (int i = 0; i < length; i++) {
            bars[i] = new Bar(
                    new Vector2D(width * i, rootHeight),
                    new Vector2D(barWidth, 0)
            );
        }
    }
    @Override
    public void update(float[] magnitudes) {
        if (!visible) {
            return;
        }
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        for (int i = 0; i < length; i++) {
            float oldHeight = bars[i].getSize().y;
            float newHeight = oldHeight - (oldHeight - height(magnitudes[i])) / (float) settings.get("acceleration").getValue();
            bars[i].setHeight(newHeight);
            float normalHeight = normalized(newHeight);
            newHeight *= settings.get("height").getValue() / 2;
            bloom.setThreshold(settings.get("bloom").getValue());
            gc.setFill(
                    Color.hsb(
                            i * 1.0 / length * settings.get("frequency color offset").getValue() + 
                            settings.get("magnitude color offset").getValue() * normalHeight +
                            settings.get("color offset").getValue()
                            , 1.0, normalHeight + 0.1));
            float x = bars[i].getPos().x;
            float y = rootHeight - newHeight;
            gc.fillRect(x, y, bars[i].getSize().x, newHeight);
        }
    }
    private float normalized(float f) {
        float n = f / 100;
        if (n > 0.9f) {
            n = 0.9f;
        }
        return n;
    }
    private float height(float magnitude) {
        float f = (float) Math.pow(magnitude + 90, 2.3) / 40;
        return f > 0 ? f : 0;
    }

    @Override
    public Group getVisualization() {
        return group;
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

    @Override
    public void update(float[] magnitudes, float[] phases) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
