/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.helpers;

import basic.math.ug.equation.buttons.KeyMouseListener;
import basic.math.ug.equation.buttons.CustomCanvas;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import eu.mihosoft.vrl.types.UniverseCreator;
import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.types.VOffscreenCanvas3D;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * This class generates all necassary setting (light, mousecontroll, bounds)
 * for the <code>PickPlotter</code>
 *
 * @author night
 */
public class PickUniverseCreator implements UniverseCreator {

    //hierin wird alles dargestellt
    private SimpleUniverse universe;
    private VCanvas3D canvas;
//    private CustomCanvas canvas;

    /*hieran werden alle Objekte angehängt
    erlaubt dynamisches hinzufügen & entfernen von Elementen */
    private BranchGroup sceneBranchGroup;
    //erlaubt das verschieben & rotieren aller objekte
    private TransformGroup rootGroup;
    private TransformGroup transGroup; // war für rotationpunktsetzVersuch nötig
    /*steuert die kamera*/
    private TransformGroup camTransform;
    private BoundingSphere bounds; // innerhalb der kugel liegt der sichtbare bereich
    private Point3d camaraPosition = new Point3d(0, 0, 20);
    //zum anklicken von objekten im canvas benötigt
    private PickCanvas pickCanvas;
    private VOffscreenCanvas3D offscreenCanvas;
    private KeyMouseListener keyMouseListener;
    private MouseRotate rotateBehavior;
    private MouseWheelZoom zoomBehavior;
    private MouseTranslate translateBehavior;

    /**
     * empty constructure.
     * initialization is made by init().
     */
    public PickUniverseCreator() {
    }

    /**
     * makes the start initialization.
     *
     * @param canvas the canvas on which all is drwan
     */
    @Override
    public void init(VCanvas3D canvas) {

        this.canvas = canvas;

        if (canvas instanceof CustomCanvas) {
            CustomCanvas cCanvas = (CustomCanvas) canvas;

            universe = new SimpleUniverse(canvas.getOffscreenCanvas3D());

            //wird zum erstellen von Bildern der dargestellten Scene benötigt
            offscreenCanvas = new VOffscreenCanvas3D(SimpleUniverse.getPreferredConfiguration());
            universe.getViewer().getView().addCanvas3D(offscreenCanvas);

            createSceneGraph();
            viewSettings();
            mouseControls();

            /*bereich zur bereitstellung der Anklick-option*/
            pickCanvas = new PickCanvas(canvas.getOffscreenCanvas3D(), getBranchGroup());
            pickCanvas.setMode(PickCanvas.GEOMETRY);//legt die genauigkeit der picks fest(teilweise) //in den mouse-funktionen gesetzt

            /*ENDE bereich zur bereitstellung der Anklick-option*/
            keyMouseListener = new KeyMouseListener(cCanvas, pickCanvas);
            this.canvas.addMouseListener(getKeyMouseListener());
            this.canvas.addMouseMotionListener(getKeyMouseListener());

            //compile muss der letzte Befehl sein bevor BranchGroup dem universe hinzugefügt wird
            //nach compile ist kein hinzufügen zur BranchGroup nicht mehr möglich
            getBranchGroup().compile();
            universe.addBranchGraph(getBranchGroup());
        }
    }

    /**
     * Returns the main TransformGroup for the camera of the PickUniverseCreator.
     * @return the main TransformGroup for the camera of the PickUniverseCreator
     */
    @Override
    public TransformGroup getCamGroup() {
        return camTransform;
    }

    /**
     * Returns the canvas of the PickUniverseCreator.
     * @return the canvas of the PickUniverseCreator
     */
    @Override
    public VCanvas3D getCanvas() {
        return canvas;
    }

    /**
     * Returns the VOffscreenCanvas3D of the PickUniverseCreator.
     * @return the VOffscreenCanvas3D of the PickUniverseCreator
     */
    @Override
    public VOffscreenCanvas3D getOffscreenCanvas() {
        return offscreenCanvas;
    }

    /**
     * Returns the universe of the PickUniverseCreator.
     * @return the universe of the PickUniverseCreator
     */
    @Override
    public SimpleUniverse getUniverse() {
        return universe;
    }

