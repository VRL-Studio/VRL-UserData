/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.ImageUtils;
import eu.mihosoft.vrl.visual.Style;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.JPanel;

/**
 * An own written button class to make a better look and feel in VRL.
 *
 * ToolButton is a basic class and is used to sets the behavior/mode
 * of e.g the PickPlotter, e.g. rotation or selection.
 *
 * @author night
 */
public class ToolButton extends JPanel implements MouseListener,
        MouseMotionListener, ActionListener {

    private VCanvas3D canvas;
    private Color iconColor;
    private Vector<ActionListener> actionListenerArray;
    protected Style style;
    private boolean active;
    private boolean selected;
    private int leftMargin;
    private int topMargin;
    protected BufferedImage buffer;
    private Dimension oldButtonSize;

    /**
     * Calls init().
     *
     * @param canvas The Canvas on which all drawings are done
     */
    public ToolButton(VCanvas3D canvas) {
        init(canvas);
        setFocusable(true);
    }

    /**
     * Sets the canvas and adds itself as MouseListener, MouseMotionListener and
     * ActionListener to itself.
     *
     * @param canvas The Canvas on which all drawings are done
     */
    public void init(VCanvas3D canvas) {
        this.canvas = canvas;
        actionListenerArray = new Vector<ActionListener>();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Addes the ActionListener to vector with opserving ActionListeners.
     *
     * @param l the ActionListener that should be added
     * @return true if adding was successful otherwise false
     */
    public void addActionListener(ActionListener l) {
        getActionListenerArray().add(l);
    }

    /**
     * Removes the ActionListener from vector with opserving ActionListeners.
     * 
     * @param l the ActionListener that should be removed
     * @return true if removing was successful otherwise false
     */
    public boolean removeActionListener(ActionListener l) {
        return getActionListenerArray().remove(l);
    }

    /**
     * Paints the margin of the button and calls drawIcon() to draw the Image of
     * the button. The Image is drawn in the center of the button.
     *
     * @param g the Graphic of the wholly button
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (canvas.getTypeRepresentation().getMainCanvas() != null) {
            style = canvas.getTypeRepresentation().getMainCanvas().getStyle();
        }

        if (buffer == null || oldButtonSize == null ||
                oldButtonSize.getWidth() != getWidth() ||
                oldButtonSize.getHeight() != getHeight()) {

            oldButtonSize = getSize();

            leftMargin = getWidth() / 5;
            topMargin = getHeight() / 5;

            int width = getWidth() - leftMargin * 2;
            int height = getHeight() - topMargin * 2;

            buffer = ImageUtils.createCompatibleImage(width, height);

            drawIcon(buffer.createGraphics());
        }

        iconColor = Color.white;

        if (style != null) {
            iconColor = style.getBaseValues().getColor(CanvasWindow.BORDER_COLOR_KEY);//getObjectBorderColor();
        }

        g2.setColor(iconColor);

        // 1 (frame / rand)
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

        // 2 (fill / füllung)
        int alpha = iconColor.getAlpha() / 4;

        Color iconBackgroundColor = new Color(iconColor.getRed(),
                iconColor.getGreen(), iconColor.getBlue(), alpha);

        if (active) {
            int red = Math.min(iconBackgroundColor.getRed() + 50, 255);
            int green = Math.min(iconBackgroundColor.getGreen() + 50, 255);
            int blue = Math.min(iconBackgroundColor.getBlue() + 50, 255);
            iconBackgroundColor = new Color(red, green, blue,
                    iconBackgroundColor.getAlpha());
        }

        if (isSelected()) {
            int red = Math.min(iconBackgroundColor.getRed(), 255);
            iconBackgroundColor = new Color(red, 255, 255,
                    iconBackgroundColor.getAlpha());
        }

        g2.setColor(iconBackgroundColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

        // 3  zu zeichnendes Bild (gestrichelte linie)
//        strokeWidth = 2;

//        leftMargin = getWidth() / 5 + strokeWidth / 2;
//        topMargin = getHeight() / 5 + strokeWidth / 2;

        g2.drawImage(buffer, leftMargin, topMargin, null);

    }

    /**
     * This Method needed to be overriden to draw a buttonclass
     * with an own image. If not a blank button will be drawn.
     *
     * @param g2 contains the complet space where the image is drawn
     */
    protected void drawIcon(Graphics2D g2) {
    }

    /**
     * Notify all added ActionListeners that an action has been performed.
     *
     * @param event
     */
    private void fireAction(ActionEvent event) {
        for (ActionListener listener : getActionListenerArray()) {
            listener.actionPerformed(event);
        }
    }

    /**
     * Returns a vector with ActionListener which was added to this button.
     *
     * @return the actionListenerArray
     */
    public Vector<ActionListener> getActionListenerArray() {
        return actionListenerArray;
    }

    /**
     * Sets the boolean selected to false if the button was selected before.
     * Calls the methode fireAction() with string "changed"
     * and the methode repaint().
     */
    protected void unselect() {
        if (isSelected()) {
            selected = false;
            repaint();
            fireAction(new ActionEvent(this, 0, "changed"));
        }

    }

    /**
     * Sets the boolean selected to true if the button was unselected before.
     * Calls the methode fireAction() with string "changed"
     * and the methode repaint().
     */
    protected void select() {
        if (!isSelected()) {
            selected = true;
            repaint();
            fireAction(new ActionEvent(this, 0, "changed"));
        }
    }

    /**
     * Calls the methode fireAction() with string "clicked"
     * and the methode select().
     *
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {   
        select();
        requestFocus();
        fireAction(new ActionEvent(this, 0, "clicked"));
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        active = true;
        repaint();
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        active = false;
        repaint();
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Currently not used.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Unselect the button if an other one is clicked.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != this && e.getActionCommand().equals("clicked")) {
            unselect();
        }
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets boolean selected to true or false depending on
     * the actually selected mode.
     *
     * @param selected the selected to set
     */
    public void setSelected(boolean mode) {
        if (mode) {
            select();
        } else {
            unselect();
        }
    }
}
