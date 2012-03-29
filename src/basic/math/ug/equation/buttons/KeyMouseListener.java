/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import eu.mihosoft.vrl.v3d.AppearanceGenerator;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Listner that is responsable for key and mouse events.
 * Contains a part of the logic which is necessary to allow picking in the <code>PickPlotter</code>.
 *
 * @todo set coordinates for drawing a selection rectangle
 *
 * @author night
 */
public class KeyMouseListener implements MouseListener, MouseMotionListener {

    private PickCanvas pickCanvas;
    private CustomCanvas canvas;
    //
    private HashSet<Shape3D> shape3DSet;
    //
    // start- und endpunkt des Auswahlrechtecks
    private Point3d begin;
    private Point3d end;
    //
    AppearanceGenerator generator = null;

    public KeyMouseListener(CustomCanvas canvas, PickCanvas pickCanvas) {
        this.canvas = canvas;
        this.pickCanvas = pickCanvas;

        shape3DSet = new HashSet<Shape3D>();

        begin = new Point3d();
        end = new Point3d();
        generator = new AppearanceGenerator();
    }

    /**
     * Contains the a part of the logic which allows to pick/choose some
     * shapes depending which mode is activated in PickPlotter.
     *
     * @param e mouse event that is used to pick on an shape
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (getButtonMode().equals(ToolButtonMode.PAINT)) {

            pickCanvas.setTolerance(1f);
            pickCanvas.setShapeLocation(e);
            PickResult result = pickCanvas.pickClosest();

            if (result != null) {
                result = pickFirstViewable(result);

                if (result != null) {
                    shapeManipulations(result);
                }
            }
        }

        if (getButtonMode().equals(ToolButtonMode.TRANSFORMATION)) {

            canvas.getTransformationButton().requestFocus();

            pickCanvas.setTolerance(10f);
            pickCanvas.setShapeLocation(e);
            PickResult result = pickCanvas.pickClosest();

            TransformationButton transformButton =
                    (TransformationButton) canvas.getTransformationButton();
            if (result != null) {
                transformButton.translateObject(result);
            }
        }
    }

    /**<p>
     * Sets the beginning point of the selction rectangle,
     * if the selection mode is activated.
     * </p>
     * @param e mouse event that is used to get the coordinates
     */
    @Override
    public void mousePressed(MouseEvent e) {

        if (getButtonMode().equals(ToolButtonMode.SELECTION)) {
            begin.x = e.getX();
            begin.y = e.getY();
        }
    }

    /**
     * disables the capability of the rectangle to be drawn and forces a repaint to
     * remove it from view, if the selection mode is activated.
     * @param e is unused
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (getButtonMode().equals(ToolButtonMode.SELECTION) && canvas.isSelectionRectangleEnabled()) {

            canvas.disableSelectionRectangle();
            canvas.repaint();

            /* START alle shapes unterhalb des rechtecks auswählen */

            Rectangle2D rec = canvas.getSelectionRectangle();

            pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
            float tolerance = 8f;

            /*falls auswahlbereich zu klein dann setzen wir die toleranz runter*/
            if ((rec.getHeight() <= tolerance * 4) || (rec.getWidth() <= tolerance * 4)) {
                tolerance = 1f;
            }

            pickCanvas.setTolerance(tolerance * 2);

            final ArrayList<PickResult> selectionResult = new ArrayList<PickResult>();

            int xPos = (int) (rec.getX() + tolerance * 2);
            int yPos = (int) (rec.getY() + tolerance * 2);
            int width = (int) (rec.getX() + rec.getWidth() - tolerance * 2);
            int height = (int) (rec.getY() + rec.getHeight() - tolerance * 2);

