/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import basic.math.elements.visual.VisualVector;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import java.io.Serializable;

/**
 * The implemented version of
 * r``= gamma * M * ( r / ||r||^3 )
 *
 * with:
 * r as a 3 dim vector
 * gamma = 1
 * M = 1
 * 
 * @author night
 */
@ObjectInfo(name="TwoBodyProblem")
@ComponentInfo(name = "TwoBodyProblem", category = "BasicMath/Procedure")
public class TwoBodyProblem implements Function, Serializable {

    private static final long serialVersionUID=1;

    // add your code here

    private Integer size = 3;


    @MethodInfo(hide=true)
    @Override
    public VisualVector run(VisualVector y) {

        if ( y.getArrayLength() != size*3){
            throw new IllegalArgumentException("Wrong number of arguments!");
        }

        VisualVector r = new VisualVector("", "r", Boolean.TRUE, size, Double.class);
//        r.setSize(size);
        VisualVector v = new VisualVector("", "v", Boolean.TRUE, size, Double.class);
//        v.setSize(size);
        VisualVector a = new VisualVector("", "a", Boolean.TRUE, size, Double.class);
//        a.setSize(size);

        // we don't change r, thus we just copy it
        for ( int i = 0; i < size; i++){
            r.setEntry(i,y.getEntry(i));
        }

        int offset = size;

        // we don't change v, thus we just copy it
        for ( int i = 0; i < size; i++){
            v.setEntry(i,y.getEntry(i+offset));
        }

         // compute r^2
        double rSquare = 0;
        for (int i = 0; i < size; i++){
                rSquare += (Double)r.getEntry(i)* (Double)r.getEntry(i);
        }

        // compute a
        for (int i = 0; i < size; i++){

            Double value = - (Double)r.getEntry(i) / ( rSquare * Math.sqrt( rSquare ) );

            a.setEntry(i,value);
        }

        VisualVector result = new VisualVector("", "r", Boolean.TRUE, size*3, Double.class);;
//        result.setSize(size*3);

        for (int i = 0; i < size; i++){
            result.setEntry(i, v.getEntry(i));
        }

        offset = size;

        for (int i = 0; i < size; i++){
            result.setEntry(offset + i, a.getEntry(i));
        }

        offset = size*2;

        for (int i = 0; i < size; i++){
            result.setEntry(offset + i, r.getEntry(i));
        }

        return result;
    }

    @MethodInfo(name="connect:",interactive=false)
    @Override
    public Function getReference(){
        System.out.println(">> TwoBodyProblem.getReference()");
        return this;
    }
}

