package edu.gcsc.vrl.userdata;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.HashSet;

/**
 * Stores the data info to a ugx file. Several files are possible and destinguished
 * via tags. 
 * Implementation is via a Singleton and the Observer Pattern. Observer can 
 * register at this Observable using a tag and will be notified once the data 
 * info associated with a tag has changed.
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
     * Info class for a tag. Stored are all observer listening to the tag and 
     * the ugx file data associated with a tag.
     */
    private class UGXFileTag {

        public Collection<LoadUGXFileObserver> observers = new HashSet<LoadUGXFileObserver>();
        public UGXFileInfo data = null;
    }

    /**
     * Info for global tags, i.e. tags that do listen to all tags with a name,
     * not depending on the object. The actual data is not stored here, but at 
     * the concrete tag with object.
     */
    private class UGXFileGlobalTag {

        public Collection<LoadUGXFileObserver> observers = new HashSet<LoadUGXFileObserver>();
    }

    /**
     * Identifier for grid loader strings. If several loaders exist, they are
     * destinguished by tag, objectID and windowID
     */
    private class Identifier {

        public Identifier(String tag, Object object, int windowID) {
            this.tag = tag;
            this.object = object;
            this.windowID = windowID;
        }
        private String tag;
        private Object object;
        private int windowID;

        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + tag.hashCode();
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

            return (tag.equals(rhs.tag)) && (object == rhs.object) && (windowID == rhs.windowID);
        }
    }
    /**
     * Map storing data and observers associated with a tag
     */
    private transient Map<Identifier, UGXFileTag> tags = new HashMap<Identifier, UGXFileTag>();
    private transient Map<String, UGXFileGlobalTag> globalTags = new HashMap<String, UGXFileGlobalTag>();

    /**
     * returns the file tag info for a tag. If create is set to true a new tag
     * entry is create, else not. If no tag exists and create is set to false 
     * null is returned.
     * 
     * @param tag       tag name
     * @param create    flag indicating if tag should be created if it does not exists
     * @return          file tag info
     */
    private synchronized UGXFileTag getTag(String tag, Object object, int windowID, boolean create) {
        Identifier id = new Identifier(tag, object, windowID);

        if (tags.containsKey(id)) {
            return tags.get(id);
        }

        if (create) {
            tags.put(id, new UGXFileTag());
            return getTag(tag, object, windowID, false);
        }

        return null;
    }

    /**
     * returns the file tag info for a tag. If create is set to true a new tag
     * entry is create, else not. If no tag exists and create is set to false 
     * null is returned.
     * 
     * @param tag       tag name
     * @param create    flag indicating if tag should be created if it does not exists
     * @return          file tag info
     */
    private synchronized UGXFileGlobalTag getGlobalTag(String tag, boolean create) {
        if (globalTags.containsKey(tag)) {
            return globalTags.get(tag);
        }

        if (create) {
            globalTags.put(tag, new UGXFileGlobalTag());
            return getGlobalTag(tag, false);
        }

        return null;
    }

    /**
     * Add an observer to this Observable. The observer listens to a tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs       the observer to add
     * @param tag       the tag
     */
    public synchronized void addObserver(LoadUGXFileObserver obs, String tag, Object object, int windowID) {
        getTag(tag, object, windowID, true).observers.add(obs);
        obs.update(getTag(tag, object, windowID, false).data);
    }

    /**
     * Add an observer to this Observable. The observer listens to a tag.
     * The observer will be updated with the current data automatically.
     * 
     * @param obs       the observer to add
     * @param tag       the tag
     */
    public synchronized void addObserver(LoadUGXFileObserver obs, String tag) {
        getGlobalTag(tag, true).observers.add(obs);

        for (Map.Entry<Identifier, UGXFileTag> entry : tags.entrySet()) {
            if (entry.getKey().tag.equals(tag)) {
                obs.update(entry.getValue().data);
            }
        }
        // TODO: should we inform the listeners here?!
        //obs.update(getTag(tag, object, windowID, false).data);
    }

    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     * @param tag       the tag
     */
    public synchronized void deleteObserver(LoadUGXFileObserver obs, String tag, Object object, int windowID) {
        Identifier id = new Identifier(tag, object, windowID);
        if (tags.containsKey(id)) {
            tags.get(id).observers.remove(obs);
        }
        if (globalTags.containsKey(tag)) {
            globalTags.get(tag).observers.remove(obs);
        }
    }

    /**
     * Removes an observer from this Observable. 
     * 
     * @param obs       the observer to remove
     * @param tag       the tag
     */
    public synchronized void deleteObserver(LoadUGXFileObserver obs) {

        for (Map.Entry<Identifier, UGXFileTag> entry : tags.entrySet()) {
            entry.getValue().observers.remove(obs);
        }
        for (Map.Entry<String, UGXFileGlobalTag> entry : globalTags.entrySet()) {
            entry.getValue().observers.remove(obs);
        }
    }

    /**
     * Removes all observer of a tag from this Observable. 
     * 
     * @param tag       the tag
     */
    public synchronized void deleteObservers(String tag, Object object, int windowID) {
        Identifier id = new Identifier(tag, object, windowID);
        if (tags.containsKey(id)) {
            tags.get(id).observers.clear();
        }
        for (Map.Entry<String, UGXFileGlobalTag> entry : globalTags.entrySet()) {
            entry.getValue().observers.clear();
        }
    }

    /**
     * Notifies all observers of a tag about the currently given data
     * 
     * @param tag   the tag
     */
    public synchronized void notifyObservers(String tag, Object object, int windowID) {
        // get data for tag
        UGXFileTag ugxTag = getTag(tag, object, windowID, false);

        // if no such tag present, return (i.e. no observer)
        if (ugxTag != null) {

            // notify observers of this tag
            for (LoadUGXFileObserver b : ugxTag.observers) {
                b.update(ugxTag.data);
            }
        }

        // get data for global tag
        UGXFileGlobalTag ugxGlobalTag = getGlobalTag(tag, false);

        // if no such tag present, return (i.e. no observer)
        if (ugxGlobalTag != null) {

            // notify observers of this tag
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
     * sets a filename for a tag. The file will be analysed and the contained 
     * data will be broadcasted to all observer of the tag.
     * 
     * @param file      the file
     * @param tag       the tag
     * @return          empty string if successful, error-msg if error occured
     */
    public synchronized String setSelectedFile(File file, String tag, Object object, int windowID) {

        UGXFileTag ugxTag = getTag(tag, object, windowID, true);

        if (!file.toString().endsWith(".ugx")) {
            setInvalidFile(tag, object, windowID);
            return "Invalid Filename: " + file.toString() + ". Must be *.ugx.";
        }

        ugxTag.data = new UGXFileInfo();

        //  Parse the ugx file for subsets and dimensions
        //  and store the subsets.
        if (ugxTag.data.parse_file(file.toString()) == false) {
            setInvalidFile(tag, object, windowID);
            return "Unable to parse ugx-File: " + file.toString();
        }


        if (ugxTag.data.const__num_grids() != 1) {
            setInvalidFile(tag, object, windowID);
            return "ugx-File must contain exactly one grid.";
        }

        if (ugxTag.data.const__num_subset_handlers(0) < 1) {
            setInvalidFile(tag, object, windowID);
            return "ugx-File must contain at least one subset handler.";
        }

        // now we notify the obersver of this tag
        notifyObservers(tag, object, windowID);

        return "";
    }

    /**
     * Sets that a tag has an invalid file.
     * 
     * @param tag       the tag
     */
    public synchronized void setInvalidFile(String tag, Object object, int windowID) {
        UGXFileTag ugxTag = getTag(tag, object, windowID, true);

        //  set to new (empty) data
        ugxTag.data = null;

        // now we notify the obersver of this tag
        notifyObservers(tag, object, windowID);
    }
}
