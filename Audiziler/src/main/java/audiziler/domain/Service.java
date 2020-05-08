/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.domain;

import audiziler.media.MPlayer;
import audiziler.media.visualizer.VisualizationType;
import audiziler.media.Visualizer;
import audiziler.ui.WindowSize;
import java.io.File;
import javafx.scene.layout.Pane;

/**
 * A class to abstract the handling of MPlayer and Visualizer from the user interface classes
 * @author vesuvesu
 */
public class Service {
    private final SettingsService settingsService;
    private final WindowSize windowSize;
    private MPlayer mediaplayer;
    private Visualizer visualizer;
    private File file;
    
    public Service(SettingsService settingsService, WindowSize windowSize) {
        this.settingsService = settingsService;
        this.windowSize = windowSize;
        
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
     * Constructs the <code>MPlayer</code> with given file, 
     * the <code>Visualizer</code> with windowSize and sets the selected file to the one given.
     * @param file
     */
    public void initializeMedia(File file) {
        this.file = file;
        mediaplayer = new MPlayer(file);
        visualizer = new Visualizer(windowSize);
        mediaplayer.setAudioSpectrumListener(
            visualizer.createListener(
                mediaplayer.getBands()
            )
        );
    }
    /**
     * Sets the type of the of the visualization displayed by the <code>Visualizer</code>, 
     * sets the currently active settings to the settings of that type
     * and (re)binds the settings of the <code>MPlayer</code> and the <code>Visualizer</code>
     * to the newly selected settings
     * @param type 
     */
    public void selectVisualization(VisualizationType type) {
        visualizer.setType(type);
        settingsService.setSettings(type);
        mediaplayer.bindSettings(settingsService.getSettings());
        visualizer.bindSettings(settingsService.getSettings());
    }
    /**
     * Toggles the playback of the audio file. 
     * @return a Boolean indicating whether the MPlayer is playing audio after the toggle
     */
    public boolean togglePlayback() {
        if (mediaplayer != null) {
            return mediaplayer.toggle();
        } else {
            return false;
        }
    }
    public void toStart() {
        mediaplayer.toStart();
    }
    public void stop() {
        if (mediaplayer != null) {
            mediaplayer.stop();
        }
        if (visualizer != null) {
            visualizer.clear();
        }
    }
    public void setOnEndOfMedia(Runnable e) {
        mediaplayer.setOnEndOfMedia(e);
    }
    public Settings getSettings() {
        return settingsService.getSettings();
    }
    public Pane getVisualization() {
        return visualizer.getVisualizer();
    }
}
