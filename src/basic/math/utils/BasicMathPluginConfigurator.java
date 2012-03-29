/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.utils;

import basic.math.elements.generators.MatrixGenerator;
import basic.math.elements.generators.ScalarGenerator;
import basic.math.elements.generators.TensorGenerator;
import basic.math.elements.generators.VectorGenerator;
import basic.math.helpers.ElementContainer;
import basic.math.operations.*;
import basic.math.operations.conception.AddOperation;
import basic.math.operations.procedure.*;
import basic.math.types.*;
import basic.math.ug.equation.tools.LivePlotter;
import basic.math.ug.equation.tools.PickPlotter;
import basic.math.ug.equation.tools.SolutionPlotter;
import basic.math.ug.equation.tools.UGInput;
import basic.math.ug.skin.*;
import eu.mihosoft.vrl.system.*;
import eu.mihosoft.vrl.types.FileListType;

/**
 *
 * @author christianpoliwoda
 */
public class BasicMathPluginConfigurator extends VPluginConfigurator {

        public BasicMathPluginConfigurator() {

        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("VRL-UserData", "0.1"));

        // allow other plugins to use the api of this plugin
        exportPackage("basic.math.utils");

        // describe the plugin
        setDescription("VRL Plugin for UG4-UserData visualisation.");

        // specify dependencies
         addDependency(new PluginDependency("VRL", "0.4", "0.4"));

        // specify dependencies
         addDependency(new PluginDependency("VRL-UG4", "0.2", "0.2"));
    }

    @Override
    public void register(PluginAPI api) {

        // this plugin only works if reflection is supported
        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;

             //
            /// TYPES 
            //

            vapi.addTypeRepresentation(new FileListType());

            // basicMath
            vapi.addTypeRepresentation(new VisualTensorType());
            vapi.addTypeRepresentation(new VisualMatrixType());
            vapi.addTypeRepresentation(new VisualVectorType());
            vapi.addTypeRepresentation(new VisualScalarType());

            vapi.addTypeRepresentation(new ParametersOperationSelectionType());
            vapi.addTypeRepresentation(new ClassSelectionType());

            vapi.addTypeRepresentation(new MathMLType());
//            vapi.addTypeRepresentation(new eu.mihosoft.vrlext.types.MathMLType());
            vapi.addTypeRepresentation(new VisualElementType());
            vapi.addTypeRepresentation(new VisualElementArrayType());
            vapi.addTypeRepresentation(new VisualVectorArrayType());
            vapi.addTypeRepresentation(new intArrayType());

            //procedure
            vapi.addTypeRepresentation(new FunctionType());
            vapi.addTypeRepresentation(new TrajectoryType());
            vapi.addTypeRepresentation(new TrajectoryArrayType());

            // equation
            vapi.addTypeRepresentation(new basic.math.ug.types.StaticFormelInputType());
            vapi.addTypeRepresentation(new basic.math.ug.types.FormelInputType());
            vapi.addTypeRepresentation(new basic.math.ug.types.FormelOutputType());
            vapi.addTypeRepresentation(new FileListType());
            vapi.addTypeRepresentation(new basic.math.ug.types.PickArrayTypeRepresentation());
            vapi.addTypeRepresentation(new basic.math.ug.types.PickTypeRepresentation());//seems not to be used

            // SkinModell
            vapi.addTypeRepresentation(new basic.math.ug.types.GeomDefSelectionType());
            vapi.addTypeRepresentation(new basic.math.ug.types.CustomDoubleType());
            vapi.addTypeRepresentation(new basic.math.ug.types.CustomLongType());
            vapi.addTypeRepresentation(new basic.math.ug.types.CustomIntegerType());
            vapi.addTypeRepresentation(new basic.math.ug.types.SkinModelSpecificParametersType());
            vapi.addTypeRepresentation(new basic.math.ug.types.SkinModelExecuteType());
            vapi.addTypeRepresentation(new basic.math.ug.types.SkinModelGeometryType());
            vapi.addTypeRepresentation(new basic.math.ug.types.SkinModelOutputType());
            vapi.addTypeRepresentation(new basic.math.ug.types.SkinModelRefinementType());
            vapi.addTypeRepresentation(new basic.math.ug.types.LgmCeckingForNgFileType());
            vapi.addTypeRepresentation(new basic.math.ug.types.LgmFileType());


             //
            /// BASICMATH ITEMS
            //

            vapi.addComponent(TensorGenerator.class);
            vapi.addComponent(MatrixGenerator.class);
            vapi.addComponent(VectorGenerator.class);
            vapi.addComponent(ScalarGenerator.class);
            
//            vapi.addComponent(TwoParametersOperation.class);
            vapi.addComponent(ElementContainer.class);
            vapi.addComponent(BilinearFormen.class);
            vapi.addComponent(CrossProduct.class);
            vapi.addComponent(Norm.class);
            vapi.addComponent(AddOperation.class);
            vapi.addComponent(MatrixVectorMultiplication.class);
            vapi.addComponent(GeometryTransformation.class);
            vapi.addComponent(TwoBodyProblem.class);
            vapi.addComponent(EulerExplicit.class);
            vapi.addComponent(Plotter.class);
            vapi.addComponent(Heun.class);

            
            // EQUATION ITEMS           
            vapi.addComponent(PickPlotter.class);
            vapi.addComponent(UGInput.class);
            vapi.addComponent(SolutionPlotter.class);
            vapi.addComponent(LivePlotter.class);
//            vapi.addComponent(SolutionMovPlotter.class);

            
            // SKIN ITEMS
            vapi.addComponent(SkinModel.class);
            vapi.addComponent(SkinModelSpecificParametersWindow.class);
            vapi.addComponent(SkinModelExecuteWindow.class);
            vapi.addComponent(SkinModelGeometryWindow.class);
            vapi.addComponent(SkinModelOutputWindow.class);
            vapi.addComponent(SkinModelRefinementWindow.class);
            vapi.addComponent(SkinModelGeometryWindowSimpler.class);
            vapi.addComponent(SkinGeometryPlotter.class);

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
/*


 * To change this template, choose Tools | Templates
 * and open the template in the editor.

package plugin;

import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;


 * VRL PluginConfigurator entweder implements PluginConfigurator interface
 * oder extends VPluginConfigurator
 * @author christianpoliwoda

public class PluginConfigurator extends VPluginConfigurator{

public PluginConfigurator() {

// set plugin name
setIdentifier(new PluginIdentifier("NativePlugin", "0.1"));

// set dependencies
//        addDependency(new PluginDependency("UG4", "0.1", "0.2"));

setDescription("Native Library Test (JNI)");
}



@Override
public void register(PluginAPI api) {

// this plugin only works if reflection is supported
if (api instanceof VPluginAPI) {
VPluginAPI vapi = (VPluginAPI) api;

vapi.addComponent(NativeComponent.class);
}
}

@Override
public void unregister(PluginAPI api) {
// not used
}

@Override
public void init() {
// not used
}



}


 */