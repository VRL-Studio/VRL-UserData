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
 * The InputWindow for the parameter group SkinModelRefinement.
 * If oppen it show the default values for the parameter group and uses them if
 * no other values were inserted.
 *
 * @author Night
 */
@ObjectInfo(name = "Skin Refinement")
public class SkinModelRefinementWindow implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String scriptFileRefinement = UGVariables.SKIN2D + "/scripts/Refinement.scr";

    /**
     * Stores the UG needed command for loading the files in a script file
     * named Refinement.scr.
     *
     * @param baseLevel
     * @param anisoLevel
     * @param bndLevel
     * @param isoLevel
     * @return the collected values as parameter group SkinModelRefinement
     * @throws IOException
     */
    public SkinModelRefinement saveRefinementSkript(
            @ParamInfo(name = "baseLevel", nullIsValid = true, style = "custom", options = "defaultValue= 0") Integer baseLevel,
            @ParamInfo(name = "anisoLevel", nullIsValid = true, style = "custom", options = "defaultValue= 3") Integer anisoLevel,
            @ParamInfo(name = "bndLevel", nullIsValid = true, style = "custom", options = "defaultValue= 0") Integer bndLevel,
            @ParamInfo(name = "isoLevel", nullIsValid = true, style = "custom", options = "defaultValue= 3") Integer isoLevel)
            throws IOException {

        File file = new File(scriptFileRefinement);

        if(file.exists()){
            file.delete();
        }

        FileWriter out = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(out);

        if (baseLevel != null) {
            writer.write("BASELEVEL      	= " + baseLevel + ";\n");
        } else {
            baseLevel = 0;

            writer.write("BASELEVEL      	= 0;\n");
            System.out.println("NO value for BASELEVEL! Therefore default value is used.");
            System.out.println("BASELEVEL = 0");
        }


        if (anisoLevel != null) {
            writer.write("ANISOLEVEL       	= " + anisoLevel + ";\n");
        } else {
            anisoLevel = 3;

            writer.write("ANISOLEVEL       	= 3;\n");
            System.out.println("NO value for ANISOLEVEL! Therefore default value is used.");
            System.out.println("ANISOLEVEL = 3");
        }

        if (bndLevel != null) {
            writer.write("BNDLEVEL       	= " + bndLevel + ";\n");
        } else {
            bndLevel = 0;

            writer.write("BNDLEVEL       	= 0;\n");
            System.out.println("NO value for BNDLEVEL! Therefore default value is used.");
            System.out.println("BNDLEVEL = 0");
        }

        if (isoLevel != null) {
            writer.write("ISOLEVEL       	= " + isoLevel + ";\n");
        } else {
            isoLevel = 3;

            writer.write("ISOLEVEL       	= 3;\n");
            System.out.println("NO value for ISOLEVEL! Therefore default value is used.");
            System.out.println("ISOLEVEL = 3");
        }


        writer.close();

        return new SkinModelRefinement(baseLevel, anisoLevel, bndLevel, isoLevel);
    }
}
