/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui.components;

import audiziler.media.visualizer.VisualizationType;
import java.util.Arrays;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author vesuvesu
 */
public class VisualizationSelector extends HBox {
    
    private final ComboBox selector;
    private final Label label;
    
    public VisualizationSelector() {
        super();
        
        selector = new ComboBox();
        selector.getItems().addAll(Arrays.asList(VisualizationType.values()));
        selector.setValue(VisualizationType.BARS);
        
        label = new Label("Type");
        super.getChildren().addAll(label, selector);
        super.setSpacing(5);
    }
    
    public ObjectProperty valueProperty() {
        return selector.valueProperty();
    }
}
