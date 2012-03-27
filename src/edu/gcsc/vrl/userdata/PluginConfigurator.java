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
public class PluginConfigurator extends VPluginConfigurator {

    public PluginConfigurator() {

        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("VRL-UserData", "0.1"));

        // allow other plugins to use the api of this plugin
        exportPackage("edu.gcsc.vrl");

        // describe the plugin
        setDescription("VRL Plugin for UG4-UserData visualisation.");

        // specify dependencies
//         addDependency(new PluginDependency("VRL", "0.4", "0.4"));
    }

    @Override
    public void register(PluginAPI api) {


        // register plugin with canvas
        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;
            
            // ... 
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
