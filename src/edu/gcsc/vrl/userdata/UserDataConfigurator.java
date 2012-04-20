/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.types.CondPairArrayType;
import edu.gcsc.vrl.userdata.types.CondUserNumberPairType;
import edu.gcsc.vrl.userdata.types.UserMatrixPairArrayType;
import edu.gcsc.vrl.userdata.types.UserMatrixArrayType;
import edu.gcsc.vrl.userdata.types.UserMatrixPairType;
import edu.gcsc.vrl.userdata.types.CondUserNumberType;
import edu.gcsc.vrl.userdata.types.UserMatrixType;
import edu.gcsc.vrl.userdata.types.CondArrayType;
import edu.gcsc.vrl.userdata.types.UserNumberPairArrayType;
import edu.gcsc.vrl.userdata.types.UserNumberType;
import edu.gcsc.vrl.userdata.types.UserNumberArrayType;
import edu.gcsc.vrl.userdata.types.UserVectorPairArrayType;
import edu.gcsc.vrl.userdata.types.UserVectorType;
import edu.gcsc.vrl.userdata.types.UserVectorArrayType;
import edu.gcsc.vrl.userdata.types.UserNumberPairType;
import edu.gcsc.vrl.userdata.types.UserVectorPairType;
import eu.mihosoft.vrl.io.VersionInfo;
import eu.mihosoft.vrl.system.*;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataConfigurator extends VPluginConfigurator {

    public UserDataConfigurator() {

        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("VRL-UserData", "0.1"));

        // allow other plugins to use the api of this plugin
        exportPackage("edu.gcsc.vrl.userdata");

        // describe the plugin
        setDescription("VRL Plugin for UG4-UserData visualization.");

        // specify dependencies
        addDependency(new PluginDependency("VRL", "0.4", VersionInfo.UNDEFINED));

        // specify dependencies
        addDependency(new PluginDependency("VRL-UG4", "0.2", VersionInfo.UNDEFINED));
        addDependency(new PluginDependency("VRL-UG4-API", "0.2", VersionInfo.UNDEFINED));
        
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
            vapi.addTypeRepresentation(new UserMatrixType());
            
            vapi.addTypeRepresentation(new CondUserNumberType());
            vapi.addTypeRepresentation(new CondUserNumberPairType());
            vapi.addTypeRepresentation(new CondArrayType());
            vapi.addTypeRepresentation(new CondPairArrayType());
            
            vapi.addTypeRepresentation(new UserNumberPairType());
            vapi.addTypeRepresentation(new UserNumberPairArrayType());
            vapi.addTypeRepresentation(new UserNumberArrayType());
            
            vapi.addTypeRepresentation(new UserVectorPairType());
            vapi.addTypeRepresentation(new UserVectorPairArrayType());
            vapi.addTypeRepresentation(new UserVectorArrayType());
            
            vapi.addTypeRepresentation(new UserMatrixPairType());
            vapi.addTypeRepresentation(new UserMatrixPairArrayType());
            vapi.addTypeRepresentation(new UserMatrixArrayType());
            
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


