/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.menu;

import basic.math.operations.procedure.*;

import basic.math.operations.conception.AddOperation;
import basic.math.helpers.ElementContainer;
import basic.math.operations.*;
import basic.math.elements.generators.*;
import basic.math.types.ClassSelectionType;
import basic.math.types.MathMLType;
import basic.math.types.VisualTensorType;
import basic.math.types.ParametersOperationSelectionType;
import basic.math.types.VisualElementArrayType;
import basic.math.types.VisualElementType;
import basic.math.types.VisualMatrixType;
import basic.math.types.VisualScalarType;
import basic.math.types.VisualVectorArrayType;
import basic.math.types.VisualVectorType;
import basic.math.types.intArrayType;
import basic.math.ug.equation.tools.LivePlotter;
import basic.math.ug.equation.tools.PickPlotter;
import basic.math.ug.equation.tools.SolutionPlotter;
import basic.math.ug.equation.tools.UGInput;
import basic.math.ug.skin.SkinGeometryPlotter;
import basic.math.ug.skin.SkinModel;
import basic.math.ug.skin.SkinModelExecuteWindow;
import basic.math.ug.skin.SkinModelGeometryWindow;
import basic.math.ug.skin.SkinModelGeometryWindowSimpler;
import basic.math.ug.skin.SkinModelOutputWindow;
import basic.math.ug.skin.SkinModelRefinementWindow;
import basic.math.ug.skin.SkinModelSpecificParametersWindow;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.FileListType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**<p>
 * Generates in popup menu of the VRL-Studio some new entries, which are
 * separeted from the before existing ones by a separation line.
 * Additionally the necessary tpyerepresantations were loaded into VRL-Studio.
 * </p><p>
 * Each new entry allows through a click at it to load a instance of the
 * labeled classes.
 * </p>
 * @author night
 */
public class BasicMathMenu implements Serializable {

    private static final long serialVersionUID = 1;
    private static String TENSOR = "Tensor Generator";
    private static String MATRIX = "Matrix Generator";
    private static String VECTOR = "Vector Generator";
    private static String SCALAR = "Scalar Generator";
//    private static String TWOPARAMETEROPERATION = "TwoParameterOperation";
    private static String ELEMENTCONTAINER = "ElementContainer";
    private static String BILINEARFORM = "Bilinearform";
    private static String CROSSPRODUCT = "Crossproduct";
    private static String NORM = "Norm";
    private static String ADDOPERATION = "AddOperation";
    private static String MATVECMULT = "MatrixVectorMultiplication";
    private static String GEOMETRYTRANSFORMER = "GeometryTransformer";
    //
    private static String TWOBODYPROBLEM = "2BodyProblem";
    private static String EULEREXPLICIT = "EulerExpicit";
    private static String PROCEDUREPLOTTER = "ProcedurePlotter";
    private static String HEUN = "Heun";
    //
    private static String SKINMODEL = "SkinModel";
    private static String SKINSPECIPARAM = "SkinModelSpecificParameters";
    private static String SKINEXECUTE = "SkinModelExecute";
    private static String SKINGEOMETRY = "SkinModelGeometry";
    private static String SKINOUTPUT = "SkinModelOutput";
    private static String SKINREFINEMENT = "SkinModelRefinement";
    private static String SKINGEOMETRYSIMPLER = "SkinModelGeometrySimpler";
    private static String SKINPLOTTER = "SkinGeometryPlotter";

