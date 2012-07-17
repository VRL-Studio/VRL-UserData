package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;

/**
 * Observer interface for UGX File info
 * 
 * @author andreasvogel
 */
public interface LoadUGXFileObserver {

    public void update(UGXFileInfo data);
}
