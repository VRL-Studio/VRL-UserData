/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.path;

import eu.mihosoft.vrl.system.VSysUtil;

/**
 * Contains the static varible UGROOT
 * UGROOT = /Users/ XXX /UG/sc/appl2d/
 * XXX should be replaced by the name of user
 *
 * UGROOT is needed to save some script files on right place.
 *
 * @author night
 */
public class UGVariables {
//    public static String UGROOT = "UG/";
//    public static String UGROOT = VSysUtil.getCustomBinaryPath()+"UG/";
    public static String UGROOT = "/Users/night/UG/";
    public static String APPL2D = UGROOT +"sc/appl2d/";
    public static String SKIN2D = UGROOT +"sc/skindemo2d/";

    public static String LGM2GNU_EXEC = UGROOT +"ug/bin/lgm2gnu";

    public static String GNUPLOTROOT = VSysUtil.getCustomBinaryPath()+"gnuplot/";
    public static String GNUPLOT_EXEC = GNUPLOTROOT +"Gnuplot.app/Contents/Resources/bin/gnuplot";
}