    /**
     * Generates the enries for the now available classes in the popup menu and
     * adds the necessary typreprasentations to the maincanvas.
     *
     * @param mainCanvas the canvas where the instances of the classes should be added.
     */
    public void addMenuEntry(final VisualCanvas mainCanvas) {

        //subcategories for better overview / navigation
        JMenu basicMathMenu = new JMenu("add BasicMath Component");
        JMenu equationMenu = new JMenu("add Equation Component");
        JMenu skinMenu = new JMenu("add Skin Component");

        //basicMath
        JMenuItem basicMathitem1 = new JMenuItem(TENSOR);
        JMenuItem basicMathitem2 = new JMenuItem(MATRIX);
        JMenuItem basicMathitem3 = new JMenuItem(VECTOR);
//        JMenuItem basicMathitem4 = new JMenuItem(TWOPARAMETEROPERATION);
        JMenuItem basicMathitem5 = new JMenuItem(ELEMENTCONTAINER);
        JMenuItem basicMathitem6 = new JMenuItem(BILINEARFORM);
        JMenuItem basicMathitem7 = new JMenuItem(SCALAR);
        JMenuItem basicMathitem8 = new JMenuItem(CROSSPRODUCT);
        JMenuItem basicMathitem9 = new JMenuItem(NORM);
        JMenuItem basicMathitem10 = new JMenuItem(ADDOPERATION);
        JMenuItem basicMathitem11 = new JMenuItem(MATVECMULT);
        JMenuItem basicMathitem12 = new JMenuItem(GEOMETRYTRANSFORMER);
        //
        JMenuItem basicMathitem13 = new JMenuItem(TWOBODYPROBLEM);
        JMenuItem basicMathitem14 = new JMenuItem(EULEREXPLICIT);
        JMenuItem basicMathitem15 = new JMenuItem(PROCEDUREPLOTTER);
        JMenuItem basicMathitem16 = new JMenuItem(HEUN);

        //equation
        JMenuItem equationItem1 = new JMenuItem("PickPlotter");
        JMenuItem equationItem2 = new JMenuItem("UGInput");
        JMenuItem equationItem3 = new JMenuItem("SolutionPlotter");
        JMenuItem equationItem4 = new JMenuItem("LivePlotter");
//        JMenuItem item5 = new JMenuItem("add SolutionMovPlotter");

        //skinModel
        JMenuItem skinItem1 = new JMenuItem(SKINMODEL);
        JMenuItem skinItem2 = new JMenuItem(SKINSPECIPARAM);
        JMenuItem skinItem3 = new JMenuItem(SKINEXECUTE);
        JMenuItem skinItem4 = new JMenuItem(SKINGEOMETRY);
        JMenuItem skinItem5 = new JMenuItem(SKINOUTPUT);
        JMenuItem skinItem6 = new JMenuItem(SKINREFINEMENT);
        JMenuItem skinItem7 = new JMenuItem(SKINGEOMETRYSIMPLER);
        JMenuItem skinItem8 = new JMenuItem(SKINPLOTTER);


        //
        // BASICMATH ITEMS
        //

        basicMathitem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(TENSOR)) {

                    mainCanvas.addObject(new TensorGenerator());
                }
            }
        });

        basicMathitem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(MATRIX)) {

                    mainCanvas.addObject(new MatrixGenerator());
                }
            }
        });

        basicMathitem3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(VECTOR)) {

                    mainCanvas.addObject(new VectorGenerator());
                }
            }
        });

//        basicMathitem4.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals(TWOPARAMETEROPERATION)) {
//
//                    mainCanvas.addObject(new TwoParametersOperation());
//                }
//            }
//        });

        basicMathitem5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(ELEMENTCONTAINER)) {

                    mainCanvas.addObject(new ElementContainer());
                }
            }
        });

        basicMathitem6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(BILINEARFORM)) {

                    mainCanvas.addObject(new BilinearFormen());
                }
            }
        });

        basicMathitem7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SCALAR)) {

                    mainCanvas.addObject(new ScalarGenerator());
                }
            }
        });

        basicMathitem8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(CROSSPRODUCT)) {

                    mainCanvas.addObject(new CrossProduct());
                }
            }
        });

        basicMathitem9.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(NORM)) {

                    mainCanvas.addObject(new Norm());
                }
            }
        });

        basicMathitem10.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(ADDOPERATION)) {

                    mainCanvas.addObject(new AddOperation());
                }
            }
        });

        basicMathitem11.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(MATVECMULT)) {

                    mainCanvas.addObject(new MatrixVectorMultiplication());
                }
            }
        });

        basicMathitem12.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(GEOMETRYTRANSFORMER)) {

                    mainCanvas.addObject(new GeometryTransformation());
                }
            }
        });

        //

        basicMathitem13.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(TWOBODYPROBLEM)) {

                    mainCanvas.addObject(new TwoBodyProblem());
                }
            }
        });

        basicMathitem14.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(EULEREXPLICIT)) {

                    mainCanvas.addObject(new EulerExplicit());
                }
            }
        });

        basicMathitem15.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(PROCEDUREPLOTTER)) {

                    mainCanvas.addObject(new Plotter());
                }
            }
        });

        basicMathitem16.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(HEUN)) {

                    mainCanvas.addObject(new Heun());
                }
            }
        });

        //
        // EQUATION ITEMS
        //

        equationItem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("PickPlotter")) {

                    mainCanvas.addObject(new PickPlotter());
                }
            }
        });

        equationItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("UGInput")) {

                    mainCanvas.addObject(new UGInput());
                }
            }
        });

        equationItem3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("SolutionPlotter")) {

                    mainCanvas.addObject(new SolutionPlotter());
                }
            }
        });

        equationItem4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("LivePlotter")) {

                    mainCanvas.addObject(new LivePlotter());
                }
            }
        });

