/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualVector;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.awt.Color;
import java.io.Serializable;

/**
 * An implplementation of the explicit euler algorithm.
 *
 * @author night
 */
@ObjectInfo(name="ExplicitEuler")
@ComponentInfo(name = "EulerExplicit", category = "BasicMath")
public class EulerExplicit implements Serializable {

    private static final long serialVersionUID=1;

    /**
     * This Method starts the calculation.
     *
     * @param f the function that should be used.
     * @param yInit the start vector
     * @param tMax the max number of iterations
     * @param dt the stepsize of each step
     * @return the calculated trajectory / graph
     */
    public Trajectory run(
            @ParamInfo(name = "Function f")Function f,
            @ParamInfo(name = "Initial Vector")VisualVectorInterface yInit,
            @ParamInfo(name = "Max")VisualScalarInterface tMax,
            @ParamInfo(name = "dt")VisualScalarInterface dt) {

        int vectorSize = yInit.getArrayLength();

        VisualVector y0 = new VisualVector(
                "", "y0", Boolean.TRUE, vectorSize, Double.class);

        Trajectory trajectory = new Trajectory(Color.green, "Euler");

        // y0 = yInit
        for (int i = 0; i < vectorSize; i++) {
            y0.setEntry(i, yInit.getEntry(i));
        }

        VisualVector y1 = null;

        for (double t = 0; t < (Double) tMax.getEntry(); t += (Double) dt.getEntry()) {

            y1 = f.run(y0);

            // y1 = y0 + f(y0,t) * dt
            for (int i = 0; i < vectorSize; i++) {
                Double value1 = (Double) y1.getEntry(i) * (Double) dt.getEntry();
                Double value2 = (Double) y0.getEntry(i);
                Double added = (value1 + value2);

                y1.setEntry(i, added);
            }

            // y0 = y1
            for (int i = 0; i < vectorSize; i++) {
                y0.setEntry(i, y1.getEntry(i));
            }

            trajectory.add(y1);
        }

        return trajectory;
    }
}

