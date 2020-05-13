/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.domain.Settings;
import javafx.scene.Group;

/**
 *
 * @author vesuvesu
 */
public interface Visualization {
    public void update(float[] magnitudes);
    public Group getVisualization();
    public void setVisible(boolean visible);
    public void setSettings(Settings settings);
}
