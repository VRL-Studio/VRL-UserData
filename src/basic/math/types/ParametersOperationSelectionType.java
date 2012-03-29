/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.helpers.ParametersOperationSelection;
import eu.mihosoft.vrl.types.SelectionInputType;
import java.io.Serializable;

/**
 * This typrepresentation allows to choose an operation from a list which is
 * defined in <code>ParametersOperationSelection</code>
 *
 * @author night
 */
public class ParametersOperationSelectionType extends SelectionInputType implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParametersOperationSelectionType() {

        setType(ParametersOperationSelection.class);
        setValueName("ParamOp: ");

        setValue(new ParametersOperationSelection());
    }
}
