/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Stores a group of <code>Setting</code> objects to be used for different visualization types.
 * @author vesuvesu
 */
public class Settings {
    HashMap<String, Setting> settings;
    
    public Settings() {
        settings = new HashMap();
    }
    
    public void add(String name,Setting setting) {
        settings.put(name, setting);
    }
    
    public Setting get(String name) {
        return settings.get(name);
    }
    
    public List<Setting> getAll() {
        return new ArrayList(settings.values());
    }
}
