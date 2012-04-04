/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.skin;

import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * The InputWindow for the parameter group SkinModelOutput.
 * @author Night
 */
@ObjectInfo(name="Skin Output")
@ComponentInfo(name = "SkinModelOutputWindow", category = "BasicMath/skin")
public class SkinModelOutputWindow implements Serializable{
    private static final long serialVersionUID = 1L;

    private static String scriptFileOutput = UGVariables.SKIN2D + "/scripts/Output.scr";

    /**
     * Stores the UG needed command for loading the files in a script file
     * named Output.scr.
     *
     * @param xdr true if xdr files should be created from UG as output
     * @param vtk true if vth files should be created from UG as output
     * @return the collected values as parameter group SkinModelOutput
     * @throws IOException
     */
    public SkinModelOutput saveOutputSkript(
            @ParamInfo(name="xdr") Boolean xdr,
            @ParamInfo(name="vtk") Boolean vtk)
            throws IOException {

        FileWriter out = new FileWriter(new File(scriptFileOutput));
        BufferedWriter writer = new BufferedWriter(out);

        if (xdr) {
            writer.write("WRITE_XDR = 1;\n");
        } else {
            writer.write("WRITE_XDR = 0;\n");
        }

        if (vtk) {
            writer.write("WRITE_VTK = 1;\n");
        } else {
            writer.write("WRITE_VTK = 0;\n");
        }


        writer.close();

        return new SkinModelOutput(xdr, vtk);
    }


}
