/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;

/**
 * This is the basic interface for all kind of data <code>Matrix</code>'s.
 * @author night
 */
public interface MatrixInterface extends TensorInterface{

    /**
     * Get entry from the position that is coded by (row, col).
     *
     * @param row which row
     * @param col which column
     * @return the entry at the position (row,col)
     */
    @MethodInfo(hide = true)
    public Object getEntry(Integer row, Integer col);

    /**
     * Sets entry at the position which is coded by (row, col).
     *
     * @param row which row
     * @param col which column
     * @param value the value that should be set at this position
     */
    @MethodInfo(hide = true)
    public void setEntry(Integer row, Integer col, Object value);

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
     * @return the element as MatrixInterface
     */
    @MethodInfo(hide = false)
    public MatrixInterface getMatrix();
}
