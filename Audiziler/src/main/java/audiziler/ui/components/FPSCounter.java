/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui.components;

import java.util.ArrayDeque;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

/**
 *
 * @author Veikko
 */
public class FPSCounter {
    Label fpsLabel;
    ArrayDeque<Long> queue;
    AnimationTimer fpsMeter;
    long time;
    long avgTime;
    int framecount;
    public FPSCounter() {
        queue = new ArrayDeque();
        time = System.nanoTime();
        avgTime = 0;
        fpsLabel = new Label("0 fps");
        framecount = 0;
        fpsMeter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long timeSpan = now - time;
                time = now;
                if (framecount == 0b1000) {
                    framecount = 0;
                    avgTime += timeSpan;
                    queue.add(timeSpan);
                    if (queue.size() > 50) {
                        avgTime -= queue.pollFirst();
                    }
                    fpsLabel.setText(String.valueOf(1e9/(avgTime/50)).substring(0, 4) + " fps");
                } else {
                    framecount++;
                }
            } 
        };
    }
    public void start() {
        fpsMeter.start();
    }
    public Label getLabel() {
        return fpsLabel;
    }
}
