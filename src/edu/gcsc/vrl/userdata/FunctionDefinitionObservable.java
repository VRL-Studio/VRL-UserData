package edu.gcsc.vrl.userdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FunctionDefinitionObservable.
 * This class provides an interface for the definition of functions on specific
 * subsets of a geometry. Elements in need of the data defined here can attach 
 * themselves to this classas an observer and will be notified whenever there is
 * a change in the data. The data is identified by the fct_tag.
 * 
 * @date 2013-12-12
 * @author mbreit (strongly based on LoadUGXFileObservable by avogel)
 */
public class FunctionDefinitionObservable
{
    /**
     * private constructor for singleton
     */
    private FunctionDefinitionObservable()
    {
        this.fctTagMap = new HashMap<Identifier, FctTagData>();
    }
    private static volatile FunctionDefinitionObservable instance = null;

    /**
     * return of singleton instance
     * 
     * @return      instance of singleton
     */
    public static FunctionDefinitionObservable getInstance()
    {
        if (instance == null)
        {
            synchronized (FunctionDefinitionObservable.class)
            {
                if (instance == null) instance = new FunctionDefinitionObservable();
            }
        }
        return instance;
    }
    
    /**
     * Data Container class. Holds the function data
     * (i.e. functions and subsets they are defined on).
     */
    public static class FctData
    {
        public FctData(String fn, List<String> ss)
        {
            fctName = fn;
            subsetList = new ArrayList<String>(ss);
        }
        private FctData()
        {
            this("", new ArrayList<String>());
        }
        
        public String fctName = null;
        public List<String> subsetList = null;
    }
    
