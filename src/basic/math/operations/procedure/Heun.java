/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.procedure;

import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualVector;
import basic.math.operations.conception.AddOperation;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.awt.Color;
import java.io.Serializable;

/**
 * An implplementation of the heun algorithm.
 *
 * @author night
 */
@ObjectInfo(name = "Heun")
@ComponentInfo(name = "Heun", category = "BasicMath")
public class Heun implements Serializable {

    private static final long serialVersionUID = 1;

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
            @ParamInfo(name = "Function f") Function f,
            @ParamInfo(name = "Initial Vector") VisualVectorInterface yInit,
            @ParamInfo(name = "Max") VisualScalarInterface tMax,
            @ParamInfo(name = "dt") VisualScalarInterface dt) {

        int vectorSize = yInit.getArrayLength();

        VisualVector y0 = new VisualVector(
                "", "y0", Boolean.TRUE, vectorSize, Double.class);

        Trajectory trajectory = new Trajectory(Color.yellow, "Heun");

        // y0 = yInit
        for (int i = 0; i < vectorSize; i++) {
            y0.setEntry(i, yInit.getEntry(i));
        }

        VisualVector y1 = null;
        VisualVector y2 = null;

        Double ddt = (Double) dt.getEntry();

        for (double t = 0; t < (Double) tMax.getEntry(); t += ddt) {

            y1 = f.run(y0);
            y2 = f.run(y1);

//            // y1 = y0 + f(y0,t) * dt
//            for (int i = 0; i < vectorSize; i++) {
//                Double value1 = (Double) y1.getEntry(i) * (Double) dt.getEntry();
//                Double value2 = (Double) y0.getEntry(i);
//                Double added = (value1 + value2);
//
//                y1.setEntry(i, added);
//            }

//            // y1 = y0 + dt/2 * ( f(t,y0) + f(t+dt, y0+dt*f(t,y0) )
//            for (int i = 0; i < vectorSize; i++) {
//
//                Double yy0 = (Double) y0.getEntry(i);
//
//
//                Double yy1 = yy0 + ddt / 2 * ((Double) y1.getEntry(i) + yy0 + ddt * (Double) y1.getEntry(i));
//
//
//                y1.setEntry(i, yy1);
//            }
            VisualVector value = mult(add( f.run(y0), f.run(add(y0, mult(f.run( y0 ),ddt) ) ) ),ddt/2.0);

           y1 = add(y0, value);

            // y0 = y1
            for (int i = 0; i < vectorSize; i++) {
                y0.setEntry(i, y1.getEntry(i));
            }

            trajectory.add(y1);
        }

        return trajectory;
    }

    @MethodInfo(hide=true,noGUI=true)
    public VisualVector add(VisualVector a,VisualVector b){

        AddOperation add = new AddOperation();

        VisualVector result = (VisualVector) add.executeVector("", "", a, b);
        return result;
    }

    @MethodInfo(hide=true,noGUI=true)
    public VisualVector mult(VisualVector a, Double s){

        VisualVector result = new VisualVector("", "res", Boolean.TRUE, a.getDimensions()[0], a.getDataType());

        for (int i = 0; i < a.getArrayLength(); i++) {

            Double tmp = ((Double)a.getEntry(i))*s;

            result.setEntry(i, tmp);

        }

        return result;
    }
}
