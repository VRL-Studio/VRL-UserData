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
 * The InputWindow for the parameter group SkinModelSpecificParameters.
 * If oppen it show the default values for the parameter group and uses them if
 * no other values were inserted.
 *
 * @author Night
 */
@ObjectInfo(name = "Skin Parameters")
@ComponentInfo(name = "SkinModelSpecificParametersWindow", category = "BasicMath/skin")
public class SkinModelSpecificParametersWindow implements Serializable{
    private static final long serialVersionUID = 1L;

    private static String scriptFileSpecificParameters = UGVariables.SKIN2D + "/scripts/SpecificParameters.scr";

    /**
     * Stores the UG needed command for loading the files in a script file
     * named SpecificParameters.scr.
     *
     *
     * @param dlib
     * @param dcor0
     * @param dcor1
     * @param depi
     * @param ddon
     * @param kcor0
     * @param kcor1
     * @param kepi
     * @param kdon
     * @param idon
     * @return the collected values as parameter group saveRefinement
     * @throws IOException
     */
    public SkinModelSpecificParameters saveSpecificParametersScript(
            @ParamInfo(nullIsValid = true, name = "dlib", style = "custom", options = "defaultValue= 3.0555D") Double dlib,
            @ParamInfo(nullIsValid = true, name = "dcor0", style = "custom", options = "defaultValue= 0.014166D") Double dcor0,
            @ParamInfo(nullIsValid = true, name = "dcor1", style = "custom", options = "defaultValue= 0.014166D") Double dcor1,
            @ParamInfo(nullIsValid = true, name = "depi", style = "custom", options = "defaultValue= 67.0D") Double depi,
            @ParamInfo(nullIsValid = true, name = "ddon", style = "custom", options = "defaultValue= 1000.0D") Double ddon,
            @ParamInfo(nullIsValid = true, name = "kcor0", style = "custom", options = "defaultValue= 0.21D") Double kcor0,
            @ParamInfo(nullIsValid = true, name = "kcor1", style = "custom", options = "defaultValue= 0.21D") Double kcor1,
            @ParamInfo(nullIsValid = true, name = "kepi", style = "custom", options = "defaultValue= 0.1D") Double kepi,
            @ParamInfo(nullIsValid = true, name = "kdon", style = "custom", options = "defaultValue= 0.04921259D") Double kdon,
            @ParamInfo(nullIsValid = true, name = "idon", style = "custom", options = "defaultValue= 20.32D") Double idon)
            throws IOException {

        FileWriter out = new FileWriter(new File(scriptFileSpecificParameters));
        BufferedWriter writer = new BufferedWriter(out);

        if (dlib != null) {
            writer.write(":DLIP = \"" + dlib + "\";\n");
        } else {
            dlib=3.0555;

            writer.write(":DLIP = \"3.0555\";\n");
            System.out.println("NO value for DLIB! Therefore default value is used.");
            System.out.println("DLIP = 3.0555");
        }



        if (dcor0 != null) {
            writer.write(":DCOR0 = \"" + dcor0 + "\";\n");
        } else {
            dcor0=0.014166;

            writer.write(":DCOR0 = \"0.014166\";\n");
            System.out.println("NO value for DCOR0! Therefore default value is used.");
            System.out.println("DCOR0 = 0.014166");
        }

        if (dcor1 != null) {
            writer.write(":DCOR1 = \"" + dcor1 + "\";\n");
        } else {
            dcor1=0.014166;

            writer.write(":DCOR1 = \"0.014166\";\n");
            System.out.println("NO value for DCOR1! Therefore default value is used.");
            System.out.println("DCOR1 = 0.014166");
        }

        if (depi != null) {
            writer.write(":DEPI = \"" + depi + "\";\n");
        } else {
            depi=67D;

            writer.write(":DEPI = \"67\";\n");
            System.out.println("NO value for DEPI! Therefore default value is used.");
            System.out.println("DEPI = 67");
        }

        if (ddon != null) {
            writer.write(":DDON = \"" + ddon + "\";\n\n");
        } else {
            ddon=1000D;

            writer.write(":DDON = \"1000.0\";\n\n");
            System.out.println("NO value for DDON! Therefore default value is used.");
            System.out.println("DDON = 1000.0");
        }



        if (kcor0 != null) {
            writer.write(":KCOR0 = \"" + kcor0 + "\";\n");
        } else {
            kcor0=0.21;

            writer.write(":KCOR0 = \"0.21\";\n");
            System.out.println("NO value for KCOR0! Therefore default value is used.");
            System.out.println("KCOR0 = 0.21");
        }

        if (kcor1 != null) {
            writer.write(":KCOR1 = \"" + kcor1 + "\";\n");
        } else {
            kcor1=0.21;

            writer.write(":KCOR1 = \"0.21\";");
            System.out.println("NO value for KCOR1! Therefore default value is used.");
            System.out.println("KCOR1 = 0.21");
        }

        if (kepi != null) {
            writer.write(":KEPI = \"" + kepi + "\";\n");
        } else {
            kepi=0.10;

            writer.write(":KEPI = \"0.10\";\n");
            System.out.println("NO value for KEPI! Therefore default value is used.");
            System.out.println("KEPI = 0.10");
        }



        writer.write("\n # corresponds to concentration 1!\n");

        if (kdon != null) {
            writer.write(":KDON = \"" + kdon + "\";\n");
        } else {
            kdon=0.04921259;

            writer.write(":KDON = \"0.04921259\" \n");
            System.out.println("NO value for KDON! Therefore default value is used.");
            System.out.println("KDON = 0.04921259");
        }

        if (idon != null) {
            writer.write(":IDON = \"" + idon + "\";\n");
        } else {
            idon=20.32;

            writer.write(":IDON = \"20.32\";\n");
            System.out.println("NO value for IDON! Therefore default value is used.");
            System.out.println("IDON = 20.32");
        }
//        writer.write(":IDON = \"20.32\";"); // HIER 2 Mal IDON ???? eins zuviel ?????? !!!!!!!


        writer.close();

        return new SkinModelSpecificParameters(dlib, dcor0, dcor1, depi, ddon, kcor0, kcor1, kepi, kdon, idon);
    }
}
