/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * A class to encapsulate the stage dimension properties
 * @author vesuvesu
 */
public class WindowSize {
    DoubleProperty width;
    DoubleProperty height;
    public WindowSize() {
        width = new SimpleDoubleProperty();
        height = new SimpleDoubleProperty();
    }
    public void bind(ReadOnlyProperty width, ReadOnlyProperty height) {
        this.width.bind(width);
        this.height.bind(height);
    }
    public double getWidth() {
        return width.get();
    }
    public double getHeight() {
        return height.get();
    }
    public DoubleProperty widthProperty() {
        return width;
    }
    public DoubleProperty heightProperty() {
        return height;
    }
}
