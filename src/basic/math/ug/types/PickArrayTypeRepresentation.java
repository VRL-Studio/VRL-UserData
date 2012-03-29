/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import basic.math.ug.equation.buttons.CustomCanvas;
import basic.math.ug.equation.helpers.PickUniverseCreator;
import eu.mihosoft.vrl.types.Shape3DArrayType;
import eu.mihosoft.vrl.visual.VGraphicsUtil;
import java.awt.Dimension;
import java.io.Serializable;

/**
 * Allows a methode to use the properties of the <code>PickUniverseCreator</code>
 * by setting the supported style to "pick".
 *
 * Use this represantation if you have many shapes which should be picked.
 *
 * @author night
 */
public class PickArrayTypeRepresentation extends Shape3DArrayType implements Serializable{
    private static final long serialVersionUID = 1L;

    private PickUniverseCreator universe;
    private boolean initialized = false;
//    private VUniverseCreator universe;

    private CustomCanvas canvas;

    /**
     * Set the key word for supported style to "pick".
     *
     * Creates a <code>PickUniverseCreator</code> and a <code>CustomCanvas</code>
     * calls after initialisation init3DView(canvas, creator).
     */
    public PickArrayTypeRepresentation() {

        setValueName("Pick:");
//        setSupportedStyle("pick");
        setStyleName("pick");

        universe = new PickUniverseCreator();
        canvas = new CustomCanvas(this, universe);

//
//        canvas.setFocusable(true);
//        canvas.requestFocus();

        //ACHTUNG !!! erst hier wird init() von PickUniCreator ausgeführt
        init3DView(canvas, universe);

//        if (!VGraphics.NO_3D) {
        if (!VGraphicsUtil.NO_3D) {            
            canvas.getSelectionButtonGroup().setUniverseActive(true);
        }
        initialized = true;
    }

    /**
     * Dispose additional resources. It will be called when the parent canvas
     * window is closed.
     */
    @Override
    public void dispose() {

        // 1. () only for initializing (see vrl Shape3dArrayType, init3DView())
        if (!initialized) {
            super.dispose();
        } else { // 2. real dispose for closing this type representation
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {

                    if (universe != null) {

//                        System.out.println("   DISPOSE!   PickArrayTypeRepresentation");
                        universe = null;
                    }

                    try {
                        setReturnTypeOutdated();
                        PickArrayTypeRepresentation.super.dispose();
                    } catch (Exception ex) {
                        //
                    }
                }
            });

            t.start();
        }
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
