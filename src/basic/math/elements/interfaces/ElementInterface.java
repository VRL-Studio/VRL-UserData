/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.interfaces;

import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import java.io.Serializable;

/**
 * This is the basic interface for all kind of data <code>Element</code>s.
 *
 * @author night
 */
public interface ElementInterface extends Serializable {

    /**
     * @param position an array which contains the positions / coordinates of
     *        the entry of interesse.
     *        e.g. for a matrix position.length ==2 and position[0]==1 position[1]==3
     *        means entry of an two diminsonal arrray and the entry on position(1,3)
     *
     * @return the entry of interesse
     */
    @MethodInfo(hide = true)
    public Object getEntry(int[] position);

    /**
     * Get the value from the data array, if index is a valid index.
     *
     * @param index the index in the data array
     * @return the wanted entry
     */
    @MethodInfo(hide = true)
    public Object getEntry(int index);

    /**
     * Sets the value in the data array, if index is a valid index.
     *
     * @param index the index in the data array
     * @param value the value to be set
     */
    @MethodInfo(hide = true)
    public void setEntry(int index, Object value);

    /**
     * Sets the entry/value at given position.
     * The class of the value and the return value of getDataTyp() must be equal.
     *
     * @param position contains the position where the value should be stored
     * @param value the value that should be stored
     */
    @MethodInfo(hide = true)
    public void setEntry(int[] position, Object value);

    /**
     * The mapping knows what data structure (e.g. matrix or scalar) is stored
     * in the data array and how to get the entry of a multidimensional data
     * element from the linear data array.
     *
     * @return the used mapping
     */
    @MethodInfo(hide = true)
    public Mapping getMapping();

    /**
     * The mapping knows what data structure (e.g. matrix or scalar) is stored
     * in the data array and how to get the entry of a multidimensional data
     * element from the linear data array.
     *
     * @param mapping that should be used
     */
    @MethodInfo(hide = true)
    public void setMapping(Mapping mapping);

    /**
     * This method should be overriden of each data element and return a
     * specialized mapping for the underlying data structure.
     * This means a matrix returns a default mapping for matrizes and a vector
     * for vectors and so on.
     * 
     * @return a default mapping
     */
    public Mapping createDefaultMapping();

    /**
     * All data is stored in a one dimensional array.
     * This method returns the object which contains the array where all data is
     * stored.
     * Be aware of the limitation of the array index (integer).
     * Therefor the max number of entries is restricted to 2.147.483.647 .
     * You can get access on the data with the help of Array.get(...).
     *
     * @return the object which contains all data entries.
     */
    @MethodInfo(hide = true)
    public Object getDataArray();

    /**
     * @return the length of the data array
     */
    public Integer getArrayLength();

    /**
     * The information of how many dimensions this element consists is coded
     * in the length of the array that is returned by this method.
     * The information about the deepness of the dimension j is stored in
     * dimension[j]. NOTE: the array begins with j=0 and ends with length-1.
     *
     * @return the integer array which stores the information about the dimensions
     * of this element.
     */
    @MethodInfo(hide = true)
    public int[] getDimensions();

    /**
     * To know which kind/typ of data is stored in the array you can call this
     * method which returns a class object of the entries of the array.
     *
     * @return the class of the data / the entries in the data array.
     */
    @MethodInfo(hide = true)
    public Class<?> getDataType();

    /**
     * By setting this value it will be decided which kind/typ of data can be stored
     * in this data element.
     * @param dataType the type/class of the data which is stored in this data element
     */
    public void setDataType(Class<?> dataType);

    /**
     * Fills all entries of the data array with the object that is used in the
     * parameter. The class of this object must be compatibable with the return
     * value of getDataTyp().
     *
     * @param value the value which is stored in all entries of the array
     */
    @MethodInfo(hide = true)
    public void fillAllEntriesWith(Object value);

    /**
     *
     * @return the default name for this class
     */
    @MethodInfo(hide = true)
    public String getDefaultName();

    /**
     * @return the data element.
     */
    @MethodInfo(hide = true)
    public ElementInterface getElement();

    /**
     * @param dimensions the dimensions to set
     */
    public void setDimensions(int[] dimensions);

    /**
     * @param arrayLength the arrayLength to set
     */
    public void setArrayLength(Integer arrayLength);

    /**
     * @param dataArray the dataArray to set
     */
    public void setDataArray(Object dataArray);
}
