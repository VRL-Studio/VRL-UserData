package edu.gcsc.vrl.userdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author mbreit
 */
public class FunctionSubsetCoordinator implements FunctionDefinitionObserver
{
    /**
     * private constructor for singleton
     */
    private FunctionSubsetCoordinator()
    {
        this.couplingMap = new HashMap<Identifier, List<FSCoupling> >();
        this.indexMap = new HashMap<Identifier, AtomicInteger>();
    }
    private static volatile FunctionSubsetCoordinator instance = null;

    /**
     * return of singleton instance
     * 
     * @return      instance of singleton
     */
    public static FunctionSubsetCoordinator getInstance()
    {
        if (instance == null)
        {
            synchronized (FunctionSubsetCoordinator.class)
            {
                if (instance == null) instance = new FunctionSubsetCoordinator();
            }
        }
        return instance;
    }
    
    /**
     * Data Container class. Holds the coupling between a pair of
     * function and subset displayers.
     */
    private class FSCoupling
    {
        public FSCoupling()
        {
            fObs = null;
            sObs = null;
        }
        
        public FunctionSubsetCoordinatorFunctionObserver fObs;
        public FunctionSubsetCoordinatorSubsetObserver sObs;
    }
    
    /**
     * Identifier for tags. If several function / subset displayer arrays exist
     * (e.g. multiple windows of the same plugin),
     * they are distinguished by fct_tag, objectID and windowID.
     */
    private class Identifier
    {
        public Identifier(String fct_tag, Object object, int windowID)
        {
            this.fct_tag = fct_tag;
            this.object = object;
            this.windowID = windowID;
        }
        private final String fct_tag;
        private final Object object;
        private final int windowID;

        @Override
        public int hashCode()
        {
            int result = 31*31*fct_tag.hashCode();
            result += 31*object.hashCode();
            result += windowID;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null) return false;
            if (obj == this) return true;
            if (obj.getClass() != getClass()) return false;
            
