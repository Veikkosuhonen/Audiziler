/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui;

import audiziler.ui.components.FileItem;
import audiziler.ui.components.SidePane;
import audiziler.ui.components.SettingSlider;
import audiziler.ui.components.VisualizationSelector;
import audiziler.domain.FileService;
import audiziler.domain.Service;
import audiziler.media.visualizer.VisualizationType;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * UI class for constructing the main scene of the application
 * @author vesuvesu
 */
public class Player {
    
    Service playbackService;
    FileService fileService;
    
    FileChooser filechooser;
    
    Stage stage;
    Scene scene;
    BorderPane borderLayout;
    StackPane finalLayout;
    Button addFileButton;
    Button playButton;
    Button previousButton;
    ImageView playIcon;
    ImageView pauseIcon;
    ImageView previousIcon;
    EventHandler playBackControl;
    
    VBox fileItems;
    ToggleGroup fileButtonsGroup;
    
    SidePane leftPane;
    SidePane rightPane;
    
    VisualizationSelector selector;
    
    Label help;
    Player(Service service, FileService fileService) {
        this.fileService = fileService;
        this.playbackService = service;
        
        // --------------Layout----------
        
        borderLayout = new BorderPane();
        finalLayout = new StackPane(borderLayout);
        finalLayout.setAlignment(Pos.CENTER);
        
        //Controls
        playButton = new Button();
        previousButton = new Button();
        playButton.setDisable(true);
        previousButton.setDisable(true);
        
        
        playIcon = new ImageView(new Image(getClass().getResource("/icons/play-icon.png").toExternalForm()));
        pauseIcon = new ImageView(new Image(getClass().getResource("/icons/pause-icon.png").toExternalForm()));        
        previousIcon = new ImageView(new Image(getClass().getResource("/icons/previous-icon.png").toExternalForm()));        
        
        playButton.setGraphic(playIcon);
        previousButton.setGraphic(previousIcon);
        
        
        //Filechooser
        filechooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("AIFF, MP3, WAV", "*.wav", "*.aiff", "*.mp3");
        filechooser.getExtensionFilters().add(filter);
        
        //--SidePanes--
        //-Left-
        leftPane = new SidePane(Pos.CENTER_LEFT);
        fileItems = new VBox();
        addFileButton = new Button("Add file");
        //Updates fileItems and adds fileItems and addFileButton
        redrawFiles();
        
        
        borderLayout.setLeft(leftPane);
        //ToggleGroup for file item buttons
        fileButtonsGroup = new ToggleGroup();
        
        //-Bottom-
        SidePane bottomPane = new SidePane(Pos.BOTTOM_CENTER);
        bottomPane.add(previousButton);
        bottomPane.add(playButton);
        borderLayout.setBottom(bottomPane);
        
        //-Right-
        rightPane = new SidePane(Pos.CENTER_RIGHT);
        selector = new VisualizationSelector();
        initRightPane();
        borderLayout.setRight(rightPane);
        
        //------Scene------
        scene = new Scene(finalLayout, 1280, 720);
        
        String style = getClass().getResource("/style/UIStyle1.css").toExternalForm();
        scene.getStylesheets().add(style);
        

        //-------Event Handling---------
        
        playBackControl = (EventHandler) (Event e) -> {
            if (playbackService.togglePlayback()) {
                playButton.setGraphic(pauseIcon);
            } else {
                playButton.setGraphic(playIcon);
            }
        };

        playButton.setOnAction(playBackControl);
        previousButton.setOnAction((ActionEvent e) -> playbackService.toStart());
        
        selector.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                playbackService.selectVisualization((VisualizationType) ov.getValue());
                redrawSettings();
            }
        });
        
        scene.setOnMousePressed(playBackControl);
        
        scene.setOnMouseMoved((MouseEvent e) -> {
            leftPane.setVisible();
            bottomPane.setVisible();
            rightPane.setVisible();
        });
        
        addFileButton.setOnAction((ActionEvent e) -> {
            File file = filechooser.showOpenDialog(stage);
            if (file == null) {
                return;
            }
            if (fileService.add(file)) {
                leftPane.add(createFileItem(file));
            }
        });
    }
    
    private void handleFileSelection(String filename) {
        playbackService.stop();
        playbackService.initializeMedia(fileService.getFile(filename));
        playbackService.selectVisualization((VisualizationType) selector.valueProperty().getValue());
        playbackService.setOnEndOfMedia(() -> playButton.setGraphic(playIcon));
        removeVisualizer();
        redrawSettings();
        finalLayout.getChildren().add(playbackService.getVisualization());
        playButton.setDisable(false);
        previousButton.setDisable(false);
    }
    
    private void handleFileRemove(String filename) {
        fileService.remove(filename);
        redrawFiles();
        
        if (playbackService.isSelected(filename)) {
            removeVisualizer();
            initRightPane();
            playbackService.stop();
            playButton.setDisable(true);
            previousButton.setDisable(true);
            playButton.setGraphic(playIcon);
        }
    }
    /**
     * Removes the second Children of the StackPane (root) layout if it exists. This children should always be the Visualizer.
     */
    private void removeVisualizer() {
        if (finalLayout.getChildren().size() == 2) {
            finalLayout.getChildren().remove(1);
        }
    }
    /**
     * Updates and redraws the visualization controls such as <code>VisualizationSelector</code> 
     * and <code>SettingSlider</code> components contained in the right <code>SidePane</code>
     */
    private void redrawSettings() {
        rightPane.clearAdditionalChildren();
        rightPane.add(selector);
        playbackService.getSettings().getAll().forEach(setting -> 
            rightPane.add(new SettingSlider(setting))
        );
    }
    /**
     * Updates and redraws <code>FileItem</code> components
     */
    private void redrawFiles() {
        leftPane.clearAdditionalChildren();
        leftPane.add(addFileButton);
        fileItems.getChildren().clear();
        leftPane.add(fileItems);
        
        fileService.getAll().forEach(file -> {
                fileItems.getChildren().add(
                    createFileItem(file)
                );
            }
        );
    }
    /**
     * Creates and returns a <code>FileItem</code> UI-component containing the filename of a given file
     * @param file
     * @return 
     */
    private FileItem createFileItem(File file) {
        
        FileItem item = new FileItem(file.getName(), fileButtonsGroup);
        
        item.setSelectHandler((EventHandler) (Event t) -> {
            handleFileSelection(file.getName());
        });

        item.setRemoveHandler((EventHandler) (Event t) -> {
            handleFileRemove(file.getName());
        });
        
        return item;
    }
    /**
     * Clears the right <code>SidePane</code> and adds  Label
     */
    private void initRightPane() {
        rightPane.clearAdditionalChildren();
        rightPane.add(new Label("Select a file"));
    }
    public void setStage(Stage stage) {
        this.stage = stage;
        //Fullscreen event handler
        this.scene.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.F) {
                stage.setFullScreen(true);
            }
        });
    }
    public Scene getScene() {
        return scene;
    }
}