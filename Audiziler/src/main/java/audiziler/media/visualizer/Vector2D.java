/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

/**
 *
 * @author vesuvesu
 */
public class Vector2D {
    float x;
    float y;
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }
    public void add(Vector2D vec) {
        this.x += vec.x;
        this.y += vec.y;
    }
    public void subtract(Vector2D vec) {
        this.x -= vec.x;
        this.y -= vec.y;
    }
    public void mul(float s) {
        this.x *= s;
        this.y *= s;
    }
}
