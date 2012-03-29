/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.skin;

import eu.mihosoft.vrl.types.Selection;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A list of all available GeomDef´s.
 *
 * @author night
 */
public class GeomDefSelection extends Selection implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * Default values for a GeomDefSelection
     */
    public GeomDefSelection() {
        ArrayList<String> collection = new ArrayList<String>();

        collection.add("glayer8");
        collection.add("glayer8cor");

        setCollection(collection);
    }
}
