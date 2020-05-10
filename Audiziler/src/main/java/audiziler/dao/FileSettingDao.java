/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.dao;

import audiziler.domain.Setting;
import audiziler.domain.Settings;
import audiziler.media.visualizer.VisualizationType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static java.util.stream.Collectors.toList;

/**
 * Reads and writes the <code>Settings</code> objects data from and to a text file
 * @author vesuvesu
 */
public class FileSettingDao implements SettingDao {
    private String filepath;
    private Map<VisualizationType, Settings> settingsList;
    private ArrayList<String> defaultSettingNames;
    /**
     * Attempts to parse a settings file and create a Map containing the <code>Settings</code> objects
     * @param filepath to the settings file
     * @throws IOException 
     */
    public FileSettingDao(String filepath, String defaultNames) throws IOException {
        this.filepath = filepath;
        settingsList = new HashMap();
        defaultSettingNames = parseDefaultNames(defaultNames);
        for (VisualizationType type : VisualizationType.values()) {
            settingsList.put(type, new Settings());
        }
        int counter = 0;
        while (true) {
            try {
                readSettings();
                validate(settingsList.values());
                break;
            } catch (FileNotFoundException e) {
                System.out.println("file '" + filepath + "' does not exist, creating");
                System.out.println("writing default settings");
                writeDefaultSettings();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                System.out.println("writing default settings");
                writeDefaultSettings();
            }
            if (counter > 2) {
                System.out.println("Exception while reading setting files. Program will exit");
                System.exit(0);
            }
            counter++;
        }
    }
    /**
     * Writes the settings data to the settings file in a somewhat readable format
     * @throws IOException 
     */
    @Override
    public void save() throws IOException {
        try (FileWriter writer = new FileWriter(new File(filepath))) {
            for (VisualizationType type : VisualizationType.values()) {
                writer.write("type;" + type + "\n");
                
                Settings settings = settingsList.get(type);
                
                for (Setting setting : settings.getAll()) {
                    writer.write(setting.getName() + ";" + setting.getDescription() + ";" + setting.getValue() + ";" + setting.getMin() + ";" + setting.getMax() + "\n");
                }
            }
            writer.close();
        }
    }
    /**
     * 
     * @param type
     * @return <code>Settings</code> for the given type
     */
    @Override
    public Settings getSettings(VisualizationType type) {
        return settingsList.get(type);
    }
    @Override
    public void setSettings(VisualizationType type, Settings settings) {
        settingsList.replace(type, settings);
    }
    /**
     * Reads the settings file, creates the <code>Settings</code> objects and puts them into the HashMap
     * @throws Exception 
     */
    private void readSettings() throws Exception {
        Scanner reader = new Scanner(new File(filepath));
        VisualizationType type = VisualizationType.BARS;
        if (!reader.hasNextLine()) {
            throw new Exception();
        }
        while (reader.hasNextLine()) {
            String[] parts = reader.nextLine().split(";");
            if (parts[0].startsWith("#")) {
                //skip lines starting with #
                continue;
            }
            if (parts[0].equals("type")) {
                type = VisualizationType.valueOf(parts[1]);
                continue;
            }
            settingsList.get(type).add(parts[0], createSetting(parts));
        }
    }
    /**
     * Create a new <code>Setting</code> object from a String array containing the fields.
     * @param String array
     * @return Setting
     */
    private Setting createSetting(String[] parts) {
        String name = parts[0];
        String description = parts[1];
        double value = Double.valueOf(parts[2]);
        double min = Double.valueOf(parts[3]);
        double max = Double.valueOf(parts[4]);
        Setting setting = new Setting(name, description, value, min, max);
        return setting;
    }
    /**
     * Writes the program default settings data to a new settings file. 
     * Should be called whenever the settings file cannot be found or is fatally malformed
     */
    private void writeDefaultSettings() {
        File file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (FileWriter writer = new FileWriter(filepath)) {
            for (VisualizationType type : VisualizationType.values()) {
                //DEFAULT SETTINGS
                writer.write("type;" + type + "\n");
                writer.write(defaultSettingNames.get(0) + ";spectrum threshold;-90.0;-110;-60\n");
                writer.write(defaultSettingNames.get(1) + ";how much color is offset;0;0;360\n");
                writer.write(defaultSettingNames.get(2) + ";how much color is offset based on frequency;144;0;360\n");
                writer.write(defaultSettingNames.get(3) + ";how much color is offset based on magnitude;86;0;360\n");
                writer.write(defaultSettingNames.get(4) + ";how fast bars move;4;1;10\n");
                writer.write(defaultSettingNames.get(5) + ";height of bars;1;0.5;8\n");
                writer.write(defaultSettingNames.get(6) + ";bloom strength;0.3;0;1\n");
                writer.write(defaultSettingNames.get(7) + ";controls the spectrum analyzer interval;41.1;41;41.2\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private ArrayList<String> parseDefaultNames(String defaultNames) {
        ArrayList<String> list = new ArrayList();
        String[] names = defaultNames.split(";");
        for (String name : names) {
            list.add(name);
        }
        return list;
    }
    
    private void validate(Collection<Settings> newSettingsList) throws Exception {
        //Check each instance of Settings in the newSettingsList
        for (Settings settings : newSettingsList) {
            
            //Create a list of setting names in the Settings
            List<String> newSettingNames = settings.getAll().stream().map(setting -> setting.getName()).collect(toList());
            
            //Check for each default name that it's in the new list
            for (String settingName : defaultSettingNames) {
                if (!newSettingNames.contains(settingName)) {
                    throw new Exception("Invalid settings: missing '" + settingName + "'");
                }
            }
        }
    }
}
