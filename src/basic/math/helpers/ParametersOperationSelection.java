/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;

import eu.mihosoft.vrl.types.Selection;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is a basic list of operation which can be done with two elements.
 * E.g. to add two matrixes.
 *
 * @author night
 */
public class ParametersOperationSelection extends Selection implements Serializable{
    private static final long serialVersionUID = 1L;

    public ParametersOperationSelection() {

        ArrayList<String> collection = new ArrayList<String>();

        collection.add("+");
        collection.add("-");
//        collection.add("*");
//        collection.add("/");

        setCollection(collection);
    }

}
