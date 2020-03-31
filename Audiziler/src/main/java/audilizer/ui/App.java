/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 *
 * @author vesuvesu
 */
public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    File file;
    Player mediaplayer;
    FileChooser filechooser;
    
    Button fileSelectButton;
    Button playButton;
    Button pauseButton;
    Label fileLabel;
    Label errorLabel;
    Label fileTypeText;
    
    Bloom bloom;
    
    @Override
    public void start(Stage stage) throws Exception {
                
        filechooser = new FileChooser();

        fileSelectButton = fileButton("Select file");
        playButton = mediaButton("Play");
        //pauseButton = mediaButton("Pause");
        fileLabel = new Label("no file selected");
        fileLabel.setPadding(new Insets(7));
        errorLabel = new Label("");
        errorLabel.setPadding(new Insets(5));
        errorLabel.setVisible(false);
        fileTypeText = new Label("Supported file types are: AIFF, MP3 and WAV");
        fileTypeText.setStyle("fx-font-size: 12px;");
        HBox hbox = new HBox(playButton);
        hbox.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(25);
        box.getChildren().addAll(fileSelectButton,fileLabel, hbox, errorLabel, fileTypeText);
        
        Rectangle rect = new Rectangle(400,400);
        rect.setFill(Color.web("0x0f2a2b"));
        rect.setStroke(Color.web("0x103233"));
        
        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(rect,box);
        
        bloom = new Bloom();
        bloom.setThreshold(0.1);
        
        fileSelectButton.setOnAction((ActionEvent e) -> {
            file = filechooser.showOpenDialog(stage);
            if (file!=null) {
                selectFile(file);
            } else {
                fileLabel.setText("no file selected");
            }
        });
        playButton.setOnAction(e -> {
            if (mediaplayer.player.statusProperty().get()==Status.PLAYING)
                mediaplayer.pause();
            else
                mediaplayer.play();
        });
        File stylesheet = new File("src/main/java/audilizer/ui/UIStyle1.css");
        URL styleUrl = stylesheet.toURI().toURL();
        
        Scene scene = new Scene(stackpane,800,800);
        scene.getStylesheets().add(styleUrl.toString());
        fileTypeText.setStyle("-fx-font-size: 14px;");
        stage.setTitle("Audilizer");
        stage.setScene(scene);
        stage.show();
    }
    public static Button fileButton(String text) {
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setTextAlignment(TextAlignment.CENTER);
        return button;
    }
    public static Button mediaButton(String name) {
        Button button = new Button(name);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setDisable(true);
        return button;
    }
    public void selectFile(File file) {
        this.file = file;
        try {
            mediaplayer = new Player(new Media(file.toURI().toURL().toString()));
            errorLabel.setVisible(false);
            fileLabel.setEffect(bloom);
            fileLabel.setText(file.getName().substring(0, file.getName().lastIndexOf(".")));
            playButton.setDisable(false);
        } catch (MediaException uafe) {
            fileLabel.setEffect(null);
            errorLabel.setVisible(true);
            errorLabel.setText(uafe.getMessage());
            playButton.setDisable(true);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
    }
    public class Player {
        MediaPlayer player;
        Player(Media media) {
            player = new MediaPlayer(media);
            player.setOnPlaying(() -> {
                playButton.setText("Pause");
            });
            player.setOnPaused(() -> {
                playButton.setText("Play");
            });
            player.setOnEndOfMedia(()-> {
                player.seek(Duration.ZERO);
                player.pause();
            });
        }
        void play() {
            player.play();
        }
        void pause() {
            player.pause();
        }
    }
}
