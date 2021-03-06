/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012–2016 Goethe Universität Frankfurt am Main, Germany
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

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.HashSet;

/**
 * Stores the data info to a ugx file. Several files are possible and destinguished
 * via ugx_tags. 
 * Implementation is via a Singleton and the Observer Pattern. Observer can 
 * register at this Observable using a ugx_tag and will be notified once the data 
 * info associated with a ugx_tag has changed.
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
public class LoadUGXFileObservable {

    /**
     * private constructor for singleton
     */
    private LoadUGXFileObservable() {
    }
    private static volatile LoadUGXFileObservable instance = null;

    /**
     * return of singleton instance
     * 
     * @return      instance of singleton
     */
    public static LoadUGXFileObservable getInstance() {
        if (instance == null) {
            synchronized (LoadUGXFileObservable.class) {
                if (instance == null) {
                    instance = new LoadUGXFileObservable();
                }
            }
        }
        return instance;
    }

    /**
     * Info class for a ugx_tag. Stored are all observer listening to the ugx_tag and 
     * the ugx file data associated with a ugx_tag.
     */
    private class UGXFileTag {

        public Collection<LoadUGXFileObserver> observers = new HashSet<LoadUGXFileObserver>();
        public UGXFileInfo data = null;
    }

    /**
     * Info for global ugx_tags, i.e. ugx_tags that do listen to all ugx_tags with a name,
 not depending on the objectID. The actual data is not stored here, but at 
 the concrete ugx_tag with objectID.
     */
    private class UGXFileGlobalTag {

        public Collection<LoadUGXFileObserver> observers = new HashSet<LoadUGXFileObserver>();
    }

    /**
     * Identifier for grid loader strings. If several loaders exist, they are
 destinguished by ugx_tag, objectIDID and windowID
     */
    private class Identifier {

        public Identifier(String ugx_tag, int objectID, int windowID) {
            this.ugx_tag = ugx_tag;
            this.objectID = objectID;
            this.windowID = windowID;
        }
        private String ugx_tag;
        private int objectID;
        private int windowID;

        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + ugx_tag.hashCode();
            result = 37 * result + windowID;
            result = 37 * result + objectID;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }

            Identifier rhs = (Identifier) obj;

            return (ugx_tag.equals(rhs.ugx_tag)) && (objectID == rhs.objectID) && (windowID == rhs.windowID);
        }
    }
    /**
     * Map storing data and observers associated with a ugx_tag
     */
    private transient Map<Identifier, UGXFileTag> ugx_tags = new HashMap<Identifier, UGXFileTag>();
    private transient Map<String, UGXFileGlobalTag> globalTags = new HashMap<String, UGXFileGlobalTag>();

    /**
     * returns the file ugx_tag info for a ugx_tag. If create is set to true a new ugx_tag
     * entry is create, else not. If no ugx_tag exists and create is set to false 
     * null is returned.
     * 
     * @param ugx_tag       ugx_tag name
     * @param create    flag indicating if ugx_tag should be created if it does not exists
     * @return          file ugx_tag info
     */
    private synchronized UGXFileTag getTag(String ugx_tag, int objectID, int windowID, boolean create) {
        Identifier id = new Identifier(ugx_tag, objectID, windowID);

        if (ugx_tags.containsKey(id)) {
            return ugx_tags.get(id);
        }

        if (create) {
            ugx_tags.put(id, new UGXFileTag());
            return getTag(ugx_tag, objectID, windowID, false);
        }

        return null;
    }

    /**
     * returns the file ugx_tag info for a ugx_tag. If create is set to true a new ugx_tag
     * entry is create, else not. If no ugx_tag exists and create is set to false 
     * null is returned.
     * 
     * @param ugx_tag       ugx_tag name
     * @param create    flag indicating if ugx_tag should be created if it does not exists
     * @return          file ugx_tag info
     */
    private synchronized UGXFileGlobalTag getGlobalTag(String ugx_tag, boolean create) {
        if (globalTags.containsKey(ugx_tag)) {
            return globalTags.get(ugx_tag);
        }

        if (create) {
            globalTags.put(ugx_tag, new UGXFileGlobalTag());
            return getGlobalTag(ugx_tag, false);
        }

        return null;
    }

    /**
     * Add an observer to this Observable. The observer listens to a ugx_tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs       the observer to add
     * @param ugx_tag       the ugx_tag
     */
    public synchronized void addObserver(LoadUGXFileObserver obs, String ugx_tag, int objectID, int windowID) {
        getTag(ugx_tag, objectID, windowID, true).observers.add(obs);
        obs.update(getTag(ugx_tag, objectID, windowID, false).data);
    }

    /**
     * Add an observer to this Observable. The observer listens to a ugx_tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs       the observer to add
     * @param ugx_tag       the ugx_tag
     */
    public synchronized void addObserver(LoadUGXFileObserver obs, String ugx_tag) {
        getGlobalTag(ugx_tag, true).observers.add(obs);

        for (Map.Entry<Identifier, UGXFileTag> entry : ugx_tags.entrySet()) {
            if (entry.getKey().ugx_tag.equals(ugx_tag)) {
                obs.update(entry.getValue().data);
            }
        }
        // TODO: should we inform the listeners here?!
        //obs.update(getTag(ugx_tag, objectID, windowID, false).data);
    }

    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     * @param ugx_tag       the ugx_tag
     * @param objectID
     * @param windowID
     */
    public synchronized void deleteObserver(LoadUGXFileObserver obs, String ugx_tag, int objectID, int windowID) {
        Identifier id = new Identifier(ugx_tag, objectID, windowID);
        if (ugx_tags.containsKey(id)) {
            ugx_tags.get(id).observers.remove(obs);
        }
        if (globalTags.containsKey(ugx_tag)) {
            globalTags.get(ugx_tag).observers.remove(obs);
        }
    }

    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     */
    public synchronized void deleteObserver(LoadUGXFileObserver obs) {

        for (Map.Entry<Identifier, UGXFileTag> entry : ugx_tags.entrySet()) {
            entry.getValue().observers.remove(obs);
        }
        for (Map.Entry<String, UGXFileGlobalTag> entry : globalTags.entrySet()) {
            entry.getValue().observers.remove(obs);
        }
    }

    /**
     * Removes all observer of a ugx_tag from this Observable. 
     * 
     * @param ugx_tag       the ugx_tag
     */
    public synchronized void deleteObservers(String ugx_tag, int objectID, int windowID) {
        Identifier id = new Identifier(ugx_tag, objectID, windowID);
        if (ugx_tags.containsKey(id)) {
            ugx_tags.get(id).observers.clear();
        }
        for (Map.Entry<String, UGXFileGlobalTag> entry : globalTags.entrySet()) {
            entry.getValue().observers.clear();
        }
    }

    /**
     * Notifies all observers of a ugx_tag about the currently given data
     * 
     * @param ugx_tag   the ugx_tag
     */
    public synchronized void notifyObservers(String ugx_tag, int objectID, int windowID) {
        // get data for ugx_tag
        UGXFileTag ugxTag = getTag(ugx_tag, objectID, windowID, false);

        // if no such ugx_tag present, return (i.e. no observer)
        if (ugxTag != null) {

            // notify observers of this ugx_tag
            for (LoadUGXFileObserver b : ugxTag.observers) {
                b.update(ugxTag.data);
            }
        }

        // get data for global ugx_tag
        UGXFileGlobalTag ugxGlobalTag = getGlobalTag(ugx_tag, false);

        // if no such ugx_tag present, return (i.e. no observer)
        if (ugxGlobalTag != null) {

            // notify observers of this ugx_tag
            for (LoadUGXFileObserver b : ugxGlobalTag.observers) {
                if (ugxTag != null) {
                    b.update(ugxTag.data);
                } else {
                    b.update(null);
                }
            }
        }
    }
    
    /**
     * Notifies a specific observer of a ugx_tag about the currently given data
     * 
     * @param obs       the observer
     * @param ugx_tag   the ugx_tag
     * @param objectID    the objectID containing the observer
     * @param windowID  the window containing the objectID
     */
    public synchronized void notifyObserver(LoadUGXFileObserver obs, String ugx_tag, int objectID, int windowID)
    {
        // try getting data for ugx_tag
        UGXFileTag ugxTag = getTag(ugx_tag, objectID, windowID, false);

        if (ugxTag != null) obs.update(ugxTag.data);
    }
    
    
    /**
     * sets a filename for a ugx_tag. The file will be analysed and the contained 
     * data will be broadcasted to all observer of the ugx_tag.
     * 
     * @param file      the file
     * @param ugx_tag       the ugx_tag
     * @return          empty string if successful, error-msg if error occured
     */
    public synchronized String setSelectedFile(File file, String ugx_tag, int objectID, int windowID) {

        UGXFileTag ugxTag = getTag(ugx_tag, objectID, windowID, true);

        if (!file.toString().endsWith(".ugx")) {
            setInvalidFile(ugx_tag, objectID, windowID);
            return "Invalid Filename: " + file.toString() + ". Must be *.ugx.";
        }

        ugxTag.data = new UGXFileInfo();

        //  Parse the ugx file for subsets and dimensions
        //  and store the subsets.
        if (ugxTag.data.parse_file(file.toString()) == false) {
            setInvalidFile(ugx_tag, objectID, windowID);
            return "Unable to parse ugx-File: " + file.toString();
        }


        if (ugxTag.data.const__num_grids() != 1) {
            setInvalidFile(ugx_tag, objectID, windowID);
            return "ugx-File must contain exactly one grid.";
        }

        if (ugxTag.data.const__num_subset_handlers(0) < 1) {
            setInvalidFile(ugx_tag, objectID, windowID);
            return "ugx-File must contain at least one subset handler.";
        }

        // now we notify the obersver of this ugx_tag
        notifyObservers(ugx_tag, objectID, windowID);

        return "";
    }

    /**
     * Sets that a ugx_tag has an invalid file.
     * 
     * @param ugx_tag       the ugx_tag
     * @param objectID
     * @param windowID
     */
    public synchronized void setInvalidFile(String ugx_tag, int objectID, int windowID) {
        UGXFileTag ugxTag = getTag(ugx_tag, objectID, windowID, true);

        //  set to new (empty) data
        ugxTag.data = null;

        // now we notify the obersver of this ugx_tag
        notifyObservers(ugx_tag, objectID, windowID);
    }
}
