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
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 *
 * @author vesuvesu
 */
public class FlameParticle implements Visualization{
    WindowSize windowSize;
    Group group;
    Canvas canvas;
    GraphicsContext gc;
    boolean visible;
    ArrayList<Particle> particles;
    float rootHeight;
    int bars;
    float barWidth;
    Scale transform;
    
    Settings settings;
    
    public FlameParticle(WindowSize windowSize) {
        this.windowSize = windowSize;
        
        canvas = new Canvas(1280,720);
        
        group = new Group(canvas);
        
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        
        visible = false;
        
        particles = new ArrayList();
        rootHeight = 0.8f * (float) canvas.getHeight();
        bars = 128;
        barWidth = 8;
        canvas.translateXProperty().bind(windowSize.widthProperty().subtract(bars * barWidth).divide(2));
        canvas.translateYProperty().bind(windowSize.heightProperty().subtract(720).divide(2));
        
    }
    @Override
    public void update(float[] magnitudes) {
        if (!visible) {
            return;
        }
        gc.clearRect(0,  0, canvas.getWidth(), canvas.getHeight());
        
        for (int i = 0; i<bars; i++) {
            float mag = magnitudes[i] - (float) settings.get("threshold").getValue();
            if (mag > 0) {
                particles.add(
                        createParticle(mag / 5, i)
                );
            }
        }
        /*
        ArrayList<Particle> removables = new ArrayList();
        for (Particle particle : particles) {
            particle.update();
            gc.setFill(particle.getColor());
            gc.fillRect(particle.getX(), particle.getY(), particle.getStrength(), particle.getStrength());
            if (particle.getAge() > 30) {
                removables.add(particle);
            }
        }
        particles.removeAll(removables);*/
        for (int i = 0; i<particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update();
            gc.setFill(particle.getColor());
            gc.fillRect(particle.getX(), particle.getY(), particle.getStrength(), particle.getStrength());
            if (particle.getAge() > 30) {
                particles.remove(i);
            }
        }
        //System.out.println("Removed: " + removables.size());
        //System.out.println("Particles: " + particles.size());
    }

    @Override
    public Group getVisualization() {
        return group;
    }
    
    private Particle createParticle(float mag, int i) {
        Particle particle = new Particle(
                new Vector2D( i * barWidth, rootHeight ), 
                new Vector2D( 0f, -mag * (float) settings.get("height").getValue() / 2 ), 
                new Vector2D( 0f, 1f - (float) settings.get("acceleration").getValue() / 2),
                (float) settings.get("color offset").getValue()
                + (float) settings.get("magnitude color offset").getValue()*mag / 50
                + i * 1.0f / bars * (float) settings.get("frequency color offset").getValue(),
                mag
        );
        return particle;
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
