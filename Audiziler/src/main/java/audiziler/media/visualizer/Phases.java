/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import audiziler.ui.WindowSize;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;

/**
 *
 * @author vesuvesu
 */
public class Phases implements Visualization {
    WindowSize windowSize;
    Settings settings;
    
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    
    boolean visible;
    int bars;
    int lines;
    float rootHeight;
    float barWidth;
    
    
    List<ArrayDeque<Float>> phaseQueues;
    
    public Phases(WindowSize windowSize) {
        this.windowSize = windowSize;
        canvas = new Canvas();
        canvas.widthProperty().setValue(1280);
        canvas.heightProperty().setValue(720);
        group = new Group(canvas);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        canvas.setEffect(bloom);
        visible = false;
        rootHeight = (float) windowSize.getHeight() / 2;
        bars = 32;
        barWidth = (float) canvas.getWidth() / bars;
        lines = 1;
        phaseQueues = new ArrayList(lines);
        for (int i = 0; i < lines; i++) {
            ArrayDeque<Float> queue = new ArrayDeque(bars);
            for (int j = 0; j < bars; j++) {
                queue.add(0f);
            }
            phaseQueues.add(queue);
        }
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(bars * barWidth).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(720).divide(2));
        
    }
    @Override
    public void update(float magnitudes[], float[] phases) {
        if (!visible) {
            return;
        }
        //System.out.println("Drawing phases");
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.WHITE);
        for (int j = 0; j < lines; j++) {
            bloom.setThreshold(settings.get("bloom").getValue());
            ArrayDeque<Float> queue = phaseQueues.get(j);
            gc.beginPath();
            Iterator<Float> iterator = queue.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                float phase = iterator.next();
                //gc.fillRect(barWidth * i, 80 * phase + rootHeight, 2, 2);
                gc.lineTo(barWidth * i, phase + rootHeight);
                i++;
            }
            gc.stroke();
            gc.closePath();
            queue.remove();
            queue.add(phases[2 + j] * (magnitudes[2 + j] + (float) settings.get("threshold").getValue()) * (float) settings.get("height").getValue() / 4);
        }
    }

    @Override
    public Group getVisualization() {
        return group;
    }

    @Override
    public void setVisible(boolean visible) {
        canvas.setVisible(visible);
        this.visible = visible;
    }
    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void update(float[] magnitudes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
