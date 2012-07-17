package edu.gcsc.vrl.userdata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import eu.mihosoft.vrl.visual.MessageType;

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

        public Collection<LoadUGXFileObserver> observers = new ArrayList<LoadUGXFileObserver>();
        public UGXFileInfo data = null;
    }
    /**
     * Map storing data and observers associated with a tag
     */
    private Map<String, UGXFileTag> tags = new HashMap<String, UGXFileTag>();

    /**
     * returns the file tag info for a tag. If create is set to true a new tag
     * entry is create, else not. If no tag exists and create is set to false 
     * null is returned.
     * 
     * @param tag       tag name
     * @param create    flag indicating if tag should be created if it does not exists
     * @return          file tag info
     */
    private synchronized UGXFileTag getTag(String tag, boolean create) {
        if (tags.containsKey(tag)) {
            return tags.get(tag);
        }

        if (create) {
            tags.put(tag, new UGXFileTag());
            return getTag(tag, false);
        }

        return null;
    }

    /**
     * Add an observer to this Observable. The observer listens to a tag.
     * 
     * @param obs       the observer to add
     * @param tag       the tag
     */
    public synchronized void addObserver(LoadUGXFileObserver obs, String tag) {
        getTag(tag, true).observers.add(obs);
    }

    /**
     * Notifies all observers of a tag about the currently given data
     * 
     * @param tag   the tag
     */
    public void notifyObservers(String tag) {
        // get data for tag
        UGXFileTag ugxTag = getTag(tag, false);

        // if no such tag present, return (i.e. no observer)
        if (ugxTag == null) {
            return;
        }

        // notify observers of this tag
        for (LoadUGXFileObserver b : ugxTag.observers) {
            b.update(ugxTag.data);
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
    public synchronized String setSelectedFile(File file, String tag) {
        UGXFileTag ugxTag = getTag(tag, true);
        ugxTag.data = new UGXFileInfo();

        //  Parse the ugx file for subsets and dimensions
        //  and store the subsets.
        if (ugxTag.data.parse_file(file.toString()) == false) {
            ugxTag.data = null;
            return "Unable to parse ugx-File: " + file.toString();
        }


        if (ugxTag.data.const__num_grids() != 1) {
            ugxTag.data = null;
            return "ugx-File must contain exactly one grid.";
        }

        if (ugxTag.data.const__num_subset_handlers(0) < 1) {
            ugxTag.data = null;
            return "ugx-File must contain at least one subset handler.";
        }

        // now we notify the obersver of this tag
        notifyObservers(tag);

        return "";
    }

    /**
     * Sets that a tag has an invalid file.
     * 
     * @param tag       the tag
     */
    public synchronized void setInvalidFile(String tag) {
        UGXFileTag ugxTag = getTag(tag, true);

        //  set to new (empty) data
        ugxTag.data = null;

        // now we notify the obersver of this tag
        notifyObservers(tag);
    }

    public synchronized UGXFileInfo getSelectedData(String tag) {
        UGXFileTag ugxTag = getTag(tag, false);
        if (ugxTag == null) {
            return null;
        } else {
            return ugxTag.data;
        }
    }
}
