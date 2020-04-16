/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

/**
 *
 * @author vesuvesu
 */
public class Window {
    private SimpleDoubleProperty WIDTH;
    private SimpleDoubleProperty HEIGHT;
    public Window() {
        WIDTH = new SimpleDoubleProperty();
        HEIGHT = new SimpleDoubleProperty();
    }
    public void bind(Stage stage) {
        WIDTH.bind(stage.widthProperty());
        HEIGHT.bind(stage.heightProperty());
    }
    public double getWidth() {
        return WIDTH.get();
    }
    public double getHeight() {
        return HEIGHT.get();
    }
}
