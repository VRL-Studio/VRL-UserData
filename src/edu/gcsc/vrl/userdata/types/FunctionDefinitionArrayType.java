package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.FunctionDefinition;
import eu.mihosoft.vrl.annotation.TypeInfo;

/**
 *
 * @author mbreit
 */
@TypeInfo(type = FunctionDefinition[].class, input = true, output = false, style = "array")
public class FunctionDefinitionArrayType extends UserDataArrayBaseType
{    
   
    public FunctionDefinitionArrayType()
    {
        setValueName("Function Definition Array");
    }

    /**
     * Evaluates the contract, e.g., checks for correct data type or range
     * condition.
     */
    @Override
    protected void evaluateContract()
    {
        // deactivated contract evaluation to prevent multiple error messages
        // due to null array

        if (value == null) invalidateValue();
    }
    
    
}
