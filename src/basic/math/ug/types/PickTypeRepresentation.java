/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;


import basic.math.ug.equation.buttons.CustomCanvas;
import basic.math.ug.equation.helpers.PickUniverseCreator;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.types.Shape3DType;
import eu.mihosoft.vrl.visual.VGraphicsUtil;
import java.awt.Dimension;
import java.io.Serializable;

/**
 * Allows a methode to use the properties of the <code>PickUniverseCreator</code>
 * by setting the supported style to "pick".
 *
 * Use this represantation if you have one shapes and you want him to be picked.
 *
 * @author night
 */
public class PickTypeRepresentation extends Shape3DType implements Serializable{
    private static final long serialVersionUID = 1L;

    private PickUniverseCreator universe;
    private CustomCanvas canvas;

    /**
     * Set the key word for supported style to "pick".
     *
     * Creates a <code>PickUniverseCreator</code> and a <code>CustomCanvas</code>
     * calls after initialisation init3DView(canvas, creator).
     */
    public PickTypeRepresentation() {

//        setValueName("Pick:");
        
//        setSupportedStyle("pick");
        setStyleName("pick");

        universe = new PickUniverseCreator();
        canvas = new CustomCanvas(this, universe);

        init3DView(canvas, universe); //ACHTUNG !!! erst hier wird init() von PickUniCreator ausgeführt

//        if (!VGraphics.NO_3D) {
        if (!VGraphicsUtil.NO_3D) {
            canvas.getSelectionButtonGroup().setUniverseActive(true);
        }
    }

    /**
     * Returns a new PickTypeRepresentation.
     *
     * @return new PickTypeRepresentation
     */
    @Override
    public TypeRepresentationBase copy() {
        return new PickTypeRepresentation();
    }

    /**
     * Dispose additional resources. It will be called when the parent canvas
     * window is closed.
     */
    @Override
    public void dispose() {

//        if (creator != null) {
            universe = null;
//        }
        super.dispose();
    }

    @Override
    public void enterFullScreenMode(Dimension size) {  
        super.enterFullScreenMode(size);

        canvas.setFullScreenMode(getMainCanvas(), true);
    }

    @Override
    public void leaveFullScreenMode() {
        super.leaveFullScreenMode();
        canvas.setFullScreenMode(getMainCanvas(), false);
    }


}
