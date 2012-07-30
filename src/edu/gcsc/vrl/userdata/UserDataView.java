/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.awt.Component;

/**
 *
 * @author andreasvogel
 */
public abstract class UserDataView {
    
    public abstract Component getComponent(); 
  
    public abstract void updateView(UserDataModel theModel);
    
    public abstract void adjustData(UGXFileInfo info, boolean modelConsistent);
}
