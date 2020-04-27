/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import audilizer.domain.Setting;
import audilizer.domain.Settings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vesuvesu
 */
public class FileSettingDao implements SettingDao {
    private String filepath;
    private List<Settings> settingsList;
    private final int SETTINGS_SLOTS = 4;
    
    public FileSettingDao(String filepath) throws IOException {
        //System.out.println(filepath);
        this.filepath = filepath;
        settingsList = new ArrayList();
        
        int i = 0;
        while (i<SETTINGS_SLOTS) {
            settingsList.add(new Settings());
            i++;
        }
        
        try {
            readSettings();
        } catch (FileNotFoundException e) {
            System.out.println("file '"+filepath+"' does not exist,");
            System.out.println("writing default settings");
            writeDefaultSettings();
            try {
                readSettings();
            } catch (Exception e2) {
                System.out.println("Something went wrong while writing default settings");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("writing default settings");
            writeDefaultSettings();
            try {
                readSettings();
            } catch (Exception e2) {
                System.out.println("Something went wrong while writing default settings");
            }
        }
    }
    
    @Override
    public void save() throws IOException {
        try (FileWriter writer = new FileWriter(new File(filepath))) {
            int slot = 0;
            for (Settings settings : settingsList) {
                writer.write("slot;" + slot + "\n");
                for (Setting setting : settings.getAll()) {
                    writer.write(setting.getName() + ";" +setting.getDescription() + ";" + setting.getValue() + ";" + setting.getMin() + ";" + setting.getMax() + "\n");
                }
                slot++;
            }
        }
    }
    
    @Override
    public Settings getSettings(int slot) {
        if (slot < 0 || slot >= SETTINGS_SLOTS) {
            System.out.println("invalid settings slot requested: " + slot + ", returning default");
            return settingsList.get(0);
        }
        return settingsList.get(slot);
    }

    @Override
    public void setSettings(int slot, Settings settings) {
        settingsList.set(slot, settings);
    }
    private void readSettings() throws Exception {
        Scanner reader = new Scanner(new File(filepath));
        int slot = 0;
        if (!reader.hasNextLine()) {
            System.out.println("settings file seems empty");
            throw new Exception();
        }
        while (reader.hasNextLine()) {
            String parts[] = reader.nextLine().split(";");
            if (parts[0].equals("slot")) {
                slot = Integer.valueOf(parts[1]);
                continue;
            }
            String name = parts[0];
            String description = parts[1];
            double value = Double.valueOf(parts[2]);
            double min = Double.valueOf(parts[3]);
            double max = Double.valueOf(parts[4]);
            Setting setting = new Setting(name, description, value, min, max);
            settingsList.get(slot).add(name, setting);
        }
    }
    private void writeDefaultSettings() {
        File file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (FileWriter writer = new FileWriter(filepath)) {
            int slot = 0;
            
            while (slot < SETTINGS_SLOTS) {
                
                //DEFAULT SETTINGS
                writer.write("slot;" + slot + "\n");
                writer.write("threshold;spectrum threshold;-90.0;-110;-60\n");
                writer.write("color offset;how much color is offset;0;0;360\n");
                writer.write("frequency color offset;how much color is offset based on frequency;144;0;360\n");
                writer.write("magnitude color offset;how much color is offset based on magnitude;86;0;360\n");
                writer.write("acceleration;how fast bars move;4;1;10\n");
                writer.write("height;height of bars;1;0.5;8\n");
                writer.write("bloom;bloom strength;0.3;0;1\n");
                slot++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
