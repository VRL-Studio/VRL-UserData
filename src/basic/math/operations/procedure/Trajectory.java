/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import basic.math.elements.visual.VisualVector;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author night
 */
class Trajectory extends ArrayList<VisualVector>{
    private Color color;
    private String name;

    public Trajectory() {
    }

    public Trajectory(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    
    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
