/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.generators;

import basic.math.elements.visual.VisualScalar;
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
 * VisualScalar and adds it to canvas, if all parameters are correct.
 * @author night
 */
@ObjectInfo(name = "Scalar Generator")
@ComponentInfo(name = "ScalarGenerator", category = "BasicMath/ElementGenerator")
public class ScalarGenerator extends ElementGenerator implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * {@inheritDoc}
     */
    @MethodInfo(name = "create")
    @Override
    public void generateElement(
            MethodRequest methodRequest,
            @ParamInfo(name = " ", style = "array", options = "hideConnector=true; hideButtonBox=true") Integer[] dimensions,
            ClassSelection dataType,
            @ParamInfo(name = "mathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "name") String shortName,
            CanvasRequest canvasRequest) {
        //
        // A LITTLE TRICK / HACK
        // is need because of the user has no possibility
        // to give the right dimension size because of ParamInfo(hide.. =true)
        //
        dimensions =new Integer[1];
        dimensions[0]=1;
        //
        super.generateElement(methodRequest, dimensions, dataType, mathML, shortName, canvasRequest);
    }

    /**
     * {@inheritDoc}
     *
     * Checks additionaly the count and the size of dimensions.
     * This means each entry in dimensions must be at least one and there need
     * to be 1 or more entries for a Scalar.
     *
     * @return a new VisualScalar
     */
    @Override
    protected VisualScalar getNewVisualElementInstance(MethodRequest methodRequest,
            Integer[] dimensions, Class<?> dataType, String mathML, String shortName, Boolean clickable) {
        if ((dimensions.length > 0) && (dimensions[0] == 1)) {
            return new VisualScalar(mathML, shortName, clickable, dataType);
        } else {
            throw new IllegalArgumentException(ScalarGenerator.class + " dimensions is not correct.");
        }
    }
}
