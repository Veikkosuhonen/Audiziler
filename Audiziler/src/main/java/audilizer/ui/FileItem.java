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
import javafx.scene.layout.HBox;

/**
 *
 * @author vesuvesu
 */
public class FileItem extends HBox{
    String name;
    ToggleButton selectButton;
    Button remove;
    
    FileItem(String name, ToggleGroup group) {
        super();
        this.name = name;
        this.selectButton = new ToggleButton(name);
        selectButton.setToggleGroup(group);
        remove = new Button("remove");
        super.setSpacing(2);
        super.getChildren().addAll(selectButton, remove);
    }
    public void setSelectHandler(EventHandler selectHandler) {
        selectButton.setOnAction((ActionEvent ae) -> {
            selectHandler.handle(ae);
            selectButton.setEffect(null);
        });
    }
    public void setRemoveHandler(EventHandler removeHandler) {
        remove.setOnAction((ActionEvent ae) -> {
            removeHandler.handle(ae);
        });
    }
}
