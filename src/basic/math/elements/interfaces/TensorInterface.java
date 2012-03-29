/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;

/**
 * This is the basic interface for all kind of data <code>Tensor</code>s.
 *
 * @author night
 */
public interface TensorInterface extends ElementInterface {

    /**
     * Get entry from the position that is coded by (row, col, deep).
     *
     * @param row which row
     * @param col which column
     * @param deep which deepness
     * @return the entry at the position
     */
    @MethodInfo(hide = true)
    public Object getEntry(Integer row, Integer col, Integer deep);

    /**
     * Sets entry at the position which is coded by (row, col, deep).
     *
     * @param row which row
     * @param col which column
     * @param deep which deepness
     * @param value the value that should be set at this position
     */
    @MethodInfo(hide = true)
    public void setEntry(Integer row, Integer col, Integer deep, Object value);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int[] getDimensions();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Integer getArrayLength();

    /**
     * A specialization of getElement().
     *
     * @return the element as TensorInterface
     */
    @MethodInfo(hide = false)
    public TensorInterface getTensor(); 
}
