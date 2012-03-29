/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.elements.Scalar;
import basic.math.elements.interfaces.VisualScalarInterface;
import java.io.Serializable;

/**
 * {@inheritDoc}
 *
 * Adaptions for scalars.
 *
 * @author night
 */
public class VisualScalarType extends VisualVectorType implements Serializable {

    private static final long serialVersionUID = 1;

    public VisualScalarType() {
        super();

        setType(VisualScalarInterface.class);
        setValueName(getDefaultTypeName());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultTypeName() {
        return Scalar.DEFAULT_NAME;
    }
}
