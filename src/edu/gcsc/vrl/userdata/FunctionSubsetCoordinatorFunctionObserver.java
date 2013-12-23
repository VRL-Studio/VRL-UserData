package edu.gcsc.vrl.userdata;

import java.util.List;

/**
 * This class is to be inherited from by objects displaying functions stored in
 * a FunctionDefinitionObservable object.
 * @author mbreit
 */
public interface FunctionSubsetCoordinatorFunctionObserver
{
    /**
     * Updates the Function Observer of a FunctionSubsetCoordinator object.
     * @param fctData A list of strings representing function names.
     */
    public void updateFunctions(List<String> fctData);
    
    /**
     *  The method allows access to the currently selected function index.
     * @return index of selected function
     */
    public int getSelectedFunction();
}
