package edu.gcsc.vrl.userdata;

import java.util.List;

/**
 * This class is to be inherited from by objects displaying subsets stored in
 * a FunctionDefinitionObservable object and belonging to a specific function
 * held by a FunctionSubsetCoordinatorFunctionObserver object.
 * @author mbreit
 */
public interface FunctionSubsetCoordinatorSubsetObserver
{
    /**
     * Updates the Subset Observer of a FunctionSubsetCoordinator object.
     * @param subsetData A list of strings representing subset names.
     */
    public void updateSubsets(List<String> subsetData);
}
