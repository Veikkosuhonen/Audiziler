/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author vesuvesu
 */
public class Setting {
    DoubleProperty value;
    String name;
    String description;
    Double min;
    Double max;
    public Setting(String name, String description, double value, double min, double max) {
        this.value = new SimpleDoubleProperty();
        this.value.set(value);
        this.name = name;
        this.description = description;
        this.min = min;
        this.max = max;
    }
    public void set(double value) {
        this.value.set(value);
    }
    public void bind(DoubleProperty binding) {
        value.bind(binding);
    }
    public DoubleProperty getProperty() {
        return value;
    }
    public double getValue() {
        return value.get();
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }
}
