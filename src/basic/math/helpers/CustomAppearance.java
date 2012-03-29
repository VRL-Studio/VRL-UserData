/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.v3d.AppearanceArray;
import eu.mihosoft.vrl.v3d.AppearanceGenerator;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@ComponentInfo(name="Custom Appearance")
@ObjectInfo(name="Custom Appearance")
public class CustomAppearance implements Serializable {

    private static final long serialVersionUID=1;

   @MethodInfo(valueName="appearance")
    public AppearanceArray getAppearances(
        @ParamInfo(name="Color (wire): ", nullIsValid=true) Color wire,
        @ParamInfo(name="Color (solid):", nullIsValid=true) Color solid,
        @ParamInfo(nullIsValid=true,name="wire thickness (optional): ") Float t) {
        AppearanceArray result = new AppearanceArray();

        AppearanceGenerator generator = new AppearanceGenerator();

        Float thickness = t;

        if (thickness == null){
            thickness = 1F;
        }

        if (wire!=null) {
            result.add(generator.getLinedAppearance(wire,thickness));
        }
        if (solid != null) {
            result.add(generator.getColoredAppearance(solid, 0.f));
        }

        if (wire==null && solid==null){
            result.add(generator.getLinedAppearance(Color.white,thickness));
            result.add(generator.getColoredAppearance(Color.white, 0.f));
        }

        return result;
    }
}
