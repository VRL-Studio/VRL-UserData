/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.elements.Vector;
import basic.math.elements.interfaces.VisualVectorInterface;
import java.io.Serializable;

/**
 * {@inheritDoc}
 *
 * Adaptions for vectors.
 *
 * @author night
 */
public class VisualVectorType extends VisualMatrixType implements Serializable {

    private static final long serialVersionUID = 1;

    public VisualVectorType() {
        super();

        setType(VisualVectorInterface.class);
        setValueName(getDefaultTypeName());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultTypeName() {
        return Vector.DEFAULT_NAME;
    }
}
