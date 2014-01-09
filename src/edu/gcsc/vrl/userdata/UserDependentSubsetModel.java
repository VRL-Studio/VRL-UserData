package edu.gcsc.vrl.userdata;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mbreit
 */
public class UserDependentSubsetModel extends UserDataModel
{
    
    public UserDependentSubsetModel()
    {
        //
    }
    
    public UserDependentSubsetModel(int nFct)
    {
        String[] selectedFunctions = new String[nFct];
        int[] selectedFunctionIndices = new int[nFct];
        for (int i=0; i<nFct; i++)
        {
            selectedFunctions[i] = "";
            selectedFunctionIndices[i] = -1;
        }
        String[] selectedSubsets = new String[]{};
        int[] selectedSubsetIndices = new int[]{};
        
        this.selections = new FSDataType(selectedFunctions, selectedFunctionIndices,
                              selectedSubsets, selectedSubsetIndices);
        this.nFct = nFct;
    }
    
   
    public static class FSDataType implements Serializable
    {
        private static final long serialVersionUID = 1L;
    
        // make class conform to javabeans standard (necessary for xml export)
        public FSDataType() {}
        
        public FSDataType(String[] _selFct, int[] _selFctInd,
                          String[] _selSs, int[] _selSsInd)
        {
            selFct = _selFct;
            selFctInd = _selFctInd;
            selSs = _selSs;
            selSsInd = _selSsInd;
        }
        
        String[] selFct;
        int[] selFctInd;
        String[] selSs;
        int[] selSsInd;

        public String[] getSelFct() {return selFct;}
        public void setSelFct(String[] selFct) {this.selFct = selFct;}

        public int[] getSelFctInd() {return selFctInd;}
        public void setSelFctInd(int[] selFctInd) {this.selFctInd = selFctInd;}

        public String[] getSelSs() {return selSs;}
        public void setSelSs(String[] selSs) {this.selSs = selSs;}

        public int[] getSelSsInd() {return selSsInd;}
        public void setSelSsInd(int[] selSsInd) {this.selSsInd = selSsInd;}
    }
    
    int nFct;
    UserDependentSubsetModel.FSDataType selections;
    
    
    public int getnFct() {return nFct;}
    public void setnFct(int nFct) {this.nFct = nFct;}
    
    public FSDataType getSelections() {return selections;}
    public void setSelections(FSDataType selections) {this.selections = selections;}
    
    
    // inherited from UserDataModel
    /**
     * @return selected functions and subsets
     */
    @Override
    public Object getData() {return selections;}
     
    /**
     * Set selected functions and subsets.
     * @param newData
     */
    @Override
    public void setData(Object newData)
    {
        if (newData instanceof FSDataType)
        {
            this.selections = (FSDataType) newData;
            this.nFct = selections.getSelFct().length;
        }
    }
    
    /**
     * @return  selected function names
     */
    public String[] getSelectedFunctions()
    {
        return selections.getSelFct();
    }
    
    /**
     * @return  selected function indices
     */
    public int[] getSelectedFunctionIndices()
    {
        return selections.getSelFctInd();
    }
    
    
    /**
     * @param arrayIndex index of function selector in JComboBox array    
     * @return  selected function name at arrayIndex
     */
    public String getSelectedFunction(int arrayIndex)
    {
        return selections.getSelFct()[arrayIndex];
    }
    
    
    /**
     * Set information about selected function.
     * @param fctNames   function names as array of string
     * @param fctIndices   function index in definition
     */
    public void setSelectedFunctions(String[] fctNames, int[] fctIndices)
    {
         selections.setSelFct(fctNames);
         selections.setSelFctInd(fctIndices);
         
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
         selections.getSelFct()[arrayIndex] = fctName;
         selections.getSelFctInd()[arrayIndex] = fctIndex;
         
         if (fctName == null) return;
         
         for (int i=0; i<nFct; i++)
             if (selections.getSelFct()[i] == null || selections.getSelFct()[i].equals("")) return;
         
         setStatus(Status.VALID);
    }
    
    /**
    * @return selected subset name
    */
    public String[] getSelectedSubsets()
    {
        return selections.getSelSs();
    }
    
    /**
    * @return selected subset indices
    */
    public int[] getSelectedSubsetIndices()
    {
        return selections.getSelSsInd();
    }


    /**
     * Set information about selected subset.
     * @param ssNames       function names as string
     * @param ssIndices     function index in definition   
     */
    public void setSelectedSubsets(String[] ssNames, int[] ssIndices)
    {
         selections.setSelSs(ssNames);
         selections.setSelSsInd(ssIndices);
         
         if (ssNames == null || ssNames.length == 0) setStatus(Status.INVALID);
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
            this.selections = m.selections;
            this.nFct = selections.getSelFct().length;
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
     * Adjusts function selections according to given functions.
     * 
     * @param fctList     new fct selections
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
        for (String selFct: selections.getSelFct())
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
        for (int sfi: selections.getSelFctInd())
            if (maxSelInd < sfi) maxSelInd = sfi;
        
        if (fctList.size() > maxSelInd)
        {
            boolean allFctDefOK = true;
            for (int i=0; i<nFct; i++)
            {
                if (selections.getSelFctInd()[i] < 0) continue;   // no sense looking for non-selected functions
                if (fctList.get(selections.getSelFctInd()[i]) == null
                    || fctList.get(selections.getSelFctInd()[i]).equals(""))
                {
                    allFctDefOK = false;
                    break;
                }
            }
            if (allFctDefOK)
            {
                for (int i=0; i<nFct; i++)
                    if (selections.getSelFctInd()[i] >= 0) selections.getSelFct()[i] = fctList.get(selections.getSelFctInd()[i]);
                if (getStatus() != Status.INVALID) setStatus(Status.WARNING);
                return;
            }
        }
        
        // if this also fails, then pick the first index for every selection
        if (!fctList.isEmpty() && fctList.get(0) != null && !fctList.get(0).equals(""))
        {
            for (int i=0; i<nFct; i++)
            {
                selections.getSelFct()[i] = fctList.get(0);
                selections.getSelFctInd()[i] = 0;
            }
            return;
        }
    
        // if everything fails (no function definition): set invalid
        for (int i=0; i<nFct; i++)
        {
            selections.getSelFct()[i] = "";
            selections.getSelFctInd()[i] = -1;
        }
        setStatus(Status.INVALID);
    }

    
    
    /**
     * Adjusts subset selections according to given subsets.
     * 
     * @param ssList     new file selections
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
        int[] ssiList = selections.getSelSsInd();
        int selssi = -1;
        for (String sel: selections.getSelSs())
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
            setSelectedSubsets(selections.getSelSs(), ssiList);
            return;
        }

        // control reaches this code only if new subset definition
        // does not contain currently selected subsets, then:
        if (ssList.size() > 0 && ssList.get(0) != null)
        {
            selections.setSelSs(new String[]{ssList.get(0)});
            selections.setSelSsInd(new int[]{0});
            
            if (!(getStatus() == Status.INVALID)) setStatus(Status.WARNING);
        }
        else
        {
            selections.setSelSs(new String[]{});                
            selections.setSelSsInd(new int[]{});
            
            setStatus(Status.INVALID);
        }
    }
    
    
    /**
     *  creates the user selections
     * @return  the created user selections
     */
    @Override
    public Object createUserData()
    {
        return selections;
    }

    /**
     * checks if user selections can be created
     * @return empty message if everything ok, error message else
     */
    @Override
    public String checkUserData()
    {
        return "";
    }
    
}
