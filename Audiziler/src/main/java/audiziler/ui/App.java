/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui;

import audiziler.dao.FileAudioFileDao;
import audiziler.domain.FileService;
import audiziler.domain.PlaybackService;
import audiziler.domain.SettingsService;
import audiziler.dao.FileSettingDao;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 *
 * @author vesuvesu
 */
public class App extends Application {
    FileSettingDao settingdao;
    SettingsService settingsService;
    PlaybackService service;
    FileAudioFileDao audioFileDao;
    FileService filemanager;
    Player player;
    WindowSize windowSize;
    /**
     * Called when the Application launches. Constructs the application logic and DAO.
     */
    @Override
    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ioe) {
            System.out.println("ERROR: Could not find config.properties.\n"
                    + "Please make sure a valid config file is located under the parent directory of the executable");
            System.exit(0);
        }
        String settingsFilePath = properties.getProperty("settingsFile");
        String audioFilePath = properties.getProperty("audioFiles");
        String defaultSettingNames = properties.getProperty("defaultSettingNames");
        try {
            settingdao = new FileSettingDao(settingsFilePath, defaultSettingNames);
        } catch (IOException ioe) {
            System.out.println("Failed to load settings from settings file");
            System.exit(0);
        }
        settingsService = new SettingsService(settingdao);
        audioFileDao = new FileAudioFileDao(audioFilePath);
        filemanager = new FileService(audioFileDao);
        windowSize = new WindowSize();
        service = new PlaybackService(settingsService, windowSize);
        player = new Player(service, filemanager, windowSize);
    }
    /**
     * Called after init. Constructs the UI.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        windowSize.bind(stage.widthProperty(), stage.heightProperty());
        player.setStage(stage);
        stage.setTitle("Audilizer");
        stage.setScene(player.getScene());
        stage.show();
        player.showPopup();
    }
    /**
     * Called when the Application stops. Invokes <code>save()</code> on DAO-classes.
     */
    @Override
    public void stop() {
        try {
            settingsService.save();
            filemanager.save();
        } catch (IOException ioe) {
            System.out.println("Failed to save some data: " + ioe.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }    
}
