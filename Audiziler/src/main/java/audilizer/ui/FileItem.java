/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.HBox;

/**
 *
 * @author vesuvesu
 */
public class FileItem extends HBox{
    String name;
    ToggleButton selectButton;
    Button remove;
    EventHandler handler;
    
    FileItem(String name, ToggleGroup group, EventHandler handler) {
        super();
        this.name = name;
        this.selectButton = new ToggleButton(name);
        selectButton.setToggleGroup(group);
        selectButton.setOnAction((ActionEvent ae) -> {
            handler.handle(ae);
            selectButton.setEffect(null);
        });
        remove = new Button("remove");
        super.setSpacing(2);
        super.getChildren().addAll(selectButton, remove);
    }
}
