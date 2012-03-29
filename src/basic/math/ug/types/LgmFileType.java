/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import eu.mihosoft.vrl.io.VFileFilter;
import eu.mihosoft.vrl.types.LoadFileType;
import java.io.Serializable;

/**
 * This Typerepresentation provides the following functionality in addition to
 * its visualisation function.
 *
 * In the open/load dialog you are only allowed to load files with the .lgm ending.
 *
 * @author night
 */
public class LgmFileType extends LoadFileType implements Serializable {

    public LgmFileType() {
        setValueName("lgmFile:"); //the name which is displayed above the input section

//        setSupportedStyle("lgm"); //the style that should be used to actived this typerepresentation        
        setStyleName("lgm");
    }


    /**
     * {@inheritDoc}
     *
     * Here a new Filter is created and returned that allows only to open files
     * with .lgm ending.
     */
    @Override
    public VFileFilter getFileFilter() {
        VFileFilter result = new VFileFilter();

        result.addEnding(".lgm");
        result.setDescription(".lgm File");

        return result;
    }
}

