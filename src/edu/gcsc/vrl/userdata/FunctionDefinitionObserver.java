package edu.gcsc.vrl.userdata;

import java.util.Collection;

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
     * @param windowID  windowID the data is associated to
     */
    public void update(final Collection<FunctionDefinitionObservable.FctData> data, String fct_tag, int windowID);
}
