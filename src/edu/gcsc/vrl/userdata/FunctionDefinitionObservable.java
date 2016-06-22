/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
package edu.gcsc.vrl.userdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        this.arrayIndexMap = new HashMap<Identifier, AtomicInteger>();
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
    public static class FctData implements Serializable
    {
        private static final long serialVersionUID = 1L;
    
        public FctData(String fn, List<String> ss)
        {
            fctName = fn;
            subsetList = new ArrayList<String>(ss);
        }
        public FctData()
        {
            this("", new ArrayList<String>());
        }
        
        public String getFctName() {return fctName;}
        public void setFctName(String fctName) {this.fctName = fctName;}

        public List<String> getSubsetList() {return subsetList;}
        public void setSubsetList(List<String> subsetList) {this.subsetList = subsetList;}
        
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
        public HashMap<Integer, FctData> data = new HashMap<Integer, FctData>();
    }
    
    
    /**
     * Identifier for tags. If several function / subset definition units exist,
     * they are distinguished by fct_tag, objectID and windowID.
     */
    private class Identifier
    {
        public Identifier(String fct_tag, int windowID)
        {
            this.fct_tag = fct_tag;
            this.windowID = windowID;
        }
        private final String fct_tag;
        private final int windowID;

        @Override
        public int hashCode()
        {
            int result = 31*fct_tag.hashCode();
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
            return (fct_tag.equals(rhs.fct_tag)) && (windowID == rhs.windowID);
        }
    }    
    
    // For every tag, this map holds the FctTagData associated; it contains a
    // list of all observers of that tag and the actual data associated with it.
    private transient Map<Identifier, FctTagData> fctTagMap;
    
    // The function definition is realized in an array of pairs
    // (function,subsets it is defined on), the array index makes access to a
    // specific pair possible.
    // This map stores the current number of arrayIndices for one function
    // definition array (perhaps, one single int would be enough, since only one
    // function definition array is to be expected).
    private transient Map<Identifier, AtomicInteger> arrayIndexMap;
   
    /**
     * Returns the FctTagData for a fct_tag. If create is set to true a new
     * fct_tag entry is created. If no fct_tag exists and create is set to false 
     * then null is returned.
     * 
     * @param fct_tag   fct_tag name
     * @param create    flag indicating if fct_tag should be created
     * @return          file fct_tag info
     */
    private synchronized FctTagData getTag(String fct_tag, int windowID, boolean create)
    {
        Identifier id = new Identifier(fct_tag, windowID);

        if (fctTagMap.containsKey(id)) return fctTagMap.get(id);

        if (create)
        {
            fctTagMap.put(id, new FctTagData());
            return getTag(fct_tag, windowID, false);
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
     * @param windowID  the window containing the object
     * @return          the FctDefType's position in the FctDef array
     */
    public synchronized int receiveArrayIndex(String fct_tag, int windowID)
    {
        Identifier id = new Identifier(fct_tag, windowID);
        if (!arrayIndexMap.containsKey(id)) arrayIndexMap.put(id, new AtomicInteger(0));
        if (!fctTagMap.containsKey(id)) fctTagMap.put(id, new FctTagData());
        
        // create a fctTagMap entry
        Integer index = arrayIndexMap.get(id).getAndIncrement();        
        fctTagMap.get(id).data.put(index, new FctData());
        
        //notifyObservers(fct_tag, windowID);
        
        return index;
    }

    /**
     * Removes the array index of specified by its owner, the FctDefType.
     * This must be called by the FctDefType, when it is removed from view.
     * @param fct_tag       function tag the FctDefType belongs to
     * @param windowID      the window containing the object
     * @param arrayIndex    the index to be revoked
     */
    public synchronized void revokeArrayIndex(String fct_tag, int windowID, int arrayIndex)
    {
        Identifier id = new Identifier(fct_tag, windowID);
        
        fctTagMap.get(id).data.remove(arrayIndex);
        
        notifyObservers(fct_tag, windowID);
    }
    
    
    /**
     * Add an observer to this Observable. The observer listens to a fct_tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs           the observer to be added
     * @param fct_tag       the fct_tag identifying the observable
     * @param windowID      the window containing the object
     * @param requestUpdate whether observer is to be updated after adding
     */
    public synchronized void addObserver(FunctionDefinitionObserver obs, String fct_tag, int windowID, boolean requestUpdate)
    {
        getTag(fct_tag, windowID, true).observers.add(obs);
        
        if (requestUpdate)
            obs.update(getTag(fct_tag, windowID, false).data.values(), fct_tag, windowID);
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
     * Removes all observers of a fct_tag from this Observable. 
     * 
     * @param fct_tag   the fct_tag
     * @param windowID  the window containing the object
     */
    public synchronized void deleteObservers(String fct_tag, int windowID)
    {
        Identifier id = new Identifier(fct_tag, windowID);
        if (fctTagMap.containsKey(id)) fctTagMap.get(id).observers.clear();
    }

    /**
     * Notifies all observers of a fct_tag about the currently given data.
     * 
     * @param fct_tag   the fct_tag
     * @param windowID  the window containing the object
     */
    public synchronized void notifyObservers(String fct_tag, int windowID)
    {
        // get data for fct_tag
        FctTagData fctTagData = getTag(fct_tag, windowID, false);

        // if no such fct_tag present, return (i.e. no observer)
        if (fctTagData != null)
        {
            // notify observers of this fct_tag
            for (FunctionDefinitionObserver obs : fctTagData.observers)
                obs.update(fctTagData.data.values(), fct_tag, windowID);
        }
    }

    /**
     * Sets the function definition data for a fct_tag.
     * @param data          the function / subset data
     * @param fct_tag       the fct_tag
     * @param windowID      the window containing the object
     * @param arrayIndex    the array index of the FunctionDefinition calling the method
     */
    public synchronized void setFunctionDefinitions(FctData data, String fct_tag, int windowID, int arrayIndex)
    {
        // set the data
        FctTagData fctTagData = getTag(fct_tag, windowID, true);
        fctTagData.data.put(arrayIndex, data);
        
        // notify the obersvers of this fct_tag
        notifyObservers(fct_tag, windowID);
    }

    /**
     * Sets that a fct_tag has an invalid file.
     * 
     * @param fct_tag   the fct_tag
     * @param windowID  the window containing the object
     * @param index     index in the function definition array
     */
    public synchronized void setInvalid(String fct_tag, int windowID, int index)
    {
        FctTagData fctTagData = getTag(fct_tag, windowID, true);

        // remove fct def for this index
        fctTagData.data.put(index, null);

        // notify the observers of this fct_tag
        notifyObservers(fct_tag, windowID);
    }
    
    public List<String> requestFunctions(String fct_tag, int windowID)
    {
        // get data for fct_tag
        FctTagData fctTagData = getTag(fct_tag, windowID, false);

        // if no such fct_tag present, return empty list
        if (fctTagData == null) return new ArrayList<String>();
        
        // construct function names list
        ArrayList<String> fcts = new ArrayList<String>();
        for (FctData fctData: fctTagData.data.values())
            fcts.add(fctData.fctName);
        
        return fcts;
    }
    
    public List<String> requestSubsetsForFunction(String fctName, String fct_tag, int windowID)
    {
        // get data for fct_tag
        FctTagData fctTagData = getTag(fct_tag, windowID, false);

        // if no such fct_tag present, return empty list
        if (fctTagData == null) return new ArrayList<String>();
        
        Iterator<FctData> it = fctTagData.data.values().iterator();
        while(it.hasNext())
        {
            FctData fd = it.next();
            if (fd.getFctName().equals(fctName)) return fd.getSubsetList();
        }
        
        // if no such function exists
        return new ArrayList<String>();
    }
}
