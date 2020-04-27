/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import audiziler.dao.SettingDao;

/**
 *
 * @author vesuvesu
 */
public class SettingsService {
    Settings settings;
    SettingDao settingsdao;
    
    public SettingsService(SettingDao settingdao) {
        this.settingsdao = settingdao;
        settings = settingdao.getSettings(0);
    }
    public void setSettings(int slot) {
        settings = settingsdao.getSettings(slot);
    }
    public Setting getSetting(String name) {
        return settings.get(name);
    }
    public Settings getSettings() {
        return settings;
    }
}
