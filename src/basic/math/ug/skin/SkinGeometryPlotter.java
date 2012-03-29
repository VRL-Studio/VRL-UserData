/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.io.ProcessTemplate;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.DefaultViewer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * These plotter is used to extract the geometry of a .lgm file which is used
 * in the skin model and show the geometry as a png picture.
 *
 * It is disgned to work from a dmg file where gnuplot is available from / in it.
 *
 * @author night
 */
public class SkinGeometryPlotter extends ProcessTemplate {

    public SkinGeometryPlotter() {

        setPath(UGVariables.GNUPLOTROOT);
        ArrayList<String> command = new ArrayList<String>();
//        command.add("chmod a+x run.sh");
        command.add("./run.sh");
        setCommand(command);
        setProcessTitle("Gnuplot");
//        setNumberOfMessageLines(7);
        setViewer(new DefaultViewer());
        
    }

    private void makeScript(File geometry, int xRes, int yRes) throws IOException {

        String run_sh = UGVariables.GNUPLOTROOT + "/run.sh";

        FileWriter out = new FileWriter(new File(run_sh));
        BufferedWriter writer = new BufferedWriter(out);

        //writer.write(UGVariables.LGM2GNU_EXEC + " " + geometry.getAbsolutePath());
        writer.write("../UG/ug/bin/lgm2gnu " + geometry.getAbsolutePath());
        writer.newLine();
//        writer.write(UGVariables.GNUPLOT_EXEC + " plot.gp");
        writer.write("./Gnuplot.app/Contents/Resources/bin/gnuplot plot.gp");
        writer.close();

        String plot_gp = UGVariables.GNUPLOTROOT + "plot.gp";

        FileWriter out2 = new FileWriter(new File(plot_gp));
        BufferedWriter writer2 = new BufferedWriter(out2);

        writer2.write("set terminal png size " + xRes + ", " + yRes);
        writer2.newLine();
        writer2.write("set output \"out.png\"");
        writer2.newLine();
        writer2.write("plot \"gnu/full\" w l");
        writer2.close();
    }

    @MethodInfo(valueOptions="fixedAspectRatio=true")
    public BufferedImage view(
            @ParamInfo(name = "lgmFile", style = "lgm") File geometry)
            throws IOException {

        makeScript(geometry, 1024, 1024);

        run();

        BufferedImage image = ImageIO.read(new File(UGVariables.GNUPLOTROOT + "out.png"));

        return image;
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