    /**
     * Returns the main TransformGroup of the PickUniverseCreator.
     * @return the main TransformGroup of the PickUniverseCreator
     */
    @Override
    public TransformGroup getRootGroup() {
        return rootGroup;
    }

    /**
     * Returns the <code>KeyMouseListener</code> which react on mouse and key
     * events and handle them.
     *
     * @return the keyMouseListener
     */
    public KeyMouseListener getKeyMouseListener() {
        return keyMouseListener;
    }

    /**
     * @return the sceneBranchGroup
     */
    public BranchGroup getBranchGroup() {
        return sceneBranchGroup;
    }

    /**
     * @return the rotateBehavior
     */
    public MouseRotate getRotateBehavior() {
        return rotateBehavior;
    }

    /**
     * @return the zoomBehavior
     */
    public MouseWheelZoom getZoomBehavior() {
        return zoomBehavior;
    }

    /**
     * @return the translateBehavior
     */
    public MouseTranslate getTranslateBehavior() {
        return translateBehavior;
    }

    /**
     * @return the camaraPosition
     */
    public Point3d getCamaraPosition() {
        return camaraPosition;
    }

    /**
     * @return the transGroup
     */
    public TransformGroup getTransGroup() {
        return transGroup;
    }

    /* lädt die objekte in die Scene*/
    /**
     * Generats the scene graph.
     * (inizialize  Branch- and TransferGroup)
     * Sets capabilities for the main TransformGroup.
     */
    private void createSceneGraph() {

        sceneBranchGroup = new BranchGroup();
        transGroup = new TransformGroup();
        rootGroup = new TransformGroup();

        // erlaubt verschiedene operationen auf der sceneTransGroup
        getRootGroup().setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        getRootGroup().setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        getRootGroup().setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        getRootGroup().setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        getRootGroup().setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        getRootGroup().setCapability(BranchGroup.ALLOW_DETACH);

        getTransGroup().setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        getTransGroup().setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        getTransGroup().setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        getTransGroup().setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        getTransGroup().setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        getTransGroup().setCapability(BranchGroup.ALLOW_DETACH);

        getTransGroup().addChild(getRootGroup());
        getBranchGroup().addChild(getTransGroup());

//        getBranchGroup().addChild(sceneTransGroup);
    }

    /*legt alle einstellungen bzgl der Sicht fest*/
    /**
     *  Set the view settings and calls lightScene().
     */
    private void viewSettings() {

        Point3d center = new Point3d(0, 0, 0);//mitte des manipulierbaren Bereichs
        Integer maxViewDistance = 1000;//radius des manipulierbaren Bereichs
        bounds = new BoundingSphere(center, maxViewDistance); //manipulierbarer Bereich (innerhalb der Kugel)

        lightScene();

        //setze min und max entfernung zur kammera die noch angezeigt werden soll
        universe.getViewer().getView().setFrontClipDistance(0.1);
        universe.getViewer().getView().setBackClipDistance(maxViewDistance);// * 0.9);//90% der GrenzKugel
        universe.getViewer().getView().setMinimumFrameCycleTime(10);
        universe.getViewingPlatform().setNominalViewingTransform();

        cameraViewDirection();
    }

