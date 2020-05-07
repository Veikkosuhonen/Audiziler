/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui.components;

import audiziler.domain.Setting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author vesuvesu
 */
public class SettingSlider extends VBox{
    private final Slider slider;
    private final Label nameLabel;
    private final Label valueLabel;
    private final HBox hbox;
    
    public SettingSlider(Setting setting) {
        super();
        slider = new Slider();
        slider.setValue(setting.getValue());
        slider.setMin(setting.getMin());
        slider.setMax(setting.getMax());
        valueLabel = new Label(formattedStringValue(setting.getValue()));
        
        setting.getProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                double value = (double) ov.getValue();
                valueLabel.setText( formattedStringValue( value ) );
            }            
        });
        
        valueLabel.setMinWidth(70);
        
        setting.bind(slider.valueProperty());
        nameLabel = new Label(setting.getName());
        nameLabel.setTooltip(new Tooltip(setting.getDescription()));
        slider.setTooltip(new Tooltip(setting.getDescription()));
        hbox = new HBox(valueLabel, slider);
        hbox.setSpacing(5);
        this.getChildren().addAll(nameLabel, hbox);
    }
    /**
     * A method to format a double value to a string displaying the 3 first digits
     * @param d
     * @return the formatted String
     */
    private String formattedStringValue(double d) {
        String raw = String.valueOf(d);
        if (raw.length() < 5) {
            return raw;
        }
        if (raw.startsWith("-")) {
            return raw.substring(0,5);
        }
        return raw.substring(0,4);
    }
}