//        item5.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals("add SolutionMovPlotter")) {
//
//                    mainCanvas.addObject(new SolutionMovPlotter());
//                }
//            }
//        });


        //
        // SKIN ITEMS
        //

        skinItem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINMODEL)) {

                    mainCanvas.addObject(new SkinModel());
                }
            }
        });

        skinItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINSPECIPARAM)) {

                    mainCanvas.addObject(new SkinModelSpecificParametersWindow());
                }
            }
        });

        skinItem3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINEXECUTE)) {

                    mainCanvas.addObject(new SkinModelExecuteWindow());
                }
            }
        });

        skinItem4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINGEOMETRY)) {

                    mainCanvas.addObject(new SkinModelGeometryWindow());
                }
            }
        });

        skinItem5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINOUTPUT)) {

                    mainCanvas.addObject(new SkinModelOutputWindow());
                }
            }
        });

        skinItem6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINREFINEMENT)) {

                    mainCanvas.addObject(new SkinModelRefinementWindow());
                }
            }
        });

        skinItem7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINGEOMETRYSIMPLER)) {

                    mainCanvas.addObject(new SkinModelGeometryWindowSimpler());
                }
            }
        });

        skinItem8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(SKINPLOTTER)) {

                    mainCanvas.addObject(new SkinGeometryPlotter());
                }
            }
        });


        //seperate from default menus and item
        mainCanvas.getPopupMenu().add(new JSeparator());

        //create basicMath specific submenus
        mainCanvas.getPopupMenu().add(basicMathMenu);
        mainCanvas.getPopupMenu().add(equationMenu);
        mainCanvas.getPopupMenu().add(skinMenu);

        //in this order they will be shown in the basic math menu
        basicMathMenu.add(basicMathitem1);//Tensor Generator
        basicMathMenu.add(basicMathitem2);//Matrix Generator
        basicMathMenu.add(basicMathitem3);//Vector Generator
        basicMathMenu.add(basicMathitem7);//Scalar Generator
//        basicMathMenu.add(basicMathitem4);//2ParameterOperation
        basicMathMenu.add(basicMathitem6);//Bilinarform
        basicMathMenu.add(basicMathitem9);//Norm
        basicMathMenu.add(basicMathitem8);//CrossProduct
        basicMathMenu.add(basicMathitem5);//ElementContainer
        basicMathMenu.add(basicMathitem10);//AddElemetnsDoubleOperation
        basicMathMenu.add(basicMathitem11);//Matrix * Vector
        basicMathMenu.add(basicMathitem12);//GEO transform
        //
        basicMathMenu.add(basicMathitem13);//2BodyProblem
        basicMathMenu.add(basicMathitem14);//EulerExplicit
        basicMathMenu.add(basicMathitem15);//ProcedurePlotter
        basicMathMenu.add(basicMathitem16);//Heun

        //add to equation menu
        equationMenu.add(equationItem1);
        equationMenu.add(equationItem2);
        equationMenu.add(equationItem3);
        equationMenu.add(equationItem4);
