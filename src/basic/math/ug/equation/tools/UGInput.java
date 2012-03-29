/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.tools;

import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.io.ProcessTemplate;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageBoxViewer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the visualation of the used equation and give the possiblity to
 * influence the solution by setting lgm and ng file and the timesteps.
 *
 * @author Night
 */
@ObjectInfo(name = "UGInput")
@ComponentInfo(name = "UGInput", category = "BasicMath/Tools")
public class UGInput extends ProcessTemplate implements Serializable {

    private static final long serialVersionUID = 1;
    private static String scriptFileDGLParameters = UGVariables.APPL2D + "/DGLparameters.scr";
    private static String scriptFileGeometry = UGVariables.APPL2D + "/Geometry.scr";
    private static String scriptFileDefinitions = UGVariables.APPL2D + "/Definitions.scr";

    /**
     * the constructor removes remaining plot files (by calling a script file named run.sh)
     * from a previous calculation and set the working path.
     *
     * run.sh contains:
     *
     * #!/bin/bash
     *
     * rm -f plot*.txt;
     * ./sc2d -ui ltd.scr
     *
     */
    public UGInput() {
        setPath(UGVariables.APPL2D);
        ArrayList<String> command = new ArrayList<String>();
//        command.add("./sc2d");
//        command.add("-ui");
//        command.add("ltd.scr");
        command.add("./run.sh");
        setCommand(command);
        setProcessTitle("sc2d -ui ltd.scr");
        setNumberOfMessageLines(7);
        setViewer(new MessageBoxViewer());
    }

