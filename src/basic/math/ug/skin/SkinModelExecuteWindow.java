/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * The InputWindow for the parameter group SkinModelExecute.
 * If oppen it show the default values for the parameter group and uses them if
 * no other values were inserted.
 *
 * @author Night
 */
@ObjectInfo(name = "Skin Execute")
public class SkinModelExecuteWindow implements Serializable{
    private static final long serialVersionUID = 1L;

    private static String scriptFileExecute = UGVariables.SKIN2D + "/scripts/Execute.scr";

    /**
     * Stores the UG needed command for loading the files in a script file
     * named Execute.scr.
     *
     *
     * @param dt
     * @param kappa
     * @param endTime
     * @param maxSteps
     * @return the collected values as parameter group SkinModelExecute
     * @throws IOException
     */
    public SkinModelExecute saveExecuteSkript(
            @ParamInfo(name = "dt", nullIsValid = true, style = "custom", options = "defaultValue= 0.1D") Double dt,
            @ParamInfo(name = "kappa", nullIsValid = true, style = "custom", options = "defaultValue= 1.03D") Double kappa,
            @ParamInfo(name = "endTime", nullIsValid = true, style = "custom", options = "defaultValue= 360000L") Long endTime,
            @ParamInfo(name = "maxSteps", nullIsValid = true, style = "custom", options = "defaultValue= 30000L") Long maxSteps)
            throws IOException {

        FileWriter out = new FileWriter(new File(scriptFileExecute));
        BufferedWriter writer = new BufferedWriter(out);

        if (dt != null) {
            writer.write("DT = " + dt + ";\n");
        } else {
            dt=0.1;

            writer.write("DT = 0.1;\n");
            System.out.println("NO value for DT! Therefore default value is used.");
            System.out.println("DT = 0.1");
        }

        if (kappa != null) {
            writer.write("KAPPA = " + kappa + ";\n");
        } else {
            kappa=1.03;

            writer.write("KAPPA = 1.03;\n");
            System.out.println("NO value for KAPPA! Therefore default value is used.");
            System.out.println("KAPPA = 1.03");
        }


        if (endTime != null) {
            writer.write("ENDTIME = " + endTime + ";\n");
        } else {
            endTime=360000L;

            writer.write("ENDTIME = 360000;\n");
            System.out.println("NO value for ENDTIME! Therefore default value is used.");
            System.out.println("ENDTIME = 360000");
        }


        writer.write("\n");

        if (maxSteps != null) {
            writer.write("MAXSTEPS = " + maxSteps + ";\n");
        } else {
            maxSteps=30000L;

            writer.write("MAXSTEPS = 30000;\n");
            System.out.println("NO value for MAXSTEPS! Therefore default value is used.");
            System.out.println("MAXSTEPS = 30000");
        }

        writer.close();

        return new SkinModelExecute(dt, kappa, endTime, maxSteps);
    }
}
