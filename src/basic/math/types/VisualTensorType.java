/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.elements.Tensor;
import basic.math.elements.interfaces.VisualTensorInterface;
import java.io.Serializable;

/**
 * {@inheritDoc}
 *
 * Adaptions for tensors.
 *
 * @author night
 */
public class VisualTensorType extends VisualElementType implements Serializable {

    private static final long serialVersionUID = 1;

    public VisualTensorType() {
        super();

        setType(VisualTensorInterface.class);
        setValueName(getDefaultTypeName());
        
//        setSupportedStyle("default");
        setStyleName("default");

    }

    /**
     * This method returns the default type name which is shown after a Connector.
     *
     * @return the best spezialized default name possible
     */
    @Override
    public String getDefaultTypeName() {
        return Tensor.DEFAULT_NAME;
    }

}
