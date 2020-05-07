/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import audiziler.media.visualizer.Vector2D;

/**
 *
 * @author vesuvesu
 */
public class Bar {
    private Vector2D pos;
    private Vector2D size;
    public Bar(Vector2D pos, Vector2D size) {
        this.pos = pos;
        this.size = size;
    }
    public void setHeight(float height) {
        size.y = height;
    }
    public Vector2D getPos() {
        return pos;
    }
    public Vector2D getSize() {
        return size;
    }
}
