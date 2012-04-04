/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.Constants;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.types.CanvasRequest;
import java.io.Serializable;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */

@ObjectInfo(name="Dummy")
@ComponentInfo(name = "UserDataWindow", category = Constants.CATEGORY)
//@ComponentInfo(name = "AddUserDataWindow", category = "VRL-UserData")
public class AddUserDataWindow implements Serializable {

    private static final long serialVersionUID = 1;

    public AddUserDataWindow() {
    }
    
    public void addWindow(CanvasRequest cReq){

        UserDataWindow w = new UserDataWindow("VRL-UserData", cReq.getCanvas());
                        
        //add InputWindow to canvas
        cReq.getCanvas().addWindow(w);
    }
    
}
