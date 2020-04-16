/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import audilizer.domain.FileManager;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
 *
 * @author vesuvesu
 */
public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }    
    @Override
    public void start(Stage stage) throws Exception {
        Window window = new Window();
        FileManager filemanager = new FileManager();
        Player player = new Player(stage, filemanager, window);
        
        player.getScene().setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.F)
                stage.setFullScreen(true);
        });
        
        stage.setTitle("Audilizer");
        stage.setScene(player.getScene());
        
        stage.show();
        window.bind(stage);
    }
}
