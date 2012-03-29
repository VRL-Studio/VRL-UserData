/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

/**
 * The supported modes, which could be used of the <code>SelectionButtonGroup</code>.
 * This mode are part of the controll/navigation logic in <code>PickPlotter</code>.
 *
 * @author night
 */
public class ToolButtonMode {

    /*is used for translation and rotation*/
    static final String TRANSFORMATION = "transformation";
    /*is used to pick shapes with a selection rectangle*/
    static final String SELECTION = "selection";
    /*is used to pick shapes with a single click or keeping the mouse button pressed and paint*/
    static final String PAINT = "paint";

}
