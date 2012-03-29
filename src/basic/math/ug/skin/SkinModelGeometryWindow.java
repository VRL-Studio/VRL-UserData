/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import basic.math.helpers.ErrorMessageWriter;
//import basic.math.ausgemustert.RepresentationGetter;
import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The InputWindow for the parameter group SkinModelGeometry.
 * If oppen it show the default values for the parameter group and uses them if
 * no other values were inserted.
 *
 * @author Night
 */
@ObjectInfo(name = "Geometry Parameters")
public class SkinModelGeometryWindow implements Serializable {

    private static final long serialVersionUID = 1L;
    protected static String scriptFileGeometry = UGVariables.SKIN2D + "/scripts/Geometry.scr";
    protected transient VisualCanvas mainCanvas;
    protected transient DefaultMethodRepresentation mRep;

    
    @MethodInfo(noGUI = true, callOptions = "assign-to-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    /**
     * Calls the methode copyFileToGeometryFolder() for .lgm file.
     * Stores the UG needed command for loading the files in a script file
     * named Geometry.scr.
     *
     * @param lgmFile contains Information about the used start geometry
     * @param geomDefSel1
     * @param geomDefSel2
     * @param finiteconc
     * @param finitecor
     * @return the collected values as parameter group SkinModelGeometry
     * @throws IOException
     */
    public SkinModelGeometry saveGeometryScript(
            MethodRequest methodRequest,
            @ParamInfo(name = "lgmFile", style = "lgm-ng") File lgmFile,
            @ParamInfo(name = "GeomDefSelection1") GeomDefSelection geomDefSel1,
            @ParamInfo(name = "GeomDefSelection2") GeomDefSelection geomDefSel2,
            @ParamInfo(name = "finiteconcFile", style = "load-dialog") File finiteconc,
            @ParamInfo(name = "finitecorFile", style = "load-dialog") File finitecor)
            throws IOException {

        mRep = methodRequest.getMethod();
//        mRep = getMethodRepresentation();

        boolean lgmExists = lgmFile.exists();
        boolean concExists = finiteconc.exists();
        boolean corExists = finitecor.exists();

        if ((!lgmExists) || (!concExists) || (!corExists)) {

            ArrayList<Connector> cList = new ArrayList<Connector>();

            if (!lgmExists) {
                cList.add(mRep.getParameter(0).getConnector());

            }
            if (!concExists) {
                cList.add(mRep.getParameter(3).getConnector());

            }
            if (!corExists) {
                cList.add(mRep.getParameter(4).getConnector());
            }

            ErrorMessageWriter.writeErrorMessage(mainCanvas, "File doesn´t exist",
                    "The given path didn´t lead to an existing File !", cList);

            return null;
        }

        FileWriter out = new FileWriter(new File(scriptFileGeometry));
        BufferedWriter writer = new BufferedWriter(out);

        writer.write("set LGMFILE " + lgmFile.getAbsolutePath() + ";\n");
        writer.write("set GEOMDEF " + geomDefSel1.getSelectedObject() + ";\n");
        writer.write("set GEOMDEF2 " + geomDefSel2.getSelectedObject() + ";\n");

        writer.write("\n");
        writer.write("ADD_INTEGRATOR_STRING = \"$struct SRESULT $file " + finiteconc.getAbsolutePath() + "\"; \n");
        writer.write("ADD_INTEGRATOR2_STRING = \"$struct SRESULT2 $file " + finitecor.getAbsolutePath() + "\"; \n");

        writer.close();

        return new SkinModelGeometry(lgmFile, geomDefSel1, geomDefSel2, finiteconc, finitecor);
    }

////    /**
////     * Created for not changing the calls in saveGeometryScript().
////     * If a child want to get its representation of saveGeometryScript() with
////     * a diffrent number of parameters than override this method.
////     *
////     * @return the method representation for saveGeometryScript(
////     *                      File, GeomDefSelection, GeomDefSelection, File, File).
////     */
////    @MethodInfo(noGUI=true)
////    protected MethodRepresentation getMethodRepresentation(){
////
////        return mainCanvas.getMethodRepresentation(this, visualID, "saveGeometryScript",
////                new Class[]{File.class, GeomDefSelection.class, GeomDefSelection.class,
////                        File.class, File.class});
////    }
}