    /*setze alle Lichteinstellungen*/
    /**
     * Sets the light settings.
     */
    private void lightScene() {

        Color3f lightWhite = new Color3f(0.6f, 0.6f, 0.6f);

        //setze das Umgebungslicht, das bescheint alles
        AmbientLight ambientLight = new AmbientLight(lightWhite);
        ambientLight.setInfluencingBounds(bounds);
        getBranchGroup().addChild(ambientLight);

        //setze das gerichtete Licht, scheint nur aus einer Richtung

        Vector3f lightDirection1 = new Vector3f(0f, -1.0f, -1.0f);
        Vector3f lightDirection2 = new Vector3f(-1f, 1.0f, -1.0f);

//        //alle lichter scheinen von oben (.., 10, ..)
//        Vector3f lightDirection1 = new Vector3f(-10.0f, 10.0f, 10.0f);  //links, vorne
//        Vector3f lightDirection2 = new Vector3f(10.0f, 10.0f, 10.0f);   //rechts, vorne
//        Vector3f lightDirection3 = new Vector3f(10.0f, 10.0f, -10.0f);  //rechts, hinten
//        Vector3f lightDirection4 = new Vector3f(-10.0f, 10.0f, -10.0f); //links, hinten
//        Vector3f lightDirection5 = new Vector3f(0.0f, 10.0f, 0.0f);     //direkt über Ursprung

        DirectionalLight directLight1 = new DirectionalLight(lightWhite, lightDirection1);
        directLight1.setInfluencingBounds(bounds);
        getBranchGroup().addChild(directLight1);

        DirectionalLight directLight2 = new DirectionalLight(lightWhite, lightDirection2);
        directLight2.setInfluencingBounds(bounds);
        getBranchGroup().addChild(directLight2);

//        DirectionalLight directLight3 = new DirectionalLight(white, lightDirection3);
//        directLight3.setInfluencingBounds(bounds);
//        getBranchGroup().addChild(directLight3);
//
//        DirectionalLight directLight4 = new DirectionalLight(white, lightDirection4);
//        directLight4.setInfluencingBounds(bounds);
//        getBranchGroup().addChild(directLight4);
//
//        DirectionalLight directLight5 = new DirectionalLight(white, lightDirection5);
//        directLight5.setInfluencingBounds(bounds);
//        getBranchGroup().addChild(directLight5);
    }

    /*position und blickrichtung der Kamera setzen*/
    /**
     * Sets the camera settings.
     */
    private void cameraViewDirection() {

        ViewingPlatform viewplatform = universe.getViewingPlatform();
        camTransform = viewplatform.getViewPlatformTransform();

        Transform3D t3d = new Transform3D();
        camTransform.getTransform(t3d);

        //argumente: position des Betrachters, wohin schaun, Vector der angibt wo oben ist
        t3d.lookAt(getCamaraPosition(), new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
        t3d.invert();

        /*Buch: Pro Java 6 3D, Seite26 - 27*/

        camTransform.setTransform(t3d);
    }

    /**
     * Sets the mouse control / behavior
     * and contains a bit of the button mode logic
     */
    private void mouseControls() {
        // standard mouse rotation
//        rotateBehavior = new MouseRotate(canvas, sceneTransGroup);
            rotateBehavior = new MouseRotate(canvas,getTransGroup());

        getBranchGroup().addChild(getRotateBehavior());
        getRotateBehavior().setSchedulingBounds(bounds);
        getRotateBehavior().setFactor(0.01);

        getBranchGroup().setCapability(BranchGroup.ALLOW_DETACH);
        getBranchGroup().setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        getBranchGroup().setCapability(BranchGroup.ALLOW_CHILDREN_READ);


        // standard zoom behavior
        zoomBehavior = new MouseWheelZoom(canvas);
//        getZoomBehavior().setTransformGroup(sceneTransGroup);
        getZoomBehavior().setTransformGroup(getTransGroup());
        getZoomBehavior().setSchedulingBounds(bounds);
        getBranchGroup().addChild(getZoomBehavior());
        getZoomBehavior().setFactor(0.05);


        // standard translate behavior
        translateBehavior = new MouseTranslate(canvas);
        getTranslateBehavior().setFactor(0.05);
//        getTranslateBehavior().setTransformGroup(sceneTransGroup);
        getTranslateBehavior().setTransformGroup(getTransGroup());
        getTranslateBehavior().setSchedulingBounds(bounds);
        getBranchGroup().addChild(getTranslateBehavior());
    }

    @Override
    public void setZoomFactor(double d) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getZoomFactor() {
        throw new UnsupportedOperationException("Not supported yet.");
//        return 1.0;
    }

    @Override
    public void dispose() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}

//    /**
//     *  Returns a shape which is generated from the given geometry
//     * @param geo Geometry which is used to build a shape
//     * @return the generated shape
//     */
//    private Shape3D getShape(Geometry geo) {
//
//        AppearanceGenerator a = new AppearanceGenerator();
//
//        return new Shape3D(geo, a.getColoredAppearance(Color.blue));
//    }