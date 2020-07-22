/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import java.util.ArrayDeque;

/**
 *
 * @author Veikko
 */
public class DynamicSetting {
    private final int start;
    private final int end;
    private final int period;
    private final ArrayDeque<Float> queue;
    private float value;
    
    public DynamicSetting(int start, int end, int period) {
        this.start = start;
        this.end = end;
        this.period = period;
        queue = new ArrayDeque();
    }
    
    public void update(float[] magnitudes) {
        float m = avgMag(magnitudes);
        queue.add(m);
        value += m;
        if (queue.size() >= period) {
            value -= queue.poll();
        }
    }
    
    public float getValue() {
        return value/period;
    }
    
    private float avgMag(float[] magnitudes) {
        float avg = 0;
        for (int i = start; i < end; i++) {
            avg += magnitudes[i];
        }
        return avg;
    }
}
