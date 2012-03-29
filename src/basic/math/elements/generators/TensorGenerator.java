/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.generators;

import basic.math.elements.visual.VisualElement;
import basic.math.elements.visual.VisualTensor;
import basic.math.helpers.ClassSelection;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.types.CanvasRequest;
import eu.mihosoft.vrl.types.MethodRequest;
import java.io.Serializable;

/**
 * This class is a specialization of ElementGenerator. It generates a
 * VisualTensor and adds the tensor to canvas, if all parameters are correct.
 * @author night
 */
@ObjectInfo(name = "Tensor Generater")
@ComponentInfo(name = "TensorGenerator", category = "BasicMath")
public class TensorGenerator extends ElementGenerator implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * {@inheritDoc}
     */
    @MethodInfo(name = "create")
    @Override
    public void generateElement(
            MethodRequest methodRequest,
            @ParamInfo(name = "dimensions", style = "array", options = "minArraySize=3; hideButtonBox=true") Integer[] dimensions,
            ClassSelection dataType,
            @ParamInfo(name = "mathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "name") String shortName,
            CanvasRequest canvasRequest
            ) {
        super.generateElement(methodRequest, dimensions, dataType, mathML, shortName, canvasRequest);
    }

    /**
     * {@inheritDoc}
     *
     * Checks additionaly the count and the size of dimensions.
     * This means each entry in dimensions must be at least one and there need
     * to be 3 or more entries for a Tensor.
     *
     * @return a new VisualTensor
     */
    @Override
    protected VisualElement getNewVisualElementInstance(
            MethodRequest methodRequest,
            Integer[] dimensions, Class<?> dataType,
            String mathML, String shortName, Boolean clickable) {

        if ((dimensions.length > 2) && (checkDimensionSizes(methodRequest, dimensions))) {

            return new VisualTensor(mathML, shortName, clickable,
                    dimensions[0], dimensions[1], dimensions[2], dataType);
        } else {
            throw new IllegalArgumentException(TensorGenerator.class + " dimensions.length is to short");
        }
    }
    
}