//        mainCanvas.getPopupMenu().add(item5);

        //add to skin menu
        skinMenu.add(skinItem1);
        skinMenu.add(skinItem2);
        skinMenu.add(skinItem3);
        skinMenu.add(skinItem4);
        skinMenu.add(skinItem7);
        skinMenu.add(skinItem5);
        skinMenu.add(skinItem6);
        skinMenu.add(skinItem8);


        /// TYPES WERE ADDED BELOW

        mainCanvas.getTypeFactory().addType(new FileListType());

        // basicMath
        mainCanvas.getTypeFactory().addType(new VisualTensorType());
        mainCanvas.getTypeFactory().addType(new VisualMatrixType());
        mainCanvas.getTypeFactory().addType(new VisualVectorType());
        mainCanvas.getTypeFactory().addType(new VisualScalarType());

        mainCanvas.getTypeFactory().addType(new ParametersOperationSelectionType());
        mainCanvas.getTypeFactory().addType(new ClassSelectionType());

        //
        mainCanvas.getTypeFactory().addType(new MathMLType());
//          mainCanvas.getTypeFactory().addType(new eu.mihosoft.vrlext.types.MathMLType());
        mainCanvas.getTypeFactory().addType(new VisualElementType());
        mainCanvas.getTypeFactory().addType(new VisualElementArrayType());
        mainCanvas.getTypeFactory().addType(new VisualVectorArrayType());
        mainCanvas.getTypeFactory().addType(new intArrayType());

        //procedure
        mainCanvas.getTypeFactory().addType(new FunctionType());
        mainCanvas.getTypeFactory().addType(new TrajectoryType());
        mainCanvas.getTypeFactory().addType(new TrajectoryArrayType());

        //
        //
        //

        // equation
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.StaticFormelInputType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.FormelInputType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.FormelOutputType());
        mainCanvas.getTypeFactory().addType(new FileListType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.PickArrayTypeRepresentation());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.PickTypeRepresentation());//seems not to be used

        // SkinModell
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.GeomDefSelectionType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.CustomDoubleType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.CustomLongType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.CustomIntegerType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.SkinModelSpecificParametersType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.SkinModelExecuteType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.SkinModelGeometryType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.SkinModelOutputType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.SkinModelRefinementType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.LgmCeckingForNgFileType());
        mainCanvas.getTypeFactory().addType(new basic.math.ug.types.LgmFileType());


//        // Bsp
//        mainCanvas.getTypeFactory().addType(new basic.math.bsp.Datentyp());
//
//        mainCanvas.getPopupMenu().add(new JSeparator());
//
//        JMenuItem bspItem1 = new JMenuItem("Daten");
//        bspItem1.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals("Daten")) {
//
//                    mainCanvas.addObject(new basic.math.bsp.Daten());
//                }
//            }
//        });
//
//        JMenuItem bspItem2 = new JMenuItem("DatenMain");
//        bspItem2.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals("DatenMain")) {
//
//                    mainCanvas.addObject(new basic.math.bsp.DatenMain());
//                }
//            }
//        });
//
//        mainCanvas.getPopupMenu().add(bspItem1);
//        mainCanvas.getPopupMenu().add(bspItem2);
//        // Bsp ende
    }
}

/*

@ComponentInfo(name="Update Session Initializer")
class UpdateSessionInitializer implements Serializable {
private static final long serialVersionUID=1;

private transient VisualCanvas mainCanvas;

@MethodInfo(noGUI = true, callOptions = "assign-to-canvas")
public void setMainCanvas(VisualCanvas mainCanvas){
this.mainCanvas = mainCanvas;
mainCanvas.setSessionInitializer(new basic.math.menu.BasicMathAddClasses())
mainCanvas.getMessageBox().addMessage("Session Initializer updated:", ">> session initializer updated!", MessageType.INFO)
def timer = new  java.util.Timer()
timer.schedule(new java.util.TimerTask() {
void run() {
mainCanvas.getCanvasWindow(UpdateSessionInitializer.this).close()
}
}, 3000)
}
}

 */
