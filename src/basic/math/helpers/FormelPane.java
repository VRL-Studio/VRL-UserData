/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The place were <code>Formel</code>´s are shown and where the label of
 * <code>FormelEntry</code>´s is / should be added.
 *
 * @author Night
 */
public class FormelPane extends JPanel {

    public FormelPane() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        setBackground( new Color(0,0,0,0));
    }

    /**
     * Adds an ExpressionLabel to the FormelPane and sets the reaction on a mouse event.
     * The FormelEntry react only on a mouse event if it is editable.
     *
     * @param ausdruck a String that contains a part of a <code>Formel</code>
     * @param listener ActionListner that should fire() if the added ExpressionLabel is editable
     * @param editable Boolean which decides if the added ExpressionLabel is editable
     */
    public void addFormel(String ausdruck, ActionListener listener,boolean editable) {
        
            ExpressionLabel expression = new ExpressionLabel(ausdruck);
            expression.setListener(listener);
            expression.setMouseEventReaction(editable);
            add(expression);
    }

    /**
     * Removes all ExpressionLabel.
     */
    public void clean() {
        for (Component c : getComponents()) {
            if (c instanceof ExpressionLabel) {
                remove(c);
            }
        }
    }
}
