/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.io.VersionInfo;
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
        addDependency(new PluginDependency("VRL", "0.4", VersionInfo.UNDEFINED));

        // specify dependencies
        addDependency(new PluginDependency("VRL-UG4", "0.2", VersionInfo.UNDEFINED));
        addDependency(new PluginDependency("VRL-UG4-API", "0.1", VersionInfo.UNDEFINED));
        
    }

    
    @Override
    public void register(PluginAPI api) {

        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;
            
            // ... 
            
             //
            /// TYPES 
            //

            vapi.addTypeRepresentation(new UserNumberType());
            vapi.addTypeRepresentation(new UserVectorType());
            
             //
            /// BASICMATH Components
            //
            
//            vapi.addComponent(AddUserDataWindow.class);
        
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


