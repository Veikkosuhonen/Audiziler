/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import audilizer.media.MPlayer;
import audilizer.media.Visualizer;
import audilizer.domain.Service;
import audilizer.domain.Setting;
import audilizer.domain.SettingsService;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author vesuvesu
 */
public class Player {
    
    SettingsService settingsService;
    Service playbackService;
    MPlayer mediaplayer;
    Visualizer visualizer;

    File file;
    FileChooser filechooser;
    
    Stage stage;
    Scene scene;
    BorderPane borderLayout;
    StackPane finalLayout;
    Button addFile;
    Button play;
    Button previous;
    ImageView playIcon;
    ImageView pauseIcon;
    ImageView previousIcon;
    EventHandler playBackControl;
    SidePane rightPane;
    
    Label help;
    Player(Service service, SettingsService settingsService) {
        
        this.settingsService = settingsService;
        this.playbackService = service;
        
        // --------------Layout----------
        
        borderLayout = new BorderPane();
        finalLayout = new StackPane(borderLayout);
        finalLayout.setAlignment(Pos.CENTER);
        
        //Controls
        addFile = new Button("Add file");
        play = new Button();
        previous = new Button();
        play.setDisable(true);
        previous.setDisable(true);
        
        
        playIcon = new ImageView(new Image(getClass().getResource("/icons/play-icon.png").toExternalForm()));
        pauseIcon = new ImageView(new Image(getClass().getResource("/icons/pause-icon.png").toExternalForm()));        
        previousIcon = new ImageView(new Image(getClass().getResource("/icons/previous-icon.png").toExternalForm()));        
        
        play.setGraphic(playIcon);
        previous.setGraphic(previousIcon);
        
        
        //Filechooser
        filechooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("AIFF, MP3, WAV","*.wav", "*.aiff", "*.mp3");
        filechooser.getExtensionFilters().add(filter);
        
        //--SidePanes--
        //-Left-
        SidePane leftPane = new SidePane(Pos.CENTER_LEFT);
        leftPane.add(addFile);
        VBox fileItems = new VBox();
        leftPane.add(fileItems);
        
        borderLayout.setLeft(leftPane);
        //ToggleGroup for file item buttons
        ToggleGroup fileButtonsGroup = new ToggleGroup();
        
        //-Bottom-
        SidePane bottomPane = new SidePane(Pos.BOTTOM_CENTER);
        bottomPane.add(previous);
        bottomPane.add(play);
        borderLayout.setBottom(bottomPane);
        
        //-Right-
        rightPane = new SidePane(Pos.CENTER_RIGHT);
        
        //Settings        
        updateSettings();
        borderLayout.setRight(rightPane);
        
        //------Scene------
        scene = new Scene(finalLayout, 1280,720);
        
        String style = getClass().getResource("/style/UIStyle1.css").toExternalForm();
        scene.getStylesheets().add(style);
        

        //-------Event Handling---------
        
        playBackControl = (EventHandler) (Event e) -> {
            if (playbackService.togglePlayback())
                play.setGraphic(pauseIcon);
            else
                play.setGraphic(playIcon);
        };

        play.setOnAction(playBackControl);
        
        scene.setOnMousePressed(playBackControl);
        
        scene.setOnMouseMoved((MouseEvent e) -> {
            leftPane.setVisible();
            bottomPane.setVisible();
            rightPane.setVisible();
        });
        
        previous.setOnAction((ActionEvent e) -> {playbackService.toStart();});
        
        addFile.setOnAction((ActionEvent e) -> {
            File file = filechooser.showOpenDialog(stage);
            if (file == null) {
                return;
            }
            String filename = file.getName();
            playbackService.add(file);
            play.setGraphic(playIcon);
            
            FileItem item = new FileItem(filename, fileButtonsGroup);
            
            item.setSelectHandler(
                (EventHandler) (Event t) -> {
                    removeVisualizer();
                    playbackService.stop();
                    playbackService.selectFile(filename);
                    playbackService.initializeMedia();
                    playbackService.setOnEndOfMedia(() -> play.setGraphic(playIcon));
                    finalLayout.getChildren().add(playbackService.getVisualization());
                    play.setDisable(false);
                    previous.setDisable(false);
            });
            item.setRemoveHandler(
                (EventHandler) (Event t) -> {
                    playbackService.remove(filename);
                    leftPane.remove(item);
                    if (playbackService.isSelected(filename)) {
                        removeVisualizer();
                        playbackService.stop();
                        play.setDisable(true);
                        previous.setDisable(true);
                        play.setGraphic(playIcon);
                    }
            });
            leftPane.add(item);
        });
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Scene getScene() {
        return scene;
    }
    private void removeVisualizer() {
        if (finalLayout.getChildren().size() == 2) {
            finalLayout.getChildren().remove(1);
        }
    }
    private void updateSettings() {
        rightPane.clearAdditionalChildren();
        for (Setting setting : settingsService.getSettings().getAll()) {
            SettingSlider settingSlider = new SettingSlider(setting);
            rightPane.add(settingSlider);
        }
    }
}