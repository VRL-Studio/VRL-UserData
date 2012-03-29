/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.types;

import basic.math.elements.Matrix;
import basic.math.elements.interfaces.VisualMatrixInterface;
import java.io.Serializable;

/**
 * {@inheritDoc}
 *
 * Adaptions for matrices.
 *
 * @author night
 */
public class VisualMatrixType extends VisualTensorType implements Serializable{
    private static final long serialVersionUID = 1;

    public VisualMatrixType() {
        super();

        setType(VisualMatrixInterface.class);
        setValueName(getDefaultTypeName());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultTypeName() {
        return Matrix.DEFAULT_NAME;
    }
}
