/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import eu.mihosoft.vrl.animation.AnimationManager;
import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.v3d.TranslateGroupAnimation;
import eu.mihosoft.vrl.v3d.V3DMath;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * A specialist ToolButton.
 * Used to change the mode in the PickPlotter.
 * If TransformationButton is active it is possible to rotate the shown geometry.
 *
 * Contains the logic how to translate the geometry if the user wants to center
 * a clicked part of the geometry.
 * Additionaly it contains the logic for moving and zooming with the keyboard.
 *
 * @author night
 */
public class TransformationButton extends ToolButton implements KeyListener {

    private CustomCanvas canvas;
    private double moveFactor = 0.51;
    private static final Vector3d MOVE_RIGHT = new Vector3d(1, 0, 0);
    private static final Vector3d MOVE_LEFT = new Vector3d(-1, 0, 0);
    private static final Vector3d MOVE_UP = new Vector3d(0, 1, 0);
    private static final Vector3d MOVE_DOWN = new Vector3d(0, -1, 0);
    private static final Vector3d ZOOM_IN = new Vector3d(0, 0, 1);
    private static final Vector3d ZOOM_OUT = new Vector3d(0, 0, -1);

    public TransformationButton(VCanvas3D canvas) {
        super(canvas);

        if (canvas instanceof CustomCanvas) {
            this.canvas = (CustomCanvas) canvas;
        }

        addKeyListener(this);

    }

    /**
     *  Draws the characteristic symbol for the TransformationButton
     * @param g2 the area where the symbol is drawn
     */
    @Override
    protected void drawIcon(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        Color strokeColor = Color.white;
//
//        if (style != null) {
//            strokeColor = style.getMessageBoxIconColor();
//        }
//        g2.setColor(strokeColor);

        float w = buffer.getWidth();
        float h = buffer.getHeight();

        Polygon cross = new Polygon();

        Point2f p1 = new Point2f(0, h / 2);
        Point2f p2 = new Point2f(w * 1 / 8, h * 3 / 8);
        Point2f p3 = new Point2f(w * 3 / 16, h * 7 / 16);
        Point2f p4 = new Point2f(w * 7 / 16, h * 7 / 16);
        Point2f p5 = new Point2f(w * 7 / 16, h * 3 / 16);
        Point2f p6 = new Point2f(w * 3 / 8, h / 8);
        Point2f p7 = new Point2f(w / 2, 0);
        Point2f p8 = new Point2f(w * 5 / 8, h / 8);
        Point2f p9 = new Point2f(w * 9 / 16, h * 3 / 16);
        Point2f p10 = new Point2f(w * 9 / 16, h * 7 / 16);
        Point2f p11 = new Point2f(w * 13 / 16, h * 7 / 16);
        Point2f p12 = new Point2f(w * 7 / 8, h * 3 / 8);
        Point2f p13 = new Point2f(w, h / 2);
        Point2f p14 = new Point2f(w * 7 / 8, h * 5 / 8);
        Point2f p15 = new Point2f(w * 13 / 16, h * 9 / 16);
        Point2f p16 = new Point2f(w * 9 / 16, h * 9 / 16);
        Point2f p17 = new Point2f(w * 9 / 16, h * 13 / 16);
        Point2f p18 = new Point2f(w * 5 / 8, h * 7 / 8);
        Point2f p19 = new Point2f(w / 2, h);
        Point2f p20 = new Point2f(w * 3 / 8, h * 7 / 8);
        Point2f p21 = new Point2f(w * 7 / 16, h * 13 / 16);
        Point2f p22 = new Point2f(w * 7 / 16, h * 9 / 16);
        Point2f p23 = new Point2f(w * 3 / 16, h * 9 / 16);
        Point2f p24 = new Point2f(w / 8, h * 5 / 8);

//        cross.addPoint((int) p1.getX(), (int) p1.getY());
//        cross.addPoint((int) p2.getX(), (int) p2.getY());
//        cross.addPoint((int) p3.getX(), (int) p3.getY());
//        cross.addPoint((int) p4.getX(), (int) p4.getY());
//        cross.addPoint((int) p5.getX(), (int) p5.getY());
//        cross.addPoint((int) p6.getX(), (int) p6.getY());
//        cross.addPoint((int) p7.getX(), (int) p7.getY());
//        cross.addPoint((int) p8.getX(), (int) p8.getY());
//        cross.addPoint((int) p9.getX(), (int) p9.getY());
//        cross.addPoint((int) p10.getX(), (int) p10.getY());
//        cross.addPoint((int) p11.getX(), (int) p11.getY());
//        cross.addPoint((int) p12.getX(), (int) p12.getY());
//        cross.addPoint((int) p13.getX(), (int) p13.getY());
//        cross.addPoint((int) p14.getX(), (int) p14.getY());
//        cross.addPoint((int) p15.getX(), (int) p15.getY());
//        cross.addPoint((int) p16.getX(), (int) p16.getY());
//        cross.addPoint((int) p17.getX(), (int) p17.getY());
//        cross.addPoint((int) p18.getX(), (int) p18.getY());
//        cross.addPoint((int) p19.getX(), (int) p19.getY());
//        cross.addPoint((int) p20.getX(), (int) p20.getY());
//        cross.addPoint((int) p21.getX(), (int) p21.getY());
//        cross.addPoint((int) p22.getX(), (int) p22.getY());
//        cross.addPoint((int) p23.getX(), (int) p23.getY());
//        cross.addPoint((int) p24.getX(), (int) p24.getY());
        
        cross.addPoint((int) p1.x,  (int) p1.y );
        cross.addPoint((int) p2.x,  (int) p2.y );
        cross.addPoint((int) p3.x,  (int) p3.y );
        cross.addPoint((int) p4.x,  (int) p4.y );
        cross.addPoint((int) p5.x,  (int) p5.y );
        cross.addPoint((int) p6.x,  (int) p6.y );
        cross.addPoint((int) p7.x,  (int) p7.y );
        cross.addPoint((int) p8.x,  (int) p8.y );
        cross.addPoint((int) p9.x,  (int) p9.y );
        cross.addPoint((int) p10.x, (int) p10.y );
        cross.addPoint((int) p11.x, (int) p11.y );
        cross.addPoint((int) p12.x, (int) p12.y );
        cross.addPoint((int) p13.x, (int) p13.y );
        cross.addPoint((int) p14.x, (int) p14.y );
        cross.addPoint((int) p15.x, (int) p15.y );
        cross.addPoint((int) p16.x, (int) p16.y );
        cross.addPoint((int) p17.x, (int) p17.y );
        cross.addPoint((int) p18.x, (int) p18.y );
        cross.addPoint((int) p19.x, (int) p19.y );
        cross.addPoint((int) p20.x, (int) p20.y );
        cross.addPoint((int) p21.x, (int) p21.y );
        cross.addPoint((int) p22.x, (int) p22.y );
        cross.addPoint((int) p23.x, (int) p23.y );
        cross.addPoint((int) p24.x, (int) p24.y );

        g2.fill(cross);
    }

