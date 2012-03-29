/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.tools;

import basic.math.helpers.ErrorMessageWriter;
import basic.math.ug.equation.buttons.CustomCanvas;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.types.Shape3DArrayType;
import eu.mihosoft.vrl.types.UniverseCreator;
import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.v3d.AppearanceGenerator;
import eu.mihosoft.vrl.v3d.IndexedGeometryList;
import eu.mihosoft.vrl.v3d.Shape3DArray;
import eu.mihosoft.vrl.v3d.TxT2Geometry;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;

/**
 * This is class where the loaded geometry can be looked at, rotated, zoomed,
 * translated and some areas (shapes) choosed.
 *
 * @todo the choosen shapes print information into e.g a file
 *       information of the choosen shape influence the calculation of the solution
 *
 * @author night
 */
@ObjectInfo(name = "PickPlotter")
@ComponentInfo(name = "PickPlotter", category = "BasicMath")
public class PickPlotter implements Serializable {

    private static final long serialVersionUID = 1;
    private transient VisualCanvas mainCanvas;


    @MethodInfo(noGUI = true, callOptions = "assign-to-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    /**<p>
     * Returns an array of shapes which all belongs to one geomerty.
     * The geomerty is loaded from the file, which is given as a parameter.
     * </p>
     *
     * @param methodRequest
     * @param file the file where geometry is stored in.
     * @return an array with shapes
     * @throws IOException
     */
    @MethodInfo( valueStyle = "pick", valueName = " ", interactive = false,
    valueOptions = "width=400;height=340")
    public Shape3DArray getShapeArray(MethodRequest methodRequest,
            @ParamInfo(style = "load-dialog", options = "invokeOnChange=true") File file) throws IOException {

        if( ! file.exists() ){

            DefaultMethodRepresentation mRep = methodRequest.getMethod();
//                    mainCanvas.getMethodRepresentation(this,
//                    visualID, "getShapeArray", new Class[]{ File.class});

            Connector c = mRep.getParameter(0).getConnector();

            ErrorMessageWriter.writeErrorMessage(mainCanvas, "File doesn´t exist",
                    "The given path didn´t lead to an existing File !", c);
            
            return null;
        }

        TxT2Geometry txt = new TxT2Geometry();
        VTriangleArray triArray = txt.loadAsVTriangleArray(file);
        IndexedGeometryList list = triArray.toIndexedGeometryList(false);

        //folge von aufrufen um an den univerCreator zukommen und über den an den KeyMouseListener
        DefaultMethodRepresentation mRep = methodRequest.getMethod();
//                mainCanvas.getMethodRepresentation(this, visualID, "getShapeArray", new Class[]{File.class});

        TypeRepresentationBase tRep = mRep.getReturnValue();
        Shape3DArrayType shapeType = (Shape3DArrayType) tRep;

//        PickUniverseCreator universeCreator = (PickUniverseCreator) shapeType.getUniverseCreator();
        UniverseCreator universeCreator = shapeType.getUniverseCreator();

//        //sorgt dafür das der KeyMouseListener die Knoten kennt um deren Infos auf der Konsole auszugeben:
//        universeCreator.getKeyMouseListener().setIndexedGeometryList(list);
//        universeCreator.getKeyMouseListener().setVTriangleArray(triArray);

        //sorgt dafür das der SkriptButton die Knoten kennt
        //um zB. deren Infos auf der Konsole auszugeben:
        VCanvas3D can = universeCreator.getCanvas();
        if(can instanceof CustomCanvas){
            CustomCanvas canvas = (CustomCanvas) can;
            canvas.getSkriptButton().setIndexedGeometryList(list);
            canvas.getSkriptButton().setTriangleArray(triArray);
        }

        AppearanceGenerator a = new AppearanceGenerator();
        Shape3DArray shapeVector = new Shape3DArray();

//        int i = 0;

        for (Geometry g : list.getGeometries()) {
            Color c = Color.BLUE;

//            if (i % 2 == 0) { c = Color.RED; }
//            i++;

            Shape3D s = new Shape3D(g, a.getColoredAppearance(c, 0.f));
            s.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
            s.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
            s.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
            s.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

//            s.setCapability(Shape3D.ALLOW_PICKABLE_READ);
//            s.setCapability(Shape3D.ALLOW_PICKABLE_WRITE);
//            s.setCapability(PickTool.BOUNDS);
//            s.setCapability(Shape3D.ENABLE_PICK_REPORTING);

            shapeVector.add(s);
        }

        return shapeVector;
    }

//    @MethodInfo( valueStyle = "default", valueName = " ", interactive = false, valueOptions = "width=280;height=220")
//    public Shape3DArray getShape(
//            @ParamInfo(style = "load-dialog", options = "invokeOnChange=true") File file) throws IOException {
//
//        TxT2Geometry txt = new TxT2Geometry();
//        Geometry geo = txt.loadTxt(file);
//
//        AppearanceGenerator a = new AppearanceGenerator();
//
//        Shape3D s = new Shape3D(geo, a.getColoredAppearance(Color.blue));
//        s.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
//        s.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
//        s.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
//        s.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
//
//        Shape3DArray result = new Shape3DArray();
//        result.add(s);
//
//        return result;
//    }
//
//
//
//    //ruft die dispose() in PickArrayTypeRepresentation auf
//    public void clearCapability() {
//
//        int id = mainCanvas.getInspector().getCanvasWindowID(this);
//
//        VisualObject vObj = (VisualObject) mainCanvas.getCanvasObjects().getById(id);
//        ObjectRepresentation oRep = vObj.getObjectRepresentation();
//        MethodRepresentation thisMethod = null;
//
//        for (MethodRepresentation mRep : oRep.getMethods()) {
//            if (mRep.getName().equals("getShapeArray()")) {
//                thisMethod = mRep;
//                break;
//            }
//        }
//
//        TypeRepresentationBase tRep = thisMethod.getReturnValue();
//
//        shape3DValue = (PickArrayTypeRepresentation) tRep;
//        shape3DValue.dispose();
//    }
    //////
//    private Vector<Geometry> getGeometryVector() {
//
//        Vector<Geometry> result = new Vector<Geometry>(); //new Vector<VTriangleArray>();
//
//        Node n1 = new Node(new Point3f(0f, 0f, 0f));
//        Node n2 = new Node(new Point3f(10f, 0f, 0f));
//        Node n3 = new Node(new Point3f(0f, 10f, 0f));
//        Node n4 = new Node(new Point3f(0f, 0f, 10f));
//
//        Triangle t1 = new Triangle(1, n1, n2, n3);
//        Triangle t2 = new Triangle(2, n1, n2, n4);
//        Triangle t3 = new Triangle(3, n1, n3, n4);
//        Triangle t4 = new Triangle(4, n2, n3, n4);
//
//        VTriangleArray a1 = new VTriangleArray();
//        VTriangleArray a2 = new VTriangleArray();
//        VTriangleArray a3 = new VTriangleArray();
//        VTriangleArray a4 = new VTriangleArray();
//
//        a1.addTriangle(t1);
//        a2.addTriangle(t2);
//        a3.addTriangle(t3);
//        a4.addTriangle(t4);
//
//        result.add(a1.getTriangleArray());
//        result.add(a2.getTriangleArray());
//        result.add(a3.getTriangleArray());
//        result.add(a4.getTriangleArray());
//
//        /*        es muß dieser aufbau gewählt werden da in der kurz form folgende  fehlermeldung auftritt
//        "void" type not allowed here (.addTriangle) - error message
//        result.add(new VTriangleArray().addTriangle(new Triangle(1, n1, n2, n3)));
//         */
//        return result;
//    }
    //////
//    public IndexedGeometryList load(@ParamInfo(style = "load-dialog") File f) throws IOException {
//
//        TxT2Geometry txt = new TxT2Geometry();
//        triArray = txt.loadAsVTriangleArray(f);
//
//        return triArray.toIndexedGeometryList();
//    }
//
////
//    private Geometry getGeometry() {
//        VTriangleArray result = new VTriangleArray();
//
//        Node n1 = new Node(new Point3f(0f,0f,0f));
//        Node n2 = new Node(new Point3f(10f,0f,0f));
//        Node n3 = new Node(new Point3f(0f,10f,0f));
//        Node n4 = new Node(new Point3f(0f,0f,10f));
//
//        result.addTriangle(new Triangle(1,n1,n2,n3));
//        result.addTriangle(new Triangle(2,n1,n2,n4));
//        result.addTriangle(new Triangle(3,n1,n3,n4));
//        result.addTriangle(new Triangle(4,n2,n3,n4));
//
//        return result.getTriangleArray();
//    }
}
