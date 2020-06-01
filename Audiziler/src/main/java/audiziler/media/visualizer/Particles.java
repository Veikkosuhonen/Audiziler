/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.ui.WindowSize;
import java.util.ArrayList;
import javafx.scene.transform.Scale;

/**
 *
 * @author vesuvesu
 */
public class Particles extends Visualization {
    ArrayList<Particle> particles;
    float rootHeight;
    int bars;
    float barWidth;
    Scale transform;
    
    public Particles(WindowSize windowSize) {
        super(windowSize);
        particles = new ArrayList();
        rootHeight = 0.5f * (float) canvas.getHeight();
        reflection.setTopOffset(-2*rootHeight);
        reflection.setTopOpacity(1.0);
        reflection.setBottomOpacity(0.0);
        bars = 128;
        barWidth = 10;
    }
    @Override
    public void update(float[] magnitudes) {
        if (!this.isVisible()) {
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
    public VisualizationType getType() {
        return VisualizationType.FLAME;
    }
}
