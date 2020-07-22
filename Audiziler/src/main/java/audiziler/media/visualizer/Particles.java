/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.ui.WindowSize;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.transform.Scale;

/**
 *
 * @author vesuvesu
 */
public class Particles extends Visualization {
    ArrayList<Particle> particles;
    float rootHeight;
    int length;
    float barWidth;
    Scale transform;
    float[] controls;
    Random random;
    public Particles(WindowSize windowSize) {
        super(windowSize);
        particles = new ArrayList();
        rootHeight = 0.5f * (float) canvas.getHeight();
        length = 128;
        barWidth = (float) canvas.getWidth() / length;
        controls = new float[8];
        random = new Random();
    }
    @Override
    public void update(float[] magnitudes) {
        if (!this.isVisible()) {
            return;
        }
        updateControls();
        gc.clearRect(0,  0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < length; i++) {
            float mag = magnitudes[i] - controls[0];
            if (mag > 0) {
                particles.add(
                        createParticle(mag / 4, i)
                );
            }
        }
        bloom.setThreshold(controls[3]);
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
                new Vector2D(random.nextFloat() - 0.5f, -mag * controls[2] / 2), 
                new Vector2D(0f, 1f - controls[1] / 2),
                controls[6]
                + controls[4] * mag / 10
                + i * 1.0f / length * controls[5],
                mag,
                controls[7],
                0.97f
        );
        return particle;
    }
    
    @Override
    public VisualizationType getType() {
        return VisualizationType.FLAME;
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
}