            Identifier rhs = (Identifier) obj;
            return (fct_tag.equals(rhs.fct_tag)) && (object == rhs.object) && (windowID == rhs.windowID);
        }
    }    
    
    // For every tag, this map holds the FSCoupling data associated.
    private final transient Map<Identifier, List<FSCoupling> > couplingMap;
    
    // The function/subset selection is realized in an array of pairs
    // (function,subsets), the index for the pair makes access to a
    // specific pair possible and is requested at this object via
    // requestArrayIndex() and by the displayer.
    // The map holds the current number of distributed indices per identifier.
    private final transient Map<Identifier, AtomicInteger> indexMap;
   
   
    /**
     * Produces a new index for a function/subset displayer instance.
     * 
     * @param fObs      the requesting functionObserver
     * @param sObs      the requesting subsetObserver
     * @param fct_tag   the function tag the function dsiplayer belongs to
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     * @return          the FctDefType's position in the FctDef array
     */
    public synchronized int requestArrayIndex(FunctionSubsetCoordinatorFunctionObserver fObs,
                                              FunctionSubsetCoordinatorSubsetObserver sObs,
                                              String fct_tag, Object object, int windowID)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        
        if (!indexMap.containsKey(id)) indexMap.put(id, new AtomicInteger(0));
        if (!couplingMap.containsKey(id)) couplingMap.put(id, new ArrayList<FSCoupling>());
        
        // if a coupling entry does not exist yet, (because no subsetObserver
        // has requested it) create it first
        int index = indexMap.get(id).getAndIncrement();
        if (couplingMap.get(id).size() <= index)
            couplingMap.get(id).add(new FSCoupling());
        
        // error if current fctIndex is still too large (should not happen)
        if (couplingMap.get(id).size() <= index)
            throw new RuntimeException("FunctionSubsetCoordinator: requested couplingMap index is out of range.");
        
        couplingMap.get(id).get(index).fObs = fObs;
        couplingMap.get(id).get(index).sObs = sObs;
        
        // register at FunctionDefinitionObservable when both fct and ss index are distributed
        FunctionDefinitionObservable.getInstance().addObserver(this, fct_tag, object, windowID);
        
        return index;
    }
    
    
    /**
     * Removes the arrayIndex specified by its owner, the Fct/Ss Observer.
     * This must be called by the Observer when it is removed from view.
     * The Observers of one fct_tag must be removed in reverse order of their
     * addition, otherwise there will be index conflicts!
     * @param fct_tag       function tag the FctDefType belongs to
     * @param object        the object bound to the observable
     * @param windowID      the window containing the object
     * @param arrayIndex    the index to be revoked
     */
    public synchronized void revokeArrayIndex(String fct_tag, Object object, int windowID, int arrayIndex)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        indexMap.get(id).decrementAndGet();
        
        // remove from couplingMap iff corresponding subsetObserver has already
        // revoked its index
        FSCoupling fsc = couplingMap.get(id).get(arrayIndex);
        if (fsc.sObs == null)
        {
            couplingMap.get(id).remove(arrayIndex);
            FunctionDefinitionObservable.getInstance().deleteObserver(this);
        }
        else fsc.fObs = null;
    }
    
    
    
    /**
     * Notifies all observers of a fct_tag about the currently given data.
     * 
     * @param couplingIndex     the index of the function/subset pair in the coupling data
     * @param fct_tag           the fct_tag
     * @param object            the object bound to the observable
     * @param windowID          the window containing the object
     */
    public synchronized void notifySubsetObserver(int couplingIndex, String fct_tag, Object object, int windowID)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        int[] selFctIndices = couplingMap.get(id).get(couplingIndex).fObs.getSelectedFunctionIndices();
        
        if (selFctIndices == null)
            throw new RuntimeException("FunctionSubsetCoordinator: selFctIndices is null!");
        
        if (selFctIndices.length < 1)
            throw new RuntimeException("FunctionSubsetCoordinator: selFctIndices has no entries!");
  
        List<String> subsets;
        if (selFctIndices[0] >= 0)
        {
            // iteratively construct subset list by removing subsets not contained
            // in the subsets list of a selected function
            subsets = new ArrayList<String>(FunctionDefinitionObservable.getInstance()
                    .requestSubsetsForFunction(selFctIndices[0], fct_tag, object, windowID));
/*System.out.println(">>> subsets: --------");
for (String s: subsets) System.out.println(">>> "+s);
System.out.println("");*/
            for (int sfi=1; sfi < selFctIndices.length; sfi++)
            {
                List<String> currSsl;
                if (selFctIndices[sfi] >= 0)
                {
                    currSsl = new ArrayList<String>(FunctionDefinitionObservable.getInstance()
                        .requestSubsetsForFunction(selFctIndices[sfi], fct_tag, object, windowID));
                }
                else  currSsl = new ArrayList<String>();
                
/*System.out.println(">>> currSsl: --------");
for (String s: currSsl) System.out.println(">>> "+s);
System.out.println("");*/
                int i = 0;
                while (i < subsets.size())
                {
                    boolean found = false;
                    for (int j=0; j<currSsl.size(); j++)
                    {
                        if (subsets.get(i).equals(currSsl.get(j)))
                        {
/*System.out.println(">>>> found item '"+subsets.get(i)+"'");*/
                            found = true;
                            i++;
                            currSsl.remove(j);
                            break;
                        }
                    }
                    if (!found)
                    {
/*System.out.println(">>>> item '"+subsets.get(i)+"' not found, removing");*/
                        subsets.remove(i);
                    }
                }
            }
/*System.out.println(">>> subsets: --------");
for (String s: subsets) System.out.println(">>> "+s);
System.out.println("");*/
        }
        // empty list if no function selected
        else subsets = new ArrayList<String>();
        
        couplingMap.get(id).get(couplingIndex).sObs.updateSubsets(subsets);
    }

    // implementation of FunctionDefinitionObserver
    /**
     * Updates the observer with the new data.
     * @param data      new data to update with.
     * @param fct_tag   fct_tag the data is associated to
     * @param object    object the data is associated to
     * @param windowID  windowID the data is associated to
     */
    @Override
    public void update(List<FunctionDefinitionObservable.FctData> data,
                       String fct_tag, Object object, int windowID)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        
        // construct function names list and notify fctObservers
        ArrayList<String> fcts = new ArrayList<String>();
        for (FunctionDefinitionObservable.FctData fctData: data)
            fcts.add(fctData.fctName);
        
        if (indexMap.containsKey(id))
        {
            for (int i = 0; i < indexMap.get(id).get(); i++)
            {
                couplingMap.get(id).get(i).fObs.updateFunctions(fcts);
                //notifySubsetObserver(i, fct_tag, object, windowID);   // should be done in observer
            }
        }
        
        
    }
}
