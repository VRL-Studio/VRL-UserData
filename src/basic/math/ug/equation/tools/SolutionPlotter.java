/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.tools;

import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.io.FileList;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.v3d.AppearanceGenerator;
import eu.mihosoft.vrl.v3d.Shape3DArray;
import eu.mihosoft.vrl.v3d.TxT2Geometry;
import eu.mihosoft.vrl.visual.Canvas;
import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;

/**<p>
 * SolutionPlotter shows after calculation the evolution of the solution.
 * </p>
 * SolutionPlotter loads the plot files which was generated by each timestep of
 * the calculation and shows as many time as wanted how the start geometry
 * was modified during the calculation.
 *
 * @author night
 */
@ObjectInfo(name = "SolutionPlotter")
@ComponentInfo(name = "SolutionPlotter", category = "BasicMath/Tools")
public class SolutionPlotter implements Serializable {

    private static final long serialVersionUID = 1;
    private transient VisualCanvas mainCanvas;

    /**
     * Sets the main canvas object.
     * @param mainCanvas the main canvas object
     */
    @MethodInfo(noGUI = true, callOptions = "assign-to-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    /**
     * Checks if another File exist and if it exist add it to the <code>FileList</code>
     * which is returned
     * @return a FileList
     */
    private FileList checkForNextFile() {

        FileList fileList = new FileList();
        int index = 0;

        boolean fileExists = true;

        while (fileExists) {
            fileExists = new File(generateFileName(index)).exists();

            if (fileExists) {
                fileList.add(new File(generateFileName(index)));
            }
            index++;
        }
        return fileList;
    }

    /**<p>
     * Generates the whole name ( this means where the plot file is stored at harddisc)
     * of the used plot file including index and ending ( .txt).
     * </p>
     * @param index the actuall highest index of existing plot files
     * @return String with the path to the search plot file
     */
    private String generateFileName(int index) {
        return UGVariables.APPL2D + "/plot" +
                index + ".txt";
    }

    /**
     * Generates from the exsiting plot file a <code>Shape3DArray</code>
     * 
     * @return the generated Shape3DArry
     */
    @MethodInfo(valueName = " ", valueOptions = "fps=30; hideConnector=true",
    interactive = true, valueStyle = "animation")
    public Shape3DArray getShape() {

        TxT2Geometry loader = new TxT2Geometry();
        Geometry g = null;
        Shape3DArray shapes = new Shape3DArray();
        AppearanceGenerator generator = new AppearanceGenerator();

        for (File f : checkForNextFile()) {
            try {
                g = loader.loadTxt(f);
//                System.out.println(f.toString());
                if (g != null) {
                    shapes.add(new Shape3D(g, generator.getColoredAppearance(Color.white, 0.f)));
                }
            } catch (Exception ex) { /**/ }
        }

        return shapes;
    }   
}