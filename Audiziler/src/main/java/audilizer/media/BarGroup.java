/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import audilizer.domain.Settings;
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
    float[] flattener;
    DoubleBinding rootHeight;
    Settings settings;
    BarGroup(int length, DoubleBinding width, DoubleBinding height, Settings settings) {
        this.length = length;
        rects = new Rectangle[length];
        rootHeight = width;
        flattener = new float[length];
        this.settings = settings;
        
        System.out.println(rootHeight.get());
        for (int i = 0; i < length; i++) {
            Rectangle rect = new Rectangle(i*6, 0, 4, 0);
            rect.translateXProperty().bind(width);
            rect.translateYProperty().bind(height.divide(4));
            rects[i] = rect;
            
            flattener[i] = 1/(1+i/30)+1;
        }
    }
    Rectangle[] update(float[] magnitudes) {
        //System.out.println(magnitudes[100]);
        for (int i = 0; i < length; i++) {
            float newHeight = (float) rects[i].getHeight() - (float) (rects[i].getHeight() - height(magnitudes[i])/settings.get("acceleration").getValue());
            newHeight *= settings.get("height").getValue();
            rects[i].setY(rootHeight.get() - newHeight);
            rects[i].setHeight(newHeight);
            float normalHeight = normalized(newHeight);
            
            rects[i].setFill(
                    Color.hsb(
                            i * 1.0 / length * settings.get("frequency color offset").getValue() + 
                            settings.get("magnitude color offset").getValue() * normalHeight +
                            settings.get("color offset").getValue()
                            , 1.0, normalHeight+0.1));
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
        float n = f / 100;
        if (n > 0.9f) {
            n = 0.9f;
        }
        return n;
    }
    private float height(float magnitude) {
        float f = (float) Math.pow(magnitude + 90, 2.3) / 40;
        if (f>0) {
            return f;
        } else {
            return 0;
        }
    }
}
