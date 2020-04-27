/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import java.util.HashMap;

/**
 *
 * @author vesuvesu
 */
public class Settings {
    HashMap<String, Setting> settings;
    
    public Settings() {
        settings = new HashMap();
        
        //Default settings defined here (name, desc, defaultvalue, min, max)
        
        
    }
    public void add(String name,Setting setting) {
        settings.put(name, setting);
    }
    public Setting get(String name) {
        return settings.get(name);
    }
    public Setting[] getAll() {
        Setting[] array = new Setting[settings.size()];
        settings.values().toArray(array);
        return array;
    }
}
