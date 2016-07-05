/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012–2016 Goethe Universität Frankfurt am Main, Germany
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.util;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class DimensionUtil {
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
