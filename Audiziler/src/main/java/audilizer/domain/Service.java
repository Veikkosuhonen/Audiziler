/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import audilizer.media.MPlayer;
import audilizer.media.Visualizer;
import java.io.File;
import javafx.scene.layout.Pane;

/**
 * A class to abstract the handling of MPlayer and Visualizer from the user interface classes
 * @author vesuvesu
 */
public class Service {
    private final SettingsService settingsService;
    private final FileManager manager;
    private MPlayer mediaplayer;
    private Visualizer visualizer;
    private File file;
    
    public Service(SettingsService settingsService, FileManager manager) {
        this.manager = manager;
        this.settingsService = settingsService;
    }
    /**
     * Adds a file to FileManager's list
     * @param file to be added
     * @return a Boolean indicating whether file was valid and added successfully
     */
    public boolean add(File file) {
        return manager.add(file);
    }
    /**
     * Removes a file with the given name if it exists
     * @param name 
     */
    public void remove(String name) {
        manager.remove(name);
    }
    /**
     * 
     * @param name
     * @return a Boolean indicating whether a file with a matching name is selected
     */
    public boolean isSelected(String name) {
        if (file == null) {
            return false;
        }
        return file.getName().equals(name);
    }
    /**
     * retrieves a file with a matching name from FileManager and selects it
     * @param name
     * @return a Boolean indicating whether the file was found
     */
    public boolean selectFile(String name) {
        file = manager.getFile(name);
        return file != null;
    }
    /**
     * Constructs an MPlayer with the currently selected file and settings, and constructs a Visualizer.
     * Creates an AudioSpectrumListener from the Visualizer and sets it to the MPlayer.
     */
    public void initializeMedia() {
        mediaplayer = new MPlayer(file, settingsService.getSettings());
        visualizer = new Visualizer(settingsService.getSettings());
        mediaplayer.setAudioSpectrumListener(
            visualizer.createListener(
                mediaplayer.getBands()
            )
        );
    }
    /**
     * Toggles the playback of the audio file. 
     * @return a Boolean indicating whether the MPlayer is playing audio after the toggle
     */
    public boolean togglePlayback() {
        return mediaplayer.toggle();
    }
    public void toStart() {
        mediaplayer.toStart();
    }
    public Pane getVisualization() {
        return visualizer.getVisualizer();
    }
    public void setOnEndOfMedia(Runnable e) {
        mediaplayer.setOnEndOfMedia(e);
    }
    public void stop() {
        if (mediaplayer != null) {
            mediaplayer.stop();
        }
        if (visualizer != null) {
            visualizer.clear();
        }
    }
}
