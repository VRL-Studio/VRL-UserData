/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.managers;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class DimensionManager {
    
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    
    /**
     * Checks if the data is an array and if counts the dimensions of the array.
     * 
     * <br>Example:  </br>
     * <br>NULL         => -1 </br>
     * <br>Object       => 0  </br>
     * <br>Object[]     => 1  </br>
     * <br>Object[][]   => 2  </br>
     * <br>Object[][][] => 3  </br>
     * 
     * @param data is the data that should be checked which array dimension it contains
     * 
     * @return the array dimension of data
     */
    public static int getArrayDimension(Object data){
        int dim = -1; // if data == null
        
        Class tmp = null;
        
        try {
            tmp = data.getClass();
        } catch (Exception e) {
            //data.getClass()
            //NULLPOINTER do not throw
            
            //e.printStackTrace(System.err);
        }
        
        while(tmp !=null){
            
            dim++;
            tmp = tmp.getComponentType();
        }
        return dim;
    }
   
    /**
     * Checks if the data is an array and if counts the dimensions of the array.
     *
     * Example: <br>
     * NULL         => -1 <br>
     * Object       => 0 <br>
     * Object[]     => 1 <br>
     * Object[][]   => 2 <br>
     * Object[][][] => 3 <br>
     *
     * @param data is the data that should be checked which array dimension it
     * contains
     *
     * @return the array dimension of data
     */
    public static int[] getArrayDimensionSizes(Object data) {

        int size = getArrayDimension(data);
        int[] dim = null;
        Object[] tmp = null;


        if (size > 0) {
            dim = new int[size];
            tmp = (Object[]) data;
            
            for (int i = 0; i < size; i++) {
                
                dim[i] = ((Object[]) tmp[i]).length;
            }

        }
        return dim;
    }
    
}
