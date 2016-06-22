/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
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
import edu.gcsc.vrl.userdata.types.FunctionDefinitionArrayType;
import edu.gcsc.vrl.userdata.types.FunctionDefinitionType;
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
            
            vapi.addTypeRepresentation( StringSubsetType.class);
            vapi.addTypeRepresentation( StringSubsetArrayType.class);
            
            vapi.addTypeRepresentation( FunctionDefinitionType.class);
            vapi.addTypeRepresentation( FunctionDefinitionArrayType.class);

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


