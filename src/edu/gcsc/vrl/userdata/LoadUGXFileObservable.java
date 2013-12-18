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
 * @author andreasvogel
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
     * not depending on the object. The actual data is not stored here, but at 
     * the concrete ugx_tag with object.
     */
    private class UGXFileGlobalTag {

        public Collection<LoadUGXFileObserver> observers = new HashSet<LoadUGXFileObserver>();
    }

    /**
     * Identifier for grid loader strings. If several loaders exist, they are
     * destinguished by ugx_tag, objectID and windowID
     */
    private class Identifier {

        public Identifier(String ugx_tag, Object object, int windowID) {
            this.ugx_tag = ugx_tag;
            this.object = object;
            this.windowID = windowID;
        }
        private String ugx_tag;
        private Object object;
        private int windowID;

        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + ugx_tag.hashCode();
            result = 37 * result + object.hashCode();
            result = 37 * result + windowID;
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

            return (ugx_tag.equals(rhs.ugx_tag)) && (object == rhs.object) && (windowID == rhs.windowID);
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
    private synchronized UGXFileTag getTag(String ugx_tag, Object object, int windowID, boolean create) {
        Identifier id = new Identifier(ugx_tag, object, windowID);

        if (ugx_tags.containsKey(id)) {
            return ugx_tags.get(id);
        }

        if (create) {
            ugx_tags.put(id, new UGXFileTag());
            return getTag(ugx_tag, object, windowID, false);
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
    public synchronized void addObserver(LoadUGXFileObserver obs, String ugx_tag, Object object, int windowID) {
        getTag(ugx_tag, object, windowID, true).observers.add(obs);
        obs.update(getTag(ugx_tag, object, windowID, false).data);
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
        //obs.update(getTag(ugx_tag, object, windowID, false).data);
    }

    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     * @param ugx_tag       the ugx_tag
     */
    public synchronized void deleteObserver(LoadUGXFileObserver obs, String ugx_tag, Object object, int windowID) {
        Identifier id = new Identifier(ugx_tag, object, windowID);
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
     * @param ugx_tag       the ugx_tag
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
    public synchronized void deleteObservers(String ugx_tag, Object object, int windowID) {
        Identifier id = new Identifier(ugx_tag, object, windowID);
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
    public synchronized void notifyObservers(String ugx_tag, Object object, int windowID) {
        // get data for ugx_tag
        UGXFileTag ugxTag = getTag(ugx_tag, object, windowID, false);

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
     * sets a filename for a ugx_tag. The file will be analysed and the contained 
     * data will be broadcasted to all observer of the ugx_tag.
     * 
     * @param file      the file
     * @param ugx_tag       the ugx_tag
     * @return          empty string if successful, error-msg if error occured
     */
    public synchronized String setSelectedFile(File file, String ugx_tag, Object object, int windowID) {

        UGXFileTag ugxTag = getTag(ugx_tag, object, windowID, true);

        if (!file.toString().endsWith(".ugx")) {
            setInvalidFile(ugx_tag, object, windowID);
            return "Invalid Filename: " + file.toString() + ". Must be *.ugx.";
        }

        ugxTag.data = new UGXFileInfo();

        //  Parse the ugx file for subsets and dimensions
        //  and store the subsets.
        if (ugxTag.data.parse_file(file.toString()) == false) {
            setInvalidFile(ugx_tag, object, windowID);
            return "Unable to parse ugx-File: " + file.toString();
        }


        if (ugxTag.data.const__num_grids() != 1) {
            setInvalidFile(ugx_tag, object, windowID);
            return "ugx-File must contain exactly one grid.";
        }

        if (ugxTag.data.const__num_subset_handlers(0) < 1) {
            setInvalidFile(ugx_tag, object, windowID);
            return "ugx-File must contain at least one subset handler.";
        }

        // now we notify the obersver of this ugx_tag
        notifyObservers(ugx_tag, object, windowID);

        return "";
    }

    /**
     * Sets that a ugx_tag has an invalid file.
     * 
     * @param ugx_tag       the ugx_tag
     */
    public synchronized void setInvalidFile(String ugx_tag, Object object, int windowID) {
        UGXFileTag ugxTag = getTag(ugx_tag, object, windowID, true);

        //  set to new (empty) data
        ugxTag.data = null;

        // now we notify the obersver of this ugx_tag
        notifyObservers(ugx_tag, object, windowID);
    }
}
