/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.dao.SettingDao;
import audiziler.media.visualizer.VisualizationType;
import java.io.IOException;

/**
 * Abstracts the handling of <code>Settings</code> objects and interfaces with <code>SettingDao</code>
 * @author vesuvesu
 */
public class SettingsService {
    Settings settings;
    SettingDao settingsdao;
    
    public SettingsService(SettingDao settingdao) {
        this.settingsdao = settingdao;
        settings = settingdao.getSettings(VisualizationType.BARS);
    }
    public void save() throws IOException {
        settingsdao.save();
    }
    public void setSettings(VisualizationType type) {
        settings = settingsdao.getSettings(type);
    }
    public Setting getSetting(String name) {
        return settings.get(name);
    }
    public Settings getSettings() {
        return settings;
    }
}
