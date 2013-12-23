package edu.gcsc.vrl.userdata;

import java.util.List;

/**
 *
 * @author mbreit
 */
public interface FunctionDefinitionObserver
{

    /**
     * Updates the observer with the new data.
     * @param data      new data to update with.
     * @param fct_tag   fct_tag the data is associated to
     * @param object    object the data is associated to
     * @param windowID  windowID the data is associated to
     */
    public void update(List<FunctionDefinitionObservable.FctData> data, String fct_tag, Object object, int windowID);
}
