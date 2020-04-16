/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import audilizer.media.MPlayer;
import audilizer.media.Visualizer;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 *
 * @author vesuvesu
 */
public class Service {
    FileManager manager;
    MPlayer mediaplayer;
    Visualizer visualizer;
    File file;
    public Service() {
        manager = new FileManager();
    }
    public boolean add(File file) {
        return manager.add(file);
    }
    public boolean selectFile(String name) {
        file = manager.getFile(name);
        return file != null;
    }
    public void initializeMedia(Scene scene) {
        mediaplayer = new MPlayer(file);
        visualizer = new Visualizer();
        mediaplayer.setAudioSpectrumListener(
            visualizer.createListener(
                mediaplayer.getBands()
            )
        );
    }
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
