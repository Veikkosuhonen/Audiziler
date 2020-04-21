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
        
        //Settings defined here (name, desc, defaultvalue, min, max)
        
        settings.put("threshold", new Setting("threshold", "spectrum threshold",-90.0, -110, -60));
        settings.put("color offset", new Setting("color offset", "how much color is offset", 0, 0, 360));
        settings.put("frequency color offset", new Setting("frequency color offset", "how much color is offset based on frequency", 144, 0, 360));
        settings.put("magnitude color offset", new Setting("magnitude color offset", "how much color is offset based on magnitude", 86, 0, 360));
        settings.put("acceleration", new Setting("acceleration", "how fast bars move", 4, 1, 10));
        settings.put("height", new Setting("height", "height of bars", 1, 0.5, 5));
        settings.put("bloom", new Setting("bloom", "bloom threshold (lower means more bloom)", 0.3, 0, 1.0));
        
    }
    public void add(String name,Setting setting) {
        settings.put(name, setting);
    }
    public Setting get(String name) {
        return settings.get(name);
    }
}
