/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.generators;

import basic.math.elements.visual.VisualMatrix;
import basic.math.helpers.ClassSelection;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.types.CanvasRequest;
import eu.mihosoft.vrl.types.MethodRequest;
import java.io.Serializable;

/**
 * This class is a specialization of ElementGenerator, generates a
 * VisualMatrix and adds it to canvas, if all parameters are correct.
 * @author night
 */
@ObjectInfo(name = "Matrix Generater")
@ComponentInfo(name = "MatrixGenerator", category = "BasicMath/ElementGenerator")
public class MatrixGenerator extends ElementGenerator implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * {@inheritDoc}
     */
    @MethodInfo(name = "create")
    @Override
    public void generateElement(
            MethodRequest methodRequest,
            @ParamInfo(name = "dimensions", style = "array", options = "minArraySize=2; hideButtonBox=true") Integer[] dimensions,
            ClassSelection dataType,
            @ParamInfo(name = "mathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "name") String shortName,
            CanvasRequest canvasRequest
            ) {
        super.generateElement(methodRequest, dimensions,dataType, mathML, shortName, canvasRequest);
    }

    /**
     * {@inheritDoc}
     *
     * Checks additionaly the count and the size of dimensions.
     * This means each entry in dimensions must be at least one and there need
     * to be 2 or more entries for a Matrix.
     *
     * @return a new VisualMatrix
     */
    @Override
    protected VisualMatrix getNewVisualElementInstance(MethodRequest methodRequest,
            Integer[] dimensions, Class<?> dataType, String mathML, String shortName, Boolean clickable) {

        if ((dimensions.length > 1) && (checkDimensionSizes(methodRequest, dimensions))) {

            return new VisualMatrix(mathML, shortName, clickable, dimensions[0], dimensions[1], dataType);
        } else {
            throw new IllegalArgumentException(MatrixGenerator.class + " dimensions.length is to short");
        }
    }
}
