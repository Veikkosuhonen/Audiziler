/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import audiziler.ui.WindowSize;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.transform.Scale;

/**
 *
 * @author vesuvesu
 */
public class Particles implements Visualization {
    WindowSize windowSize;
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    Bloom bloom;
    Reflection reflection;
    boolean visible;
    ArrayList<Particle> particles;
    float rootHeight;
    int bars;
    float barWidth;
    Scale transform;
    Settings settings;
    
    public Particles(WindowSize windowSize) {
        this.windowSize = windowSize;
        canvas = new Canvas(1920, 1080);
        gc = canvas.getGraphicsContext2D();
        bloom = new Bloom();
        reflection = new Reflection();
        reflection.setInput(bloom);
        canvas.setEffect(reflection);
        visible = false;
        particles = new ArrayList();
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        bars = 128;
        barWidth = 10;
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(bars * barWidth).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(canvas.getHeight()).divide(2));
        group = new Group(canvas);
    }
    @Override
    public void update(float[] magnitudes) {
        if (!visible) {
            return;
        }
        gc.clearRect(0,  0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < bars; i++) {
            float mag = magnitudes[i] - (float) settings.get("threshold").getValue();
            if (mag > 0) {
                particles.add(
                        createParticle(mag / 5, i)
                );
            }
        }
        bloom.setThreshold(settings.get("bloom").getValue());
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update();
            gc.setFill(particle.getColor());
            gc.fillRect(particle.getX(), particle.getY(), particle.getStrength(), particle.getStrength());
            if (particle.getAge() > 70) {
                particles.remove(i);
            }
        }
    }

    @Override
    public Group getVisualization() {
        return group;
    }
    
    private Particle createParticle(float mag, int i) {
        Particle particle = new Particle(
                new Vector2D(i * barWidth, rootHeight), 
                new Vector2D(0f, -mag * (float) settings.get("height").getValue() / 2), 
                new Vector2D(0f, 1f - (float) settings.get("acceleration").getValue() / 2),
                (float) settings.get("color offset").getValue()
                + (float) settings.get("magnitude color offset").getValue() * mag / 10
                + i * 1.0f / bars * (float) settings.get("frequency color offset").getValue(),
                mag
        );
        return particle;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        group.setVisible(visible);
    }
    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
