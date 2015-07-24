package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.lang.VLangUtils;
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
        for (int i=0; i<nFct; i++)
            selectedFunctions[i] = "";
        
        String[] selectedSubsets = new String[]{};
        int[] selectedSubsetIndices = new int[]{};
        
        this.selections = new FSDataType(selectedFunctions,
                              selectedSubsets, selectedSubsetIndices);
        this.nFct = nFct;
    }
    
   
    public static class FSDataType implements Serializable
    {
        private static final long serialVersionUID = 1L;
    
        // make class conform to javabeans standard (necessary for xml export)
        public FSDataType() {}
        
        public FSDataType(String[] _selFct, String[] _selSs, int[] _selSsInd)
        {
            selFct = _selFct;
            selSs = _selSs;
            selSsInd = _selSsInd;
        }
        
        String[] selFct;
        String[] selSs;
        int[] selSsInd;

        public String[] getSelFct() {return selFct;}
        public void setSelFct(String[] selFct) {this.selFct = selFct;}

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
     */
    public void setSelectedFunctions(String[] fctNames)
    {
         selections.setSelFct(fctNames);
         
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
     */
    public void setSelectedFunction(int arrayIndex, String fctName)
    {
         selections.getSelFct()[arrayIndex] = fctName;
         
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
        // selected functions; if that is the case, adjust selected indices and
        // do not change status
        boolean allFunctionsPresent = true;
        for (String selFct : selections.getSelFct())
        {
            boolean fctPresent = false;
            for (String fct : fctList)
            {
                if (fct.equals(selFct))
                {
                    fctPresent = true;
                    break;
                }
            }
            if (!fctPresent)
                allFunctionsPresent = false;
        }
            
        if (allFunctionsPresent) setStatus(Status.VALID);
        else setStatus(Status.INVALID);
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
        int[] ssiList = new int[selections.getSelSs().length];
        int selssi = 0;
        for (String sel: selections.getSelSs())
        {
            boolean found = false;
            String ss;
            for (int ssi=0; ssi < ssList.size(); ssi++)
            {
                ss = ssList.get(ssi);
                if (ss.equals(sel))
                {
                    ssiList[selssi] = ssi;
                    found = true;
                    selssi++;
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
        // select whatever has been found
        if (ssList.size() > 0 && ssList.get(0) != null)
        {
            //selections.setSelSs(new String[]{ssList.get(0)});
            int[] newSelSsIndices = new int[selssi];
            System.arraycopy(ssiList, 0, newSelSsIndices, 0, selssi);
            
            selections.setSelSsInd(newSelSsIndices);
            
            if (!(getStatus() == Status.INVALID)) setStatus(Status.WARNING);
        }
        // or select nothing at all
        else
        {
            
            //selections.setSelSs(new String[]{});                
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

    @Override
    public String getModelAsCode()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("new ")
          .append(FSDataType.class.getName())
          .append("(");
          
        // String[] _selFct
        sb.append("[");
        String selFct_string = "";
        for (String selFct : selections.selFct)
            selFct_string += ",\"" + VLangUtils.addEscapesToCode(selFct) + "\"";
        if (selFct_string.length() > 0) selFct_string = selFct_string.substring(1);
        sb.append(selFct_string);
        sb.append("] as String[], ");
        
        // String[] _selSs
        sb.append("[");
        String selSs_string = "";
        for (String selSs : selections.selSs)
            selSs_string += ",\"" + VLangUtils.addEscapesToCode(selSs) + "\"";
        if (selSs_string.length() > 0) selSs_string = selSs_string.substring(1);
        sb.append(selSs_string);
        sb.append("] as String[], ");
        
        // int[] _selSsInd
        sb.append("[");
        String selSsi_string = "";
        for (int selSs : selections.selSsInd)
            selSsi_string += "," + selSs;
        if (selSsi_string.length() > 0) selSsi_string = selSsi_string.substring(1);
        sb.append(selSsi_string);
        sb.append("] as int[]");
        
        sb.append(")");
        return sb.toString();
    }
    
}
