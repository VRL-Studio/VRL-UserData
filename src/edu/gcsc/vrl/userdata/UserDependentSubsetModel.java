package edu.gcsc.vrl.userdata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mbreit
 */
public class UserDependentSubsetModel extends UserDataModel
{
    private int nFct;
    private String[] selectedFunctions;
    private int[] selectedFunctionIndices;
    private List<String> selectedSubsets;
    private int[] selectedSubsetIndices;
    
    public UserDependentSubsetModel(int _nFct)
    {
        nFct = _nFct;
        selectedFunctions = new String[nFct];
        selectedFunctionIndices = new int[nFct];
        for (int i=0; i<nFct; i++)
        {
            selectedFunctions[i] = "";
            selectedFunctionIndices[i] = -1;
        }
        selectedSubsets = new ArrayList<String>();
        selectedSubsetIndices = new int[]{};
    }
    
    /**
     * @return number of functions to be selected
     */
    public int getNFct()
    {
        return nFct;
    }
    
    // inherited from UserDataModel
    /**
     * Not to be used.
     * Use getSelectedFunction() and getSelectedSubset() instead.
     * @return error
     */
    @Override
    public Object getData()
    {
        throw new RuntimeException("UserDependentSubsetModel: Call to getData() which is not allowed.\n");
    }
    
    /**
     * @return selected function names
     */
    public String[] getSelectedFunctions()
    {
        return selectedFunctions;
    }
     
    /**
     * @param arrayIndex index of function selector in JComboBox array    
     * @return  selected function names
     */
    public String getSelectedFunction(int arrayIndex)
    {
        return selectedFunctions[arrayIndex];
    }
            
    /**
     * @return selected subset name
     */
    public List<String> getSelectedSubsets()
    {
        return selectedSubsets;
    }
            
    /**
     * @return selected function indices
     */
    public int[] getSelectedFunctionIndices()
    {
        return selectedFunctionIndices;
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
         throw new RuntimeException("UserDependentSubsetModel: Call to setData() which is not allowed.\n");
    }

    
    /**
     * Set information about selected function.
     * @param fctNames   function names as array of string
     * @param fctIndices   function index in definition
     */
    public void setSelectedFunctions(String[] fctNames, int[] fctIndices)
    {
         selectedFunctions = fctNames;
         selectedFunctionIndices = fctIndices;
         
         if (fctNames == null) return;
         if (fctNames.length != nFct)
         {
             throw new RuntimeException("UserDependentSubsetModel: Number of selected functions "
                                        + "must be exactly "+nFct+", but is "+fctNames.length+".\n");
         }
         for (int i=0; i<nFct; i++)
             if (fctNames[i] == null || fctNames[i].equals("")) return;
         
         setStatus(Status.VALID);
    }
    
    
    /**
     * Set information about selected function.
     * @param arrayIndex index of function selector in JComboBox array
     * @param fctName    function name as string
     * @param fctIndex   index of fctName in definition
     */
    public void setSelectedFunction(int arrayIndex, String fctName, int fctIndex)
    {
         selectedFunctions[arrayIndex] = fctName;
         selectedFunctionIndices[arrayIndex] = fctIndex;
         
         if (fctName == null) return;
         
         for (int i=0; i<nFct; i++)
             if (selectedFunctions[i] == null || selectedFunctions[i].equals("")) return;
         
         setStatus(Status.VALID);
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
         else setStatus(Status.VALID);
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
            setSelectedFunctions(m.getSelectedFunctions(), m.getSelectedFunctionIndices());
            setSelectedSubsets(m.getSelectedSubsets(), m.getSelectedSubsetIndices());
            setStatus(m.getStatus());
        }
        else throw new RuntimeException("UserData could not be set from other UserDataModel.\n");
    }
    
    /**
     * Not to be used.
     * Use adjustFunctionData(), adjustSubsetData() instead.
     * @param data
     */
    @Override
    public void adjustData(Object data)
    {
         throw new RuntimeException("UserDependentSubsetModel: Call to adjustData() which is not allowed.\n");
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
        
        // check whether new function definitions still contain all previously
        // selected functions; if that is the case, do not change status
        boolean allFunctionsPresent = true;
        for (String selFct: selectedFunctions)
        {
            if (selFct.equals("")) continue;   // no sense looking for non-selected functions
            boolean fctPresent = false;
            for (String fct: fctList)
            {
                if (fct.equals(selFct))
                {
                    fctPresent = true;
                    break;
                }
            }
            if (!fctPresent)
            {
                allFunctionsPresent = false;
                break;
            }
        }
        
        if (allFunctionsPresent) return;
        
        // control reaches this code only if new function definition
        // does not contain all currently selected functions, then:
        // if possible: select the same indices as before and warn
        int maxSelInd = 0;
        for (int sfi: selectedFunctionIndices)
            if (maxSelInd < sfi) maxSelInd = sfi;
        
        if (fctList.size() > maxSelInd)
        {
            boolean allFctDefOK = true;
            for (int i=0; i<nFct; i++)
            {
                if (selectedFunctionIndices[i] < 0) continue;   // no sense looking for non-selected functions
                if (fctList.get(selectedFunctionIndices[i]) == null
                    || fctList.get(selectedFunctionIndices[i]).equals(""))
                {
                    allFctDefOK = false;
                    break;
                }
            }
            if (allFctDefOK)
            {
                for (int i=0; i<nFct; i++)
                    if (selectedFunctionIndices[i] >= 0) selectedFunctions[i] = fctList.get(selectedFunctionIndices[i]);
                if (getStatus() != Status.INVALID) setStatus(Status.WARNING);
                return;
            }
        }
        
        // if this also fails, then pick the first index for every selection
        if (!fctList.isEmpty() && fctList.get(0) != null && !fctList.get(0).equals(""))
        {
            for (int i=0; i<nFct; i++)
            {
                selectedFunctions[i] = fctList.get(0);
                selectedFunctionIndices[i] = 0;
            }
            return;
        }
    
        // if everything fails (no function definition): set invalid
        for (int i=0; i<nFct; i++)
        {
            selectedFunctions[i] = "";
            selectedFunctionIndices[i] = -1;
        }
        setStatus(Status.INVALID);
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
        public FSDataType(String[] _selFct, List<String> _selSs)
        {
            selFct = _selFct;
            selSs = _selSs;
        }
        public String[] selFct;
        public List<String> selSs;
    }
    
    
    /**
     *  creates the user data
     * @return  the created user data
     */
    @Override
    public Object createUserData()
    {
        return new FSDataType(selectedFunctions, selectedSubsets);
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
