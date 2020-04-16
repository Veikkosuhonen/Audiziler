/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.media;

import java.util.Random;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Translate;

/**
 *
 * @author vesuvesu
 */
public class LightCube {
    Box[] cubes;
    PointLight light1;
    PointLight light2;
    AmbientLight light3;
    PhongMaterial material;
    Reflection reflection;
    LightCube(DoubleBinding width, DoubleBinding height) {
        Random random = new Random();
        light1 = new PointLight(Color.rgb(10 ,86, 128));
        light2 = new PointLight(Color.rgb(86, 20, 128));
        light2.getTransforms().add(new Translate(1920, 0, 100));
        light3 = new AmbientLight(Color.rgb(10,10,10));
        cubes = new Box[20];
        int cubesWidth = 1920;
        int spacing = cubesWidth/cubes.length;
        material = new PhongMaterial();
        material.setSpecularColor(Color.WHITE);
        material.setSpecularPower(32);
        reflection = new Reflection(100,0.7, 0.7, 0.1);
        reflection.setFraction(0.7);
        for (int i = 0; i<cubes.length; i++) {
            cubes[i] = new Box(100,100,100);
            cubes[i].translateXProperty().bind(width.subtract(cubesWidth).divide(2).add(i*spacing));
            cubes[i].translateYProperty().bind(height.divide(2));
            cubes[i].setTranslateZ(300);
            cubes[i].setRotationAxis(new Point3D(random.nextInt() * 400, random.nextInt() * 400, random.nextInt() * 400));
            cubes[i].setDrawMode(DrawMode.FILL);
            cubes[i].setCullFace(CullFace.BACK);
            cubes[i].setMaterial(material);
        }
        
    }
    Node[] update(float[] magnitudes) {
        Node[] nodes = new Node[cubes.length+3];
        for (int i = 0; i<cubes.length; i++) {
            cubes[i].setWidth(magnitudes[i*3]+100);
            cubes[i].setHeight(magnitudes[10 + i*3]+100);
            cubes[i].setDepth(magnitudes[20 + i*3]+100);
            cubes[i].setRotate(cubes[i].getRotate() + (magnitudes[i*2]+90)/20);
            cubes[i].setEffect(reflection);

            nodes[i] = cubes[i];
        }
        //light.setRotate(light.getRotate() + 1);
        nodes[nodes.length-3] = light3;
        nodes[nodes.length-2] = light1;
        nodes[nodes.length-1] = light2;
        return nodes;
    }
}
