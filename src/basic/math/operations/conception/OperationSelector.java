/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.conception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class should be a central place all available operations a registered
 * and if one operations is need it will get from these class.
 *
 * This class is a singelton to make sure that operations are registered in the
 * same instance.
 *
 * @author night
 */
public class OperationSelector implements Serializable {

    private static final long serialVersionUID = 1;
    //
    private static OperationSelector instance = null;
    //
    private HashMap<String, Operation> operationsHashMap;

    private OperationSelector() {
        operationsHashMap = new HashMap<String, Operation>();
    }

    /**
     * @return the one and only instance of this class
     */
    public static OperationSelector getOperationSelector() {

        if (instance == null) {
            instance = new OperationSelector();
        }

        return instance;
    }

    /**
     * This method check if there is an Operation known which conform with
     * the involved parameters.
     *
     * @param implementationInfo the short name for this operation
     * @param parameterClasses an arraylist of classes of the involved elements
     * @param datatype e.g. Double if Element.Double is involved
     * @return the mathching Operation for the three parameters
     */
    public Operation getOperation(
            String implementationInfo,
            ArrayList<Class> parameterClasses,
            Class<?> datatype) {

        Operation op = operationsHashMap.get(implementationInfo + parameterClasses + datatype);

        return op;

    }

    /**
     * Adds the operation to the list of available operations.
     *
     * @param op the to added operation
     * @param implementationInfo the short name for this operation
     * @param parameterClasses an arraylist of classes of the involved elements
     * @param datatype e.g. Double if Element.Double is involved
     */
    public void addOperation(
            Operation op,
            String implementationInfo,
            ArrayList<Class> parameterClasses,
            Class<?> datatype) {

        operationsHashMap.put(implementationInfo + parameterClasses + datatype, op);
    }

}
