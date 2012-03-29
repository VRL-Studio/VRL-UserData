/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.mappings;

import java.io.Serializable;

/**
 * This Interface allows different kind of accesses of the concret memory array.
 * So by changing the the mapping instance it is possible to get access to e.g.
 * every second entry.
 *
 * @author night
 */
public interface Mapping extends Serializable{
    

    /**
     * Converts the multidimensional position into an index of the linear array.
     *
     * @param position of the entry in the multidimensional data
     * @param dimsions the size of the multidimensional data
     * @return the index of the wanted entry in the linear array
     */
    public Integer getIndex(int[] position, int[] dimsions);

    /**
     * Calculates the real linear memory array lenght.
     *
     * @return the product of all dim(i)
     */
    public Integer calculateCompleteArrayLength();

    /**
     * The visual length can be smaller than the complete array length because
     * of using e.g. only every second data entry for visualization.
     *
     * @return the visual length of mapped element if != null , else arrayLength
     */
    public Integer getVisualLength();

    /**
     * @return the converted visual index in the real array index
     */
    public Integer visualIndexToArrayIndex(Integer visualIndex);
}
