/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author vesuvesu
 */

public class BarGroup {
    Rectangle[] rects;
    int length;
    BarGroup(int length, DoubleBinding width, DoubleBinding height) {
        this.length = length;
        rects = new Rectangle[length];
        
        for (int i = 0; i < length; i++) {
            Rectangle rect = new Rectangle(i*4, 0, 4, 0);
            rect.translateXProperty().bind(width);
            rect.translateYProperty().bind(height);
            rects[i] = rect;
        }
    }
    Rectangle[] update(float[] magnitudes) {
        //System.out.println(magnitudes[100]);
        for (int i = 0; i < length; i++) {
            float newHeight = (float) rects[i].getHeight() - ((float) rects[i].getHeight() - height(magnitudes[i]))/3;
            
            rects[i].setY(320 - newHeight);
            rects[i].setHeight(newHeight);
            rects[i].setFill(Color.hsb(i * 1.0 / length * 64 + 128, 1.0, normalized(magnitudes[i])));
        }
        return rects;
    }
    private float positive(float f) {
        if (f < 0) {
            return 0;
        }
        return f;
    }
    private float normalized(float f) {
        float n = (f + 100) / 60;
        if (n > 1.0f) {
            n = 1.0f;
        }
        return n;
    }
    private float height(float magnitude) {
        return (float) Math.pow(magnitude + 90, 2) / 10;
    }
}
