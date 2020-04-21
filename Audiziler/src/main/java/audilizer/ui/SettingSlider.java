/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import audilizer.domain.Setting;
import javafx.beans.binding.StringBinding;
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
    Slider slider;
    Label nameLabel;
    Label valueLabel;
    HBox hbox;
    StringBinding valueStringProperty;
    public SettingSlider(Setting setting) {
        super();
        slider = new Slider();
        slider.setValue(setting.getValue());
        slider.setMin(setting.getMin());
        slider.setMax(setting.getMax());
        valueLabel = new Label(integerPartAsString(setting.getValue()));
        
        setting.getProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                double value = (double) ov.getValue();
                valueLabel.setText( integerPartAsString( value ) );
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
    private String integerPartAsString(double d) {
        String raw = String.valueOf(((Double) d));
        if (raw.length() < 5) {
            return raw;
        }
        if (raw.startsWith("-")) {
            return raw.substring(0,5);
        }
        return raw.substring(0,4);
    }
}
