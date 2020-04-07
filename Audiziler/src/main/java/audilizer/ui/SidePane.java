/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audilizer.ui;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author vesuvesu
 */
public class SidePane extends StackPane{
    Pos pos;
    Rectangle opener;
    VBox vbox;
    HBox hbox;
    
    boolean vertical;
    
    PauseTransition openerShowingTime;
    PauseTransition menuShowingTime;
    
    SidePane(Pos pos) {
        super();
        determineAlignment(pos);
        this.pos = pos;
        
        openerShowingTime = new PauseTransition(Duration.seconds(4));
        openerShowingTime.setOnFinished((ActionEvent e) -> {
            if (opener.isVisible())
                super.setVisible(false); 
        });
        menuShowingTime = new PauseTransition(Duration.seconds(2));
        menuShowingTime.setOnFinished((ActionEvent e) -> {
            if (!this.isHover()) {
                opener.setVisible(true);
                if (vertical)
                    vbox.setVisible(false);
                else 
                    hbox.setVisible(false);
            }
        });
        
        if (vertical) {
            opener = new Rectangle(40,100);
            vbox = new VBox();
            vbox.setAlignment(pos);
            vbox.setSpacing(4);
            vbox.setVisible(false);
            vbox.setOnMouseExited((MouseEvent e) -> {
                menuShowingTime.playFromStart();
            });
            super.getChildren().addAll(opener, vbox);
            super.setAlignment(pos);
        } else {
            opener = new Rectangle(100, 40);
            hbox = new HBox();
            hbox.setAlignment(pos);
            hbox.setSpacing(4);
            hbox.setVisible(false);
            hbox.setOnMouseExited((MouseEvent e) -> {
                menuShowingTime.playFromStart();
            });
            super.getChildren().addAll(opener, hbox);
            super.setAlignment(pos);
        }
        
        //opener.getStyleClass().add("opener");
        
        opener.setOnMouseEntered((MouseEvent e) -> {
            opener.setVisible(false);
            if (vertical)
                vbox.setVisible(true);
            else
                hbox.setVisible(true);
        });
    }
    public void add(Node node) {
        if (vertical) {
            vbox.getChildren().add(node);
        } else {
            hbox.getChildren().add(node);
        }
    }
    private void determineAlignment(Pos pos) {
        if (pos == Pos.CENTER_LEFT || pos == Pos.CENTER_RIGHT) {
            vertical = true;
        } else if (pos == Pos.TOP_CENTER || pos == Pos.BOTTOM_CENTER) {
            vertical = false;
        } else {
            System.out.println("Illegal alignment for SidePane: " + pos);
        }
    }
    public void setVisible() {
        super.setVisible(true);
        openerShowingTime.playFromStart();
    }
}
