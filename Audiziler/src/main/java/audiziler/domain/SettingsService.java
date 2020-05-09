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
    VisualizationType type;
    public SettingsService(SettingDao settingdao) {
        this.settingsdao = settingdao;
        type = VisualizationType.BARS;
        settings = settingdao.getSettings(type);
    }
    public void save() throws IOException {
        settingsdao.setSettings(this.type, settings);
        settingsdao.save();
    }
    public void setSettings(VisualizationType type) {
        settingsdao.setSettings(this.type, settings);
        this.type = type;
        settings = settingsdao.getSettings(type);
    }
    public Setting getSetting(String name) {
        return settings.get(name);
    }
    public Settings getSettings() {
        return settings;
    }
}
