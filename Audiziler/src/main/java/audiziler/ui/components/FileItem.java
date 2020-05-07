/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui.components;

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
    private final ToggleButton selectButton;
    private final Button remove;
    
    public FileItem(String name, ToggleGroup group) {
        super();
        this.selectButton = new ToggleButton(name);
        selectButton.setToggleGroup(group);
        remove = new Button("remove");
        super.setSpacing(2);
        super.getChildren().addAll(selectButton, remove);
        
        //Disable button when it is selected
        selectButton.disableProperty().bind(selectButton.selectedProperty());
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
