/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author vesuvesu
 */
public class BarGroup {
    Rectangle[] rects;
    int length;
    BarGroup(int length) {
        this.length = length;
        rects = new Rectangle[length];
        
        for (int i = 0; i < length; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(2);
            rect.setHeight(0);
            rect.setFill(Color.RED);
            rects[i] = rect;
        }
    }
    Rectangle[] update(float[] magnitudes) {
        System.out.println(magnitudes[100]);
        for (int i = 0; i < length; i++) {
            rects[i].setHeight(positive(magnitudes[i] + 90) * 4);
            rects[i].setFill(Color.hsb(i * 1.0 / length * 255, 1.0, normalized(magnitudes[i])));
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
}
