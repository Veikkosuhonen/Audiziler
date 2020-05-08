/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import audiziler.domain.Settings;
import audiziler.media.visualizer.VisualizationType;
import java.io.IOException;

/**
 * DAO interface for classes handling the storing of <code>Settings</code> objects data
 * @author vesuvesu
 */
public interface SettingDao {
    
    public void setSettings(VisualizationType type, Settings settings);
    
    public Settings getSettings(VisualizationType type);
    
    public void save() throws IOException;
}
