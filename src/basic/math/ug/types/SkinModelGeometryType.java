/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import basic.math.ug.skin.SkinModelGeometry;
import eu.mihosoft.vrl.types.TypeTemplate;
import java.io.Serializable;
//import skin.SkinModelGeometry;

/**
 *
 * @author Night
 */
public class SkinModelGeometryType extends TypeTemplate implements Serializable{
    private static final long serialVersionUID = 1L;

    public SkinModelGeometryType() {

        setType(SkinModelGeometry.class);
        setValueName("SkinModelGeometry");
    }

    /**
     * {@inheritDoc}
     *
     * Method overriden to avoid session loading bug, where last values are
     * used for script but default values are shown. Therefor returns always false.
     */
    @Override
    protected boolean isUpToDate() {
        return false;
    }
}
