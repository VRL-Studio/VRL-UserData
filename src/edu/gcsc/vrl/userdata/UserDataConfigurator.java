/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.system.*;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataConfigurator extends VPluginConfigurator {

    public UserDataConfigurator() {

        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("VRL-UserData", "0.1"));

        // allow other plugins to use the api of this plugin
        exportPackage("edu.gcsc.vrl.userdata");

        // describe the plugin
        setDescription("VRL Plugin for UG4-UserData visualisation.");

        // specify dependencies
         addDependency(new PluginDependency("VRL", "0.4", "0.4"));

        // specify dependencies
         addDependency(new PluginDependency("VRL-UG4", "0.2", "0.2"));
        
    }

    
    @Override
    public void register(PluginAPI api) {

        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;
            
            // ... 
            
             //
            /// TYPES 
            //

            vapi.addTypeRepresentation(new UserDataType());
            
             //
            /// BASICMATH Components
            //
            
            vapi.addComponent(AddUserDataWindow.class);
            
            vapi.addComponent(UserDataWindow.class);
        }
    }

    @Override
    public void unregister(PluginAPI api) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(InitPluginAPI iApi) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}


