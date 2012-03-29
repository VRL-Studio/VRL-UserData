/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import basic.math.elements.visual.VisualVector;
import java.io.Serializable;


/**
 * Interface for functions with vector-valued input and output.
 *
 * @author night
 */
public interface Function extends Serializable{

    /**
     * Returns the function output.
     * @param params the input vector
     * @return the function output
     */
    public VisualVector run(VisualVector params);

   /**
     * Returns a reference to this function.
     * @return a reference to this function
     */
    public Function getReference();

}
