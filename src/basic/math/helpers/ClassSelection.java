/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import eu.mihosoft.vrl.types.Selection;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used in element generator classes to give the user a list of
 * the supported datatyp for the entries in the new created element.
 *
 * By selecting one item of this list the new element will contain entries of 
 * this datatyp. Default selection is Double.
 *
 * @author night
 */
public class ClassSelection extends Selection implements Serializable {

    private static final long serialVersionUID = 1L;

    ArrayList<Class> collection = new ArrayList<Class>();

    /**
     * Adds Double, Float Integer and Long to the list of supported datatyps.
     */
    public ClassSelection() {

        collection.add(Double.class);
        collection.add(Float.class);
        collection.add(Integer.class);
        collection.add(Long.class);
//        collection.add(Object.class);

//// X.TYPE Instance representation for primitive type x
//        collection.add(Double.TYPE);
//        collection.add(Float.TYPE);
//        collection.add(Integer.TYPE);
//        collection.add(Long.TYPE);

        setCollection(collection);
    }

    public Class getSelectedClass() {
        Class clazz = collection.get(getSelectedIndex());

        return clazz;
    }
}