    /**
     * @return the canvas
     */
    public CustomCanvas getCanvas() {
        return canvas;
    }

    /**
     * Moves the observed that way that the point where the user click on the object
     * is posed at the center of the display of PickPlotter without changing
     * the zoom factor.
     *
     * @param result PickResult used to get the information where the users pick.
     */
    public void translateObject(PickResult result) {

        // the move direction
        Vector3d direction = new Vector3d();

        // the intersection coordinates (intersection between mouse pointer ray and shape)
        Point3d coordinates = new Point3d();
        PickIntersection intersection = null;

        // get the object that describes the intersection between mouse pointer ray and shape
        if (result.numIntersections() > 0) {
            intersection = result.getIntersection(0);
        }

        if (intersection != null) {
            // global intersection coordinates
            coordinates = intersection.getPointCoordinatesVW();
//            System.out.println("Coordinates:" + System.currentTimeMillis() / 1000.0d + " :: " + coordinates);

            // compute global coordinates of transGroup node
            Point3d transGroupPos =
                    V3DMath.getGlobalCoordinates(getCanvas().getUniverse().getTransGroup());

            // init direction vector with global coordinates of transGroup node
            direction.set(transGroupPos);

            // subtract coordinates from direction vector which is now the final
            // direction vector except that it uses global coordinates
            direction.sub(coordinates);

            translateGroupGlobal(getCanvas().getUniverse().getTransGroup(),
                    getCanvas().getUniverse().getRootGroup(),
                    direction, 1.0);

            // center transGroup
            Vector3d centerDirection = new Vector3d();

            // init with negative direction to the position of transGroup
            // and set z=0 for not translating in z-direction
            centerDirection.sub(transGroupPos);
//            centerDirection.setZ(0);
            centerDirection.z=0; //setZ(0);

            // transform the direction form global to local coordinatesystem
//        transformGlobalToLocal(centerDirection, getCanvas().getUniverse().getTransGroup());
            V3DMath.globalToLocal(centerDirection, getCanvas().getUniverse().getTransGroup());

            // apply the translation into center
            translateGroup(getCanvas().getUniverse().getTransGroup(), centerDirection, 1.0);
        } // end if no intersection
    }