    /**
     * Contains the necessary parameters to manipulate the solution
     * and calls the subfunctions which create appendant script files
     *
     * @param f the visualation of the equation
     * @param timeSteps how many iteration should be done
     * @param lgmFile contains Information about the used start geometrie
     * @param ngFile contains Information about the used start geometrie
     * @throws IOException
     */
    @MethodInfo(valueStyle = "mathml", valueOptions = "hideConnector=true")
    public String saveScripts(
            final VisualScalarInterface scalarBeta,
            final VisualMatrixInterface matrixA,
            final VisualVectorInterface vectorC,
            //            @ParamInfo(name = "DGL: ", style = "static", options = "hideConnector=true") Formel f,
            @ParamInfo(name = "timeSteps: ", options = "hideConnector=true") final Integer timeSteps,
            @ParamInfo(name = "lgmFile:", style = "load-dialog", options = "hideConnector=true") final File lgmFile,
            @ParamInfo(name = "ngFile:", style = "load-dialog", options = "hideConnector=true") final File ngFile) throws IOException {

        Thread calculationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("Calculation start");
//        saveDGLParametersScript(f);
                    saveDGLParametersScript(scalarBeta, matrixA, vectorC);
                    saveGeometryScript(lgmFile, ngFile);
                    saveDefinitionsScript(timeSteps);
                    //start calculaion with ug by running the ./run.sh script
                    UGInput.this.run();
                    //
                    System.out.println("Calculation end");
                } catch (IOException ex) {
                    System.err.println(UGInput.class + ".saveScripts(): error in Thread.");
                    Logger.getLogger(UGInput.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        if (checkDimensions(scalarBeta, matrixA, vectorC)) {
            calculationThread.start();

            return generateEquation(scalarBeta, matrixA, vectorC);
        } else {
            throw new IllegalArgumentException(UGInput.class +
                    " checkDimensions() faild calculation thread not started."+
                    "The involved elements need to be a 2x2 or 3x3 matrix and"+
                    "depending on the matrix a vector with length 2 or 3.");
        }

//        return scalarBeta.getXmlCoding()+ matrixA.getXmlCoding()+ vectorC.getXmlCoding();
//        return "<mtext> - TEXT - </mtext>";
//        return resultThread.mathMLresult(); // WRONG :-(
    }

    /**
     * Checks the dimensions of the involved elements if they are compatible.
     * matrix should be 2x2 or 3x3 and vector compatible.
     *
     * @param scalarBeta used scalar
     * @param matrixA used matrix
     * @param vectorC used vector
     * @return true if dimension of involved elements are correct
     */
    private Boolean checkDimensions(VisualScalarInterface scalarBeta,
            VisualMatrixInterface matrixA, VisualVectorInterface vectorC) {
        //matrix.row == matrix.col && vector.length == matrix.row &&
        // ( matrix.row == 2 || matrix.row == 3)
        if ((matrixA.getDimensions()[0] == matrixA.getDimensions()[1]) &&
                (matrixA.getDimensions()[0] == vectorC.getDimensions()[0]) &&
                ((matrixA.getDimensions()[0] == 2) || (matrixA.getDimensions()[0] == 3))) {
            return true;
        }

        return false;
    }

    /**
     * Generates the mathML representation of to visualized equation.
     * This is the visualization of "Diffusions-Konvektion-Reaktion-Gleichung".
     * 
     * @param scalarBeta is the involved scalar
     * @param matrixA is the involved matrix
     * @param vectorC is the involved vector
     * @return the generated mathML code
     */
    @MethodInfo(hide = true, noGUI = true)
    private String generateEquation(
            VisualScalarInterface scalar,
            VisualMatrixInterface matrix,
            VisualVectorInterface vector) {

        String result = "<math><mfrac> <mi>d</mi> <mi>dt</mi> </mfrac>" +
                " <mo>(</mo></math> " +
                "<ci>(</ci>" + scalar.getXmlCoding() + "<ci>)</ci>" +
                "<math><mo>*</mo> <mi>u</mi> <mo>)</mo></math> " +
                "<math><mo>-</mo> <mi>div( </mi></math>" +
                "<ci>(</ci>" + matrix.getXmlCoding() + "<ci>)</ci>" +
                "<math><mo>*</mo><mi>&nabla;</mi><mi>u</mi> <mo>+</mo></math> " +
                "<ci>(</ci>" + vector.getXmlCoding() + "<ci>)</ci>" +
                "<math><mo>*</mo><mi>u</mi><mo>) = 0</mo></math>";

        //ohne klammern um die Elemente
        String result2 = "<math><mfrac> <mi>d</mi> <mi>dt</mi> </mfrac>" +
                " <mo>(</mo></math> " +
                scalar.getXmlCoding() +
                "<math><mo>*</mo> <mi>u</mi> <mo>)</mo></math> " +
                "<math><mo>-</mo> <mi>div( </mi></math>" +
                matrix.getXmlCoding() +
                "<math><mo>*</mo><mi>&nabla;</mi><mi>u</mi> <mo>+</mo></math> " +
                vector.getXmlCoding() +
                "<math><mo>*</mo><mi>u</mi><mo>) = 0</mo></math>";

        return result2;
    }

    /**
     * Writtes the used parameters (e.g. the entries of the matrix )
     * in a script file named DGLparameters.scr.
     *
     * @throws IOException
     */
    private void saveDGLParametersScript(VisualScalarInterface scalarBeta,
            VisualMatrixInterface matrixA,
            VisualVectorInterface vectorC) throws IOException {

        setPath(UGVariables.APPL2D);
        scriptFileDGLParameters = UGVariables.APPL2D + "/DGLparameters.scr";

        FileWriter out = new FileWriter(new File(scriptFileDGLParameters));
        BufferedWriter writer = new BufferedWriter(out);

        writer.write("Equation_Parameters");
        writer.newLine();
        writer.newLine();

        for (int i = 0; i < matrixA.getDimensions()[0]; i++) {//row

            for (int j = 0; j < matrixA.getDimensions()[1]; j++) {//col
                writer.write("$a" + i + j + " " + ((Double) matrixA.getEntry(i, j)) + " " );
            }
            writer.newLine();
        }

        writer.newLine();
        writer.newLine();

        for (int i = 0; i < vectorC.getDimensions()[0]; i++) {//row
            writer.write("$c" + i + " " + ((Double) vectorC.getEntry(i)) + " ");
        }

        writer.newLine();
        writer.newLine();

        writer.write("$b" + " " + ((Double) scalarBeta.getEntry()));

        writer.close();
    }

//    /**
//     * Writtes the used parameters (e.g. the entries of the matrix )
//     * in a script file named DGLparameters.scr.
//     *
//     * @param f the visualation of the equation
//     * @throws IOException
//     */
//    private void saveDGLParametersScript(Formel f) throws IOException {
//
//        setPath(UGVariables.APPL2D);
//        scriptFileDGLParameters = UGVariables.APPL2D + "/DGLparameters.scr";
//
//        FileWriter out = new FileWriter(new File(scriptFileDGLParameters));
//        BufferedWriter writer = new BufferedWriter(out);
//
//        writer.write("Equation_Parameters");
//        writer.newLine();
//        writer.newLine();
//
//        for (FormelEntry e : f) {
////            System.out.println("ENTRY: " + e.getShortName());
//            writer.newLine();
//            if (e.getData() != null) {
//                writer.write(e.getData());
////                System.out.println("ENTRY:" + e.getShortName() + " OK");
//            }
//            writer.newLine();
//        }
//        writer.close();
//    }
    /**
     * Calls the methode copyFileToFolderLGMDOMAINS() for lgm and ng file.
     * Stores the ug needed comand for loading the files in a script file 
     * named Geometry.scr.
     *
     * @param lgmFile contains Information about the used start geometrie
     * @param ngFile contains Information about the used start geometrie
     * @throws IOException
     */
    private void saveGeometryScript(File lgmFile, File ngFile) throws IOException {

        copyFileToFolderLGMDOMAINS(lgmFile, "lgm");
        copyFileToFolderLGMDOMAINS(ngFile, "ng");

        setPath(UGVariables.APPL2D);
        scriptFileGeometry = UGVariables.APPL2D + "/Geometry.scr";

        FileWriter out = new FileWriter(new File(scriptFileGeometry));
        BufferedWriter writer = new BufferedWriter(out);

        writer.write("new grid $b geometry.lgm $f P1_conform $h @HEAP;");
        writer.close();
    }

    /**
     * Copied the the given file into the necessary folder at harddisc
     * (UG/sc/apple2d/LGMDOMAINS/)
     * under the name "geomatry" with the extansion contained in parameter ext
     *
     * @param inputFile file that should be copied
     * @param ext contains a String which decides of which typ the parameter inputFile is
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void copyFileToFolderLGMDOMAINS(File inputFile, String ext) throws FileNotFoundException, IOException {

        File outputFile = new File(UGVariables.APPL2D + "/LGMDOMAINS/geometry." + ext);

        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;

        while ((c = in.read()) != -1) {
            out.write(c);
        }

        in.close();
        out.close();
    }

    /**
     * Stores the default definitions and the number of timesteps
     * into a script file named Definitions.scr.
     *
     * @param timeSteps how many iteration should be done
     * @throws IOException
     */
    private void saveDefinitionsScript(Integer timeSteps) throws IOException {

        setPath(UGVariables.APPL2D);

        scriptFileDefinitions = UGVariables.APPL2D + "/Definitions.scr";

        FileWriter out = new FileWriter(new File(scriptFileDefinitions));
        BufferedWriter writer = new BufferedWriter(out);

        writer.write("DOLOG      = 0;");
        writer.write("DOGRAPHICS = 1;");
        writer.write("MAXLEVEL   = 4;");
        writer.write("TIMESTEPS  = " + timeSteps + ";");
        writer.write("DELTAT     = 0.01;");
        writer.write("ABSLIMIT   = 1.0E-10;");
        writer.write("DOFILM     = 0;");
        writer.write("FILMINC    = 1;");
        writer.write("film       = \"film\";");


        writer.close();
    }

    /**
     * Methode that is needed, that this class knows to which canvas
     * shes been added.
     *
     * @param mainCanvas the canvas to which this class is added
     */
    @MethodInfo(noGUI = true, callOptions = "assign-to-canvas")
    public void setMainCanvas(VisualCanvas mainCanvas) {

        super.setMainCanvas(mainCanvas);
    }
}
