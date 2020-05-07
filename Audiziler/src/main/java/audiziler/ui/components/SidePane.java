/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiziler.ui.components;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * UI-component class
 * @author vesuvesu
 */
public class SidePane extends StackPane{
    private final Pos pos;
    private Rectangle opener;
    private VBox vbox;
    private HBox hbox;
    
    private boolean vertical;
    
    private final PauseTransition openerShowingTime;
    private PauseTransition menuShowingTime;
    /**
     * Creates the component with different layout depending on the <code>Pos</code> given.
     * Can be Left, Right, Bottom or Top.
     * @param pos 
     */
    public SidePane(Pos pos) {
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
    /**
     * Adds a child <code>Node</code> to the <code>VBox</code> or to the <code>HBox</code> layout,
     * depending whether the <code>SidePane</code> will be rendered to the side or to the top or the bottom.
     * @param node 
     */
    public void add(Node node) {
        if (vertical) {
            vbox.getChildren().add(node);
        } else {
            hbox.getChildren().add(node);
        }
    }
    /**
     * Determines whether the child nodes should be rendered vertically or horizontally.
     * @param pos 
     */
    private void determineAlignment(Pos pos) {
        if (null == pos) {
            System.out.println("Illegal alignment for SidePane: " + pos);
        } else switch (pos) {
            case CENTER_LEFT:
            case CENTER_RIGHT:
                vertical = true;
                break;
            case TOP_CENTER:
            case BOTTOM_CENTER:
                vertical = false;
                break;
            default:
                System.out.println("Illegal alignment for SidePane: " + pos);
                break;
        }
    }
    public void setVisible() {
        super.setVisible(true);
        openerShowingTime.playFromStart();
    }
    public void remove(Node node) {
        if (vertical) {
            vbox.getChildren().remove(node);
        } else {
            hbox.getChildren().remove(node);
        }
    }
    public void clearAdditionalChildren() {
        if (vertical) {
            vbox.getChildren().clear();
        } else {
            hbox.getChildren().clear();
        }
    }
}
