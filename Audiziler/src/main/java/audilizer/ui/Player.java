/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import audilizer.media.MPlayer;
import audilizer.media.Visualizer;
import audilizer.domain.FileManager;
import audilizer.domain.Service;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
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
    
    Service playbackService;
    MPlayer mediaplayer;
    Visualizer visualizer;

    File file;
    FileChooser filechooser;
    
    Scene scene;
    BorderPane borderLayout;
    StackPane finalLayout;
    Button fileSelect;
    Button play;
    Button previous;
    ImageView playIcon;
    ImageView pauseIcon;
    ImageView previousIcon;
    EventHandler playBackControl;
    
    Label help;
    Player(Stage stage, FileManager filemanager) {
        
        this.playbackService = new Service();
        
        // --------------Layout----------
        
        borderLayout = new BorderPane();
        finalLayout = new StackPane(borderLayout);
        finalLayout.setAlignment(Pos.CENTER);
        
        //Controls
        fileSelect = new Button("Add file");
        play = new Button();
        previous = new Button();
        play.setDisable(true);
        previous.setDisable(true);
        
        try {
            playIcon = new ImageView(new Image(new File("src/main/java/audilizer/ui/icons/play-icon.png").toURI().toURL().toString()));
            pauseIcon = new ImageView(new Image(new File("src/main/java/audilizer/ui/icons/pause-icon.png").toURI().toURL().toString()));
            previousIcon = new ImageView(new Image(new File("src/main/java/audilizer/ui/icons/previous-icon.png").toURI().toURL().toString()));
        } catch (MalformedURLException mue) {
            System.out.println("mue: "+mue.getMessage());
        }
        play.setGraphic(playIcon);
        previous.setGraphic(previousIcon);
        
        
        //Filechooser
        filechooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("AIFF, MP3, WAV","*.wav", "*.aiff", "*.mp3");
        filechooser.getExtensionFilters().add(filter);
        
        //--SidePanes--
        //-Left-
        SidePane leftPane = new SidePane(Pos.CENTER_LEFT);
        leftPane.add(fileSelect);
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
        SidePane rightPane = new SidePane(Pos.CENTER_RIGHT);
        
        //Settings
        SettingSlider thresholdSlider = new SettingSlider(playbackService.getSetting("threshold"));
        rightPane.add(thresholdSlider);
        
        SettingSlider colorOffsetSlider = new SettingSlider(playbackService.getSetting("color offset"));
        rightPane.add(colorOffsetSlider);
        
        SettingSlider frequencyColorOffsetSlider = new SettingSlider(playbackService.getSetting("frequency color offset"));
        rightPane.add(frequencyColorOffsetSlider);
        
        SettingSlider magnitudeColorOffsetSlider = new SettingSlider(playbackService.getSetting("magnitude color offset"));
        rightPane.add(magnitudeColorOffsetSlider);
        
        SettingSlider accelerationSlider = new SettingSlider(playbackService.getSetting("acceleration"));
        rightPane.add(accelerationSlider);
        
        SettingSlider heightSlider = new SettingSlider(playbackService.getSetting("height"));
        rightPane.add(heightSlider);
        
        SettingSlider bloomSlider = new SettingSlider(playbackService.getSetting("bloom"));
        rightPane.add(bloomSlider);
        
        borderLayout.setRight(rightPane);
        
        
        
        //------Scene------
        scene = new Scene(finalLayout, 1280,720);
        File stylesheet = new File("src/main/java/audilizer/ui/style/UIStyle1.css");
        try {
            URL styleUrl = stylesheet.toURI().toURL();
            scene.getStylesheets().add(styleUrl.toString());
        }
        catch (MalformedURLException mue) {System.out.println("could not find stylesheet");}
        Camera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);
        
        
        
        
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
        
        fileSelect.setOnAction((ActionEvent e) -> {
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
                    playbackService.initializeMedia(scene);
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
    public Scene getScene() {
        return scene;
    }
    private void removeVisualizer() {
        if (finalLayout.getChildren().size() == 2) {
            finalLayout.getChildren().remove(1);
        }
    }
}