/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import audilizer.domain.Settings;

/**
 *
 * @author vesuvesu
 */
public interface SettingDao {
    
    public Settings getSettings(int slot);
    
    public void setSettings(int slot, Settings settings);
    
    public void save() throws Exception;
}