            //manuel pick in x- and y- direction in the inner of the selection rectangle
            //the used step size is tolerance
            for (int x = xPos; x < width; x += tolerance) {
                for (int y = yPos; y < height; y += tolerance) {
                    pickCanvas.setShapeLocation(x, y);

                    PickResult[] results = pickCanvas.pickAll();

                    if (results != null) {
                        for (PickResult r : results) {
                            if (r != null) {
                                selectionResult.add(r);
                            }
                        }
                    }
                }
            }




            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    for (PickResult result : selectionResult) {

                        shapeManipulations(result);
                    }
                }
            };

            new Thread(runnable).start();




            /* ENDE alle shapes unterhalb des rechtecks auswählen */

        }
    }

    /**
     * unused
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * unused
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**<p>
     * Sets the end point of the selction rectangle, if the selection mode is activated.
     * Calls setRectangle to create the rectangle, allows it to be drawn and force a repaint.
     * </p><p>
     * Allows to selected shape after shape by paint with the mouse
     * on the displayed geometry if the paint mode is selected.
     * </p>
     *
     * @param e mouse event that is used to get the coordinates
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        if (getButtonMode().equals(ToolButtonMode.SELECTION)) {
            end.x = e.getX();
            end.y = e.getY();

            canvas.setRectangle(begin, end);
            canvas.enableSelectionRectangle();
            canvas.repaint();
        }

        if (getButtonMode().equals(ToolButtonMode.PAINT)) {

            pickCanvas.setTolerance(1f);
            pickCanvas.setShapeLocation(e);
            PickResult result = pickCanvas.pickClosest();

            if (result != null) {
                result = pickFirstViewable(result);

                if (result != null) {
                    shapeManipulations(result);
                }

            }
        }
    }

    /**
     * unused
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Generates a Shape3D from the PickResult and sets the appearance of the shape.
     * The appearance depence on state of the shift button on the keyboard is pressed or not.
     *
     * @param result the used PickResult
     * @param e MouseEvent which contains the information if SHIFT is pressed
     */
    private void shapeManipulations(PickResult result) {

        if (result == null) {
            System.out.println("PickResult is null");
        } else {
            Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);

            if (s != null) {
                /*get the used color and set the appearance*/
//                AppearanceGenerator generator = new AppearanceGenerator();


                Color color = ((ColorButton) canvas.getColorButtonGroup().getSelectedButton()).getColor();
                s.setAppearance(generator.getColoredAppearance(color,0.f));

                if (canvas.getColorButtonGroup().getMode().equals(ColorButtonMode.DESELECT)) {

                    shape3DSet.remove(s);
                    
                    
                    //methode nicht mehr vorhanden !?!? 17.09.2011 beim ueberarbeiten zum plugin
//                    setName sollte aber da sein !!!
//                    s.setName(null); 
                    
//                   moegliche alternative zu setName !?!
                    s.setUserData(null);

                } else {

                    shape3DSet.add(s);
                    String numericMethod = "none";

                    if (canvas.getColorButtonGroup().getMode().equals(ColorButtonMode.DIRICHLET)) {
                        numericMethod = ColorButtonMode.DIRICHLET;

                    } else if (canvas.getColorButtonGroup().getMode().equals(ColorButtonMode.NEUMANN)) {
                        numericMethod = ColorButtonMode.NEUMANN;
                    }

//                    s.setName(numericMethod);
                    
//                   moegliche alternative zu setName !?!
                    s.setUserData(numericMethod);
                }
            } else {
                System.out.println("shape has value null");
            }
        }
    }

    //test um den abstand zum bildschirm zu bestimmen falls er kleiner ist als
    //der min darstellbare abstand nehme das nächste objekt auf pickstrahl
    //das weiter weg vom monitor ist
    /**
     * Calculates the distance between the picked part of the shown object and
     * the camera position. Is the distance smaller than the minimum distance for
     * visualisation then check if there are other pickable parts of the object
     * that are farther away and distance to camera is bigger then the minimum
     * distance. If there aren´t any result is set to null, else the founded
     * PickResult is returned.
     * @param result PickResult which contains the nearest pick
     * @return result PickResult which fulfill the condition if one exist
     */
    private PickResult pickFirstViewable(PickResult result) {

        double frontClipDistance = canvas.getUniverse().getUniverse().
                getViewer().getView().getFrontClipDistance();

        //get the position/coordinates of the camera
        Vector3d camTransVec = new Vector3d();
        Transform3D t3d = new Transform3D();
        canvas.getUniverse().getCamGroup().getTransform(t3d);
        t3d.get(camTransVec);
        Point3d camPos = new Point3d(camTransVec);
//        System.out.println("CameraPoint z: " + camPos.getZ());

        //check if there is an intersection
        if (result.getClosestIntersection(camPos) != null) {

            double distance = result.getClosestIntersection(camPos).getDistance();
//            System.out.println("First [0] Distance: " + distance);
//            System.out.println("FrontClipDistance: " + frontClipDistance);


            //check if the distance from closest pick to camera position is smaller
            //than the distanc from camera to the minimum distance from camera
            //(frontClipDis). FrontClipDis is multipliat by a factor because of
            //resulting calculation error if isn´t done
            byte factor = 5;
            if (distance <= frontClipDistance * factor) {
                //|| (result.getClosestIntersection(camPos).getPointCoordinates().getZ() > camPos.getZ() ) ) {

                PickResult[] resultArray = pickCanvas.pickAllSorted();

                for (int i = 1; i < resultArray.length; i++) {

                    result = resultArray[i];
                    distance = result.getClosestIntersection(camPos).getDistance();
//                    System.out.println("["+i+"]Distance: " + distance);

                    // if the distance from the now/next selected pickresult is
                    // enough away from camera position then return this pick as
                    // as final PickResult
                    if (distance > frontClipDistance * factor) {
                        //&&(result.getClosestIntersection(camPos).getPointCoordinates().getZ() < camPos.getZ()) ) {
                        break;
                    }
                    //set result null if no pick fulfill the condition
                    if ((i == resultArray.length) && (distance <= frontClipDistance * factor)) {
                        result = null;
                    }
                }
            }
        }

        return result;
    }

    /**
     * @return the buttonMode
     */
    public String getButtonMode() {
        return canvas.getSelectionButtonGroup().getMode();
    }

    /**
     * @return the shape3DSet
     */
    public HashSet<Shape3D> getShape3DSet() {
        return shape3DSet;
    }
}
