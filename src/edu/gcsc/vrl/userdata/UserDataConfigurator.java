/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.types.LoadUGXFileStringType;
import edu.gcsc.vrl.userdata.types.UserDataTupleArrayType;
import edu.gcsc.vrl.userdata.types.CondUserNumberType;
import edu.gcsc.vrl.userdata.types.UserMatrixType;
import edu.gcsc.vrl.userdata.types.CondUserNumberArrayType;
import edu.gcsc.vrl.userdata.types.LoadUGXFileType;
import edu.gcsc.vrl.userdata.types.StringSubsetArrayType;
import edu.gcsc.vrl.userdata.types.StringSubsetType;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import edu.gcsc.vrl.userdata.types.UserMatrixArrayType;
import edu.gcsc.vrl.userdata.types.UserNumberType;
import edu.gcsc.vrl.userdata.types.UserNumberArrayType;
import edu.gcsc.vrl.userdata.types.UserVectorType;
import edu.gcsc.vrl.userdata.types.UserVectorArrayType;
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
        setIdentifier(new PluginIdentifier("VRL-UserData", "0.2"));

        // allow other plugins to use the api of this plugin
        exportPackage("edu.gcsc.vrl.userdata");

        // describe the plugin
        setDescription("VRL Plugin for UG4-UserData visualization.");

        // specify dependencies
        addDependency(new PluginDependency("VRL", "0.4.2", VersionInfo.UNDEFINED));

        // specify dependencies
        addDependency(new PluginDependency("VRL-UG", "0.2", VersionInfo.UNDEFINED));
        addDependency(new PluginDependency("VRL-UG-API", "0.2", VersionInfo.UNDEFINED));
        
    }

    
    @Override
    public void register(PluginAPI api) {

        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;
            
            // ... 
            
             //
            /// TYPES 
            //

            vapi.addTypeRepresentation( StringSubsetType.class);
            vapi.addTypeRepresentation( StringSubsetArrayType.class);


            vapi.addTypeRepresentation( UserDataTupleType.class);
            vapi.addTypeRepresentation( UserNumberType.class);
            vapi.addTypeRepresentation( UserVectorType.class);
            vapi.addTypeRepresentation( UserMatrixType.class);
            vapi.addTypeRepresentation( CondUserNumberType.class);
            
            vapi.addTypeRepresentation( UserDataTupleArrayType.class);
            vapi.addTypeRepresentation( UserNumberArrayType.class);
            vapi.addTypeRepresentation( UserVectorArrayType.class);
            vapi.addTypeRepresentation( UserMatrixArrayType.class);
            vapi.addTypeRepresentation( CondUserNumberArrayType.class);

            vapi.addTypeRepresentation( LoadUGXFileStringType.class);
            vapi.addTypeRepresentation( LoadUGXFileType.class);

            
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


