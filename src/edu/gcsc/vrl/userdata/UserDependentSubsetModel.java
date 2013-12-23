package edu.gcsc.vrl.userdata;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author mbreit
 */
public class UserDependentSubsetModel extends UserDataModel
{
    private String selectedFunction = "";
    private int selectedFunctionIndex = -1;
    private List<String> selectedSubsets = new ArrayList<String>();
    private int[] selectedSubsetIndices = new int[]{};
    //private List<AtomicInteger>selectedSubsetIndices = new ArrayList<AtomicInteger>();
    
    // inherited from UserDataModel
    /**
     * Not to be used.
     * Use getSelectedFunction() and getSelectedSubset() instead.
     * @return error
     */
    @Override
    public Object getData()
    {
        throw new RuntimeException("UserDependentSubsetModel: Call to getData() which is not allowed.");
    }
    
    /**
     * @return selected function name
     */
    public String getSelectedFunction()
    {
        return selectedFunction;
    }
            
    /**
     * @return selected subset name
     */
    public List<String> getSelectedSubsets()
    {
        return selectedSubsets;
    }
            
    /**
     * @return selected function index
     */
    public int getSelectedFunctionIndex()
    {
        return selectedFunctionIndex;
    }
    
    /**
     * Find the index belonging to the selected subset.
     * @return selected index
     */
    public int[] getSelectedSubsetIndices()
    {
        return selectedSubsetIndices;
    }
    
    /**
     * Not to be used.
     * Use setSelectedFunction() and setSelectedSubset() instead.
     * @param newData
     */
    @Override
    public void setData(Object newData)
    {
         throw new RuntimeException("UserDependentSubsetModel: Call to setData() which is not allowed.");
    }

    
    /**
     * Set information about selected function.
     * @param fctName   function name as string
     * @param fctIndex   function index in definition
     */
    public void setSelectedFunction(String fctName, int fctIndex)
    {
         selectedFunction = fctName;
         selectedFunctionIndex = fctIndex;
         
         if (fctName != null && !fctName.equals("")) setStatus(Status.VALID);
    }

    
    /**
     * Set information about selected subset.
     * @param ssNames       function names as string
     * @param ssIndices     function index in definition   
     */
    public void setSelectedSubsets(List<String> ssNames, int[] ssIndices)
    {
         selectedSubsets = ssNames;
         selectedSubsetIndices = ssIndices;
         
         if (ssNames == null || ssNames.isEmpty()) setStatus(Status.INVALID);
    }
    
    
    /**
     * @param model the model to set
     */
    @Override
    public void setModel(UserDataModel model)
    {
        if (model instanceof UserDependentSubsetModel)
        {
            UserDependentSubsetModel m = (UserDependentSubsetModel) model;
            setSelectedFunction(m.getSelectedFunction(), m.getSelectedFunctionIndex());
            setSelectedSubsets(m.getSelectedSubsets(), m.getSelectedSubsetIndices());
            setStatus(m.getStatus());
        }
        else throw new RuntimeException("UserData could not be set from other UserDataModel.");
    }
    
    /**
     * Not to be used.
     * Use adjustFunctionData(), adjustSubsetData() instead.
     * @param data
     */
    @Override
    public void adjustData(Object data)
    {
         throw new RuntimeException("UserDependentSubsetModel: Call to adjustData() which is not allowed.");
    }
    
    /**
     * Adjusts function data according to given functions.
     * 
     * @param fctList     new fct data
     */
    public void adjustFunctionData(List<String> fctList)
    {
        if (fctList == null)
        {
            setStatus(Status.INVALID);
            return;
        }
        
        for (String fct: fctList)
        {
            if (fct.equals(selectedFunction))
            {
                if (getStatus() != Status.VALID) setStatus(Status.WARNING);
                return;
            }
        }

        // control reaches this code only if new function definition
        // does not contain currently selected function, then:
        if (fctList.size() > 0 && fctList.get(0) != null)
        {
            selectedFunction = fctList.get(0);
            selectedFunctionIndex = 0;

            setStatus(Status.WARNING);
        }
        else
        {
            selectedFunction = "";
            selectedFunctionIndex = -1;

            setStatus(Status.INVALID);
        }
    }

    
    
    /**
     * Adjusts subset data according to given subsets.
     * 
     * @param ssList     new file data
     */
    public void adjustSubsetData(List<String> ssList)
    {
        if (ssList == null)
        {
            setStatus(Status.INVALID);
            return;
        }
        
        // compare selectedSubsetsList with given List,
        // check occurence of each selected element 
        boolean allFound = true;
        int[] ssiList = getSelectedSubsetIndices();
        int selssi = -1;
        for (String sel: selectedSubsets)
        {
            selssi++;
            boolean found = false;
            String ss;
            for (int ssi=0; ssi < ssList.size(); ssi++)
            {
                ss = ssList.get(ssi);
                if (ss.equals(sel))
                {
                    ssiList[selssi] = ssi;
                    found = true;
                    break;
                }
            }
            if (!found) allFound = false;
        }
        if (allFound)
        {
            setSelectedSubsets(selectedSubsets, ssiList);
            return;
        }

        // control reaches this code only if new subset definition
        // does not contain currently selected subsets, then:
        if (ssList.size() > 0 && ssList.get(0) != null)
        {
            selectedSubsets.clear();
            selectedSubsets.add(ssList.get(0));
            selectedSubsetIndices = new int[]{0};
            
            if (!(getStatus() == Status.INVALID)) setStatus(Status.WARNING);
        }
        else
        {
            selectedSubsets.clear();                
            selectedSubsetIndices = new int[]{};
            
            setStatus(Status.INVALID);
        }
    }
    
    public class FSDataType
    {
        public FSDataType(String _selFct, List<String> _selSs)
        {
            selFct = _selFct;
            selSs = _selSs;
        }
        String selFct;
        List<String> selSs;
    }
    
    
    /**
     *  creates the user data
     * @return  the created user data
     */
    @Override
    public Object createUserData()
    {
        return new FSDataType(selectedFunction, selectedSubsets);
    }

    /**
     * checks if user data can be created
     * @return empty message if everything ok, error message else
     */
    @Override
    public String checkUserData()
    {
        return "";
    }
    
}
