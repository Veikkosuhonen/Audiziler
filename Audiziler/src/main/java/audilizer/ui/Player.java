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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
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
    BorderPane layout;
    Button fileSelect;
    Button play;
    ImageView playIcon;
    ImageView pauseIcon;
    EventHandler playBackControl;
    
    Label help;
    
    Player(Stage stage, FileManager filemanager) {
        
        this.playbackService = new Service();
        
        // --------------Layout----------
        
        layout = new BorderPane();
        
        //Controls
        fileSelect = new Button("Add file");
        play = new Button();

        try {
            playIcon = new ImageView(new Image(new File("src/main/java/audilizer/ui/icons/play-icon.png").toURI().toURL().toString()));
            pauseIcon = new ImageView(new Image(new File("src/main/java/audilizer/ui/icons/pause-icon.png").toURI().toURL().toString()));
        } catch (MalformedURLException mue) {
            System.out.println("mue: "+mue.getMessage());
        }
        play.setGraphic(playIcon);
        
        //Filechooser
        filechooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("AIFF, MP3, WAV","*.wav", "*.aiff", "*.mp3");
        filechooser.getExtensionFilters().add(filter);
        
        //SidePanes
        SidePane leftPane = new SidePane(Pos.CENTER_LEFT);
        leftPane.add(fileSelect);
        layout.setLeft(leftPane);
        //ToggleGroup for file item buttons
        ToggleGroup fileButtonsGroup = new ToggleGroup();

        SidePane bottomPane = new SidePane(Pos.BOTTOM_CENTER);
        bottomPane.add(play);
        layout.setBottom(bottomPane);
        
        
        help = new Label("<----- Hover over opener to add an audiofile"
                + "\nClick filename to select"
                + "\nControl playback with playbutton at the bottom or with mouse"
                + "\nPress f to go into fullscreen mode");
        Font font = new Font("Helvetica-Light",12);
        help.setFont(font);
        help.setBorder(Border.EMPTY);
        HBox helpBox = new HBox(help);
        helpBox.setAlignment(Pos.CENTER);
        helpBox.setPadding(new Insets(50));
        layout.setTop(helpBox);
        
        //------Scene------
        
        scene = new Scene(layout, 1280,720);
        File stylesheet = new File("src/main/java/audilizer/ui/style/UIStyle1.css");
        try {
            URL styleUrl = stylesheet.toURI().toURL();
            scene.getStylesheets().add(styleUrl.toString());
        }
        catch (MalformedURLException mue) {System.out.println("could not find stylesheet");}
        
        
        //-------Event Handling---------
        
        playBackControl = new EventHandler() {
            @Override
            public void handle(Event e) {
                if (playbackService.togglePlayback())
                    play.setGraphic(pauseIcon);
                else
                    play.setGraphic(playIcon);
            }
        };
        
        play.setOnAction(playBackControl);
        scene.setOnMousePressed(playBackControl);
        scene.setOnMouseMoved((MouseEvent e) -> {
            leftPane.setVisible();
            bottomPane.setVisible();
        });
        
        fileSelect.setOnAction((ActionEvent e) -> {
            File file = filechooser.showOpenDialog(stage);
            String filename = file.getName();
            playbackService.add(file);
            leftPane.add(
                new FileItem(
                    filename, 
                    fileButtonsGroup,
                    new EventHandler() {
                        @Override
                        public void handle(Event t) {
                            playbackService.selectFile(filename);
                            playbackService.initializeMedia();
                            playbackService.setOnEndOfMedia(() -> play.setGraphic(playIcon));
                            layout.setCenter(playbackService.getVisualization());
                        }
                    }
                )
            );
        });
    }
    public Scene getScene() {
        return scene;
    }
}