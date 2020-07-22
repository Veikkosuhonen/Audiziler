/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.media.visualizer;

import javafx.scene.paint.Color;

/**
 *
 * @author vesuvesu
 */
public class Particle {
    private Vector2D pos;
    private Vector2D vel;
    private Vector2D acc;

    private double h;
    private double s;
    private double b;
    
    private float strength;
    private float opacity;
    private float decay;
    
    private int age;
    
    public Particle(Vector2D pos, Vector2D vel, Vector2D acc, float h, float strength, float opacity, float decay) {
        this.pos = pos;
        this.vel = vel;
        this.acc = acc;
        this.h = h;
        s = 1.0f;
        b = 1.0f;
        this.strength = strength;
        this.opacity = opacity;
        this.decay = decay;
        age = 0;
    }
    public void update() {
        vel.add(acc);
        pos.add(vel);
        age++;
        s *= decay;
        b *= decay;
        strength *= decay;
    }
    public float getX() {
        return pos.x;
    }
    public float getY() {
        return pos.y;
    }
    public int getAge() {
        return age;
    }
    public Vector2D getVel() {
        return vel;
    }
    public void setAcc(Vector2D acc) {
        this.acc = acc;
    }
    public Color getColor() {
        return Color.hsb(h, s, b, opacity);
    }
    public float getStrength() {
        return strength;
    }
}