    /**
     * Translates the given TransformGroup in the direction of the specified
     * direction vector. The translation is scaled by the moveFactor value.
     * The translation is smooth. This is realized by an animation using
     * linear interpolation.
     *
     * @param direction defines the translation (local coordinates)
     * @param moveFactor value which decides how much we go in the direction
     */
    public void translateGroup(TransformGroup group, Vector3d direction, double moveFactor) {

        Vector3d translateVec = new Vector3d(direction);
        translateVec.scale(moveFactor);

        TranslateGroupAnimation animation =
                new TranslateGroupAnimation(group, translateVec);

        animation.setDuration(0.3);

        AnimationManager manager =
                getCanvas().getTypeRepresentation().getMainCanvas().getAnimationManager();

        manager.addAnimation(animation);
    }

    /**
     * Performs a translation as specified by @link{#translateGroup}. The
     * difference is that this method takes a direction vector in global coordinates
     * and transforms the vector to local coordinates relative to the specified
     * parent group. The chold group is the group that is translated.
     *
     * @param parentGroup the parent group
     * @param group the child group to translate
     * @param direction defines the translation (global coordinates)
     * @param moveFactor describes how far the translation is done in the direction
     */
    public void translateGroupGlobal(TransformGroup parentGroup, TransformGroup group,
            Vector3d direction, double moveFactor) {

        Vector3d vec = new Vector3d(direction);
//        transformGlobalToLocal(vec, parentGroup);
        V3DMath.globalToLocal(vec, parentGroup);
        translateGroup(group, vec, moveFactor);
    }

    /**
     * unused
     * @param e KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * unused
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Depending on the key code of the KeyEvent translateGroup(Vector, double)
     * is called with correspondent values. KeyPressed() consumes also the KeyEvent
     * at the end to prevent side effects, like scrolling in VRL-Studio
     * when you want to translate the object in PickPlotter.
     *
     * @param e KeyEvent that contains
     */
    @Override
    public void keyPressed(KeyEvent e) {

        TransformGroup transGroup = getCanvas().getUniverse().getTransGroup();

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP:
//                System.out.println("move up , VK_UP");
                translateGroupGlobal(transGroup, transGroup, MOVE_UP, moveFactor);
                break;

            case KeyEvent.VK_DOWN:
//                System.out.println("move down , VK_DOWN");
                translateGroupGlobal(transGroup, transGroup, MOVE_DOWN, moveFactor);
                break;

            case KeyEvent.VK_LEFT:
//                System.out.println("Translate left , VK_LEFT");
                translateGroupGlobal(transGroup, transGroup, MOVE_LEFT, moveFactor);
                break;

            case KeyEvent.VK_RIGHT:
//                System.out.println("Translate right , VK_RIGHT");
                translateGroupGlobal(transGroup, transGroup, MOVE_RIGHT, moveFactor);
                break;

            case KeyEvent.VK_1:
                moveFactor -= 0.1;
                System.out.println("moveFactor" + moveFactor);
                break;

            case KeyEvent.VK_2:
                moveFactor += 0.1;
                System.out.println("moveFactor" + moveFactor);
                break;
        }

        if (e.getKeyChar() == '+') {
//            System.out.println("Zoom in , VK_PLUS");
            translateGroupGlobal(transGroup, transGroup, ZOOM_IN, moveFactor);
        }
        if (e.getKeyChar() == '-') {
//            System.out.println("Zoom out , VK_MINUS");
            translateGroupGlobal(transGroup, transGroup, ZOOM_OUT, moveFactor);
        }

        e.consume();
    }
}
// area of unused methods (backup):

//    /**
//     * Transform the vector form global to local coordinatesystem.
//     *
//     * @param vec The vector that should be transformed
//     * @param localGroup The TransformGroup which represents the lokal coordinatesystem
//     */
//    public void transformGlobalToLocal(Vector3d vec, TransformGroup localGroup) {
//
//        // get transform object from localGroup
//        Transform3D transGroupTransform = new Transform3D();
//        localGroup.getTransform(transGroupTransform);
//
//        // get rotation of localGroup transform
//        Quat4d rotation = new Quat4d();
//        transGroupTransform.get(rotation);
//
//        // invert rotation angle
//        AxisAngle4d rotation2 = new AxisAngle4d();
//        rotation2.set(rotation);
//        double angle = rotation2.getAngle();
//        rotation2.setAngle(-angle);
//
//        // set inverted rotation
//        transGroupTransform.setRotation(rotation2);
//
//        // apply the transformation
//        transGroupTransform.transform(vec);
//    }