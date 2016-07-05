/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import java.awt.Component;

/**
 *
 * @author andreasvogel
 */
public abstract class UserDataView {
    
    public abstract Component getComponent(); 
  
    public abstract void adjustView(UserDataModel theModel);

    public abstract void adjustView(UserDataModel.Status status);

    public abstract void adjustView(Object data);

    public abstract void closeView();

}
