/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import net.sourceforge.jeuclid.swing.JMathComponent;

/**<p>
 * Consist of the label of a FormalEntry and the associated MouseListener.
 * The label is spezial formated string. It contains MathML information which 
 * were used to set the content of JMathComponent.<br>
 * JMathComponent is a part of JEuclid and do visualisation of the equation 
 * in normal mathematical notation.
 * </p>
 *
 * @author Night
 */
public class ExpressionLabel extends JPanel implements MouseListener {

    private ActionListener listener;
    private JMathComponent jMath;
    private static String emptyString = "<math></math>";
    private Boolean mouseEventReaction = false;
    private JPopupMenu popup;

    public ExpressionLabel(String exp) {

        addMouseListener(this);
        setBackground(new Color(0, 0, 0, 0));
        BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        jMath = new JMathComponent();
        jMath.setContent(exp);
        jMath.setBackground(new Color(0, 0, 0, 0));
        jMath.setFontSize(18);

        add(jMath);

        rightClickOptions();
    }

    /**
     * allows to set the size of a matrix form vrl by right click
     */
    private void rightClickOptions() {

        popup = new JPopupMenu("Menu");
        JMenuItem item1 = new JMenuItem("set size");

        item1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(new ActionEvent(this, 0, "set size"));
            }
        });

        popup.add(item1);
    }

    /**
     * @return the listener
     */
    public ActionListener getListener() {
        return listener;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1 && mouseEventReaction) {
            fireAction(new ActionEvent(this, 0, "clicked"));
        }
    }
    /**
     * not used
     */
    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 3 && mouseEventReaction) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (mouseEventReaction) {
            setBackground(Color.GREEN);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (mouseEventReaction) {
            setBackground(new Color(0, 0, 0, 0));
        }
    }

    /**
     * Fires an action. This method is called whenever the corresponding
     * mouse event occures.
     * @param event the mouse event
     */
    private void fireAction(ActionEvent event) {
        if (listener != null) {
            listener.actionPerformed(event);
        }
    }

    /**
     * @return the mouseEventReaction
     */
    public Boolean getMouseEventReaction() {
        return mouseEventReaction;
    }

    /**
     * @param mouseEventReaction the mouseEventReaction to set
     */
    public void setMouseEventReaction(Boolean mouseEventReaction) {
        this.mouseEventReaction = mouseEventReaction;
    }

}
