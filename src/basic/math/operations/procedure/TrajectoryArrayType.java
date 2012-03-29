/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import eu.mihosoft.vrl.types.ArrayBaseType;


/**
 * The the default TypeRepresentation for an array of Trajectories.
 *
 * @author night
 */
public class TrajectoryArrayType extends ArrayBaseType {

    public TrajectoryArrayType() {
        setValueName("Trajectory Array");
        setType(Trajectory[].class);
    }
}
