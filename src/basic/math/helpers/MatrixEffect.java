/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;

import basic.math.elements.interfaces.VisualMatrixInterface;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serializable;


/**
 *
 * @author night
 */
@ComponentInfo(name="MatrixEffect")
class MatrixEffect implements Serializable {
    private static final long serialVersionUID=1;

    public BufferedImage applyEffect(BufferedImage image, VisualMatrixInterface matrix) {

        BufferedImage out =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = out.createGraphics();
        g2.drawImage(image,0,0,null);

        float[] BLUR = (float[]) matrix.getElement().getDataArray();

        int[] dims = matrix.getDimensions();

        ConvolveOp matrixOp = null;

        if(dims[0]==dims[1]){
            matrixOp = new ConvolveOp(new Kernel(dims[0], dims[1], BLUR));
        }



        return matrixOp.filter(out, null);
    }
}