    /**
     * Tag Class. Stored are all observers listening to the
     * fct_tag and the function definition data associated with it.
     */
    private class FctTagData
    {
        public Collection<FunctionDefinitionObserver> observers = new HashSet<FunctionDefinitionObserver>();
        public List<FctData> data = new ArrayList<FctData>();
    }
    
    
    /**
     * Identifier for tags. If several function / subset definition units exist,
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
    
    // For every tag, this map holds the FctTagData associated; it contains a
    // list of all observers of that tag and the actual data associated with it.
    private transient Map<Identifier, FctTagData> fctTagMap = new HashMap<Identifier, FctTagData>();
    
    // The function definition is realized in an array of pairs
    // (function,subsets it is defined on), the array index makes access to a
    // specific pair possible.
    // This map stores the current number of arrayIndices for one function
    // definition array (perhaps, one single int would be enough, since only one
    // function definition array is to be expected).
    private transient Map<Identifier, AtomicInteger> arrayIndexMap = new HashMap<Identifier, AtomicInteger>();
   
    /**
     * Returns the FctTagData for a fct_tag. If create is set to true a new
     * fct_tag entry is created. If no fct_tag exists and create is set to false 
     * then null is returned.
     * 
     * @param fct_tag   ugx_tag name
     * @param create    flag indicating if fct_tag should be created
     * @return          file fct_tag info
     */
    private synchronized FctTagData getTag(String fct_tag, Object object, int windowID, boolean create)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);

        if (fctTagMap.containsKey(id)) return fctTagMap.get(id);

        if (create)
        {
            fctTagMap.put(id, new FctTagData());
            return getTag(fct_tag, object, windowID, false);
        }

        return null;
    }

    /**
     * Produces a new array index for an instance of FunctionDefinitionType to
     * be observed. The FunctionDefinitionType objects are arranged in an array,
     * but the single objects do not have information about the position in this
     * array, this is provided here.
     * 
     * @param fct_tag   the function tag the FctDefType belongs to
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     * @return          the FctDefType's position in the FctDef array
     */
    public synchronized int receiveArrayIndex(String fct_tag, Object object, int windowID)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        if (!arrayIndexMap.containsKey(id)) arrayIndexMap.put(id, new AtomicInteger(0));
        if (!fctTagMap.containsKey(id)) fctTagMap.put(id, new FctTagData());
        fctTagMap.get(id).data.add(new FctData("-- no fct def --",new ArrayList<String>()));
        
        notifyObservers(fct_tag, object, windowID);
        
        return arrayIndexMap.get(id).getAndIncrement();
    }

    /**
     * Removes the array index of specified by its owner, the FctDefType.
     * This must be called by the FctDefType, when it is removed from view.
     * @param fct_tag       function tag the FctDefType belongs to
     * @param object        the object bound to the observable
     * @param windowID      the window containing the object
     * @param arrayIndex    the index to be revoked
     */
    public synchronized void revokeArrayIndex(String fct_tag, Object object, int windowID, int arrayIndex)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        Integer a = null;
        
        arrayIndexMap.get(id).decrementAndGet();
        fctTagMap.get(id).data.remove(arrayIndex);
        
        notifyObservers(fct_tag, object, windowID);
    }
    
    
    /**
     * Add an observer to this Observable. The observer listens to a ugx_tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs       the observer to be added
     * @param fct_tag   the fct_tag identifying the observable
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     */
    public synchronized void addObserver(FunctionDefinitionObserver obs, String fct_tag, Object object, int windowID)
    {
        getTag(fct_tag, object, windowID, true).observers.add(obs);
        obs.update(getTag(fct_tag, object, windowID, false).data, fct_tag, object, windowID);
    }

    
    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     */
    public synchronized void deleteObserver(FunctionDefinitionObserver obs)
    {
        for (Map.Entry<Identifier, FctTagData> entry : fctTagMap.entrySet())
            entry.getValue().observers.remove(obs);
    }

    /**
     * Removes all observer of a ugx_tag from this Observable. 
     * 
     * @param fct_tag   the fct_tag
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     */
    public synchronized void deleteObservers(String fct_tag, Object object, int windowID)
    {
        Identifier id = new Identifier(fct_tag, object, windowID);
        if (fctTagMap.containsKey(id)) fctTagMap.get(id).observers.clear();
    }

    /**
     * Notifies all observers of a fct_tag about the currently given data.
     * 
     * @param fct_tag   the fct_tag
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     */
    public synchronized void notifyObservers(String fct_tag, Object object, int windowID)
    {
        // get data for fct_tag
        FctTagData fctTagData = getTag(fct_tag, object, windowID, false);

        // if no such fct_tag present, return (i.e. no observer)
        if (fctTagData != null)
        {
            // notify observers of this fct_tag
            for (FunctionDefinitionObserver obs : fctTagData.observers)
                obs.update(fctTagData.data, fct_tag, object, windowID);
        }
    }

    /**
     * Sets the function definition data for a fct_tag.
     * @param data          the function / subset data
     * @param fct_tag       the fct_tag
     * @param object        the object bound to the observable
     * @param windowID      the window containing the object
     * @param arrayIndex    the array index of the FunctionDefinition calling the method
     */
    public synchronized void setFunctionDefinitions(FctData data, String fct_tag, Object object, int windowID, int arrayIndex)
    {
        // set the data
        FctTagData fctTagData = getTag(fct_tag, object, windowID, true);
        fctTagData.data.set(arrayIndex, data);
        
        // notify the obersvers of this fct_tag
        notifyObservers(fct_tag, object, windowID);
    }

    /**
     * Sets that a ugx_tag has an invalid file.
     * 
     * @param fct_tag   the fct_tag
     * @param object    the object bound to the observable
     * @param windowID  the window containing the object
     */
    public synchronized void setInvalidData(String fct_tag, Object object, int windowID)
    {
        FctTagData fctTagData = getTag(fct_tag, object, windowID, true);

        //  set to new (empty) data
        fctTagData.data = null;

        // notify the observers of this fct_tag
        notifyObservers(fct_tag, object, windowID);
    }
    
    public List<String> requestSubsetsForFunction(int fctIndex, String fct_tag, Object object, int windowID)
    {
       // get data for fct_tag
        FctTagData fctTagData = getTag(fct_tag, object, windowID, false);

        // if no such fct_tag present, return empty list
        if (fctTagData == null) return new ArrayList<String>();
        
        return fctTagData.data.get(fctIndex).subsetList;
    }
}
