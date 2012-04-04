/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import basic.math.helpers.FileUtils;
import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.io.ProcessTemplate;
import eu.mihosoft.vrl.visual.DefaultViewer;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the center of the skin model representaion in the VRL
 * were all needed parameters / setting comes together.
 *
 * By pressing the invoke button the run skript is started and UG starts the
 * calculation.
 * @author Night
 */
@ObjectInfo(name = "SkinModel")
@ComponentInfo(name = "SkinModel", category = "BasicMath/skin")
public class SkinModel extends ProcessTemplate implements Serializable {

    private static final long serialVersionUID = 1;

    private SkinModelSpecificParameters skinModelSpecificParameters;
    private SkinModelGeometry skinModelGeometry;
    private SkinModelExecute skinModelExecute;
    private SkinModelRefinement skinModelRefinement;
    private SkinModelOutput skinModelOutput;

    /**
     * Default Constructor
     * Setting where done like:
     * - which script should be startet
     * - output size for MessageBox
     *
     */
    public SkinModel() {
        setPath(UGVariables.SKIN2D);
        ArrayList<String> command = new ArrayList<String>();

        command.add("./haut2d");
        setCommand(command);
        setProcessTitle("haut2d");
//        setNumberOfMessageLines(7);
        setViewer(new DefaultViewer());
    }

    /**
     * 
     * This methode starts the run script which starts the calculations 
     * of hautdemo2d.
     * 
     * @param skinModelSpecificParameters
     * @param skinModelGeometry
     * @param skinModelExecute
     * @param skinModelRefinement
     * @param skinModelOutput
     */
    public void runScript(
            SkinModelSpecificParameters skinModelSpecificParameters,
            SkinModelGeometry skinModelGeometry,
            SkinModelExecute skinModelExecute,
            SkinModelRefinement skinModelRefinement,
            SkinModelOutput skinModelOutput) {

        this.skinModelSpecificParameters = skinModelSpecificParameters;
        this.skinModelGeometry = skinModelGeometry;
        this.skinModelExecute = skinModelExecute;
        this.skinModelRefinement = skinModelRefinement;
        this.skinModelOutput = skinModelOutput;

         FileUtils.deleteFiles(UGVariables.SKIN2D +"vtk/", false);

        run();
    }


}
