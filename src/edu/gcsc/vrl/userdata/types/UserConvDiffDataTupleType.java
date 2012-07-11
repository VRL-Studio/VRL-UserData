/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.*;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Box;

/**
 *
 * @author A. Vogel
 */
@TypeInfo(type=UserConvDiffDataTuple.class, input=true, output=false, style="default")
public class UserConvDiffDataTupleType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    private UserNumberWindow sourceWindow;
    private UserVectorWindow velocityWindow;
    private UserMatrixWindow diffusionWindow;
    private UserNumberModel sourceModel;
    private UserVectorModel velocityModel;
    private UserMatrixModel diffusionModel;
    
    private VTextField subsetInput = new VTextField("");

    public UserConvDiffDataTupleType() {
        
        sourceModel = new UserNumberModel();
        velocityModel = new UserVectorModel();
        diffusionModel = new UserMatrixModel();

       setName("");

       nameLabel.setAlignmentX(LEFT_ALIGNMENT);
       add(nameLabel);


        subsetInput.setMinimumSize(new Dimension(80, subsetInput.getHeight()));
        subsetInput.setPreferredSize(new Dimension(80, subsetInput.getHeight()));
        subsetInput.setSize(new Dimension(80, subsetInput.getHeight()));
        setInputComponent(subsetInput);

        add(subsetInput);
        
        
//        // a little trick to have default values if parameter of this type is used
//        getSourceWindow().close();
//        getDiffusionWindow().close();
//        getVelocityWindow().close();
        

       Box b= Box.createVerticalBox();
       add(b);
      
       VButton sourceBtn = new VButton("Source");
       VButton velocityBtn = new VButton("Velocity");
       VButton diffusionBtn = new VButton("Diffusion");
       
       b.add(diffusionBtn);
       b.add(velocityBtn);
       b.add(sourceBtn);
       
       sourceBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sourceWindow = new UserNumberWindow(sourceModel,
                        UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());

                customParamData2SourceWindow();

                
                //add InputWindow to canvas
                getMainCanvas().addWindow(sourceWindow);
            }
        });

        velocityBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                velocityWindow = new UserVectorWindow(velocityModel,
                        UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());

                 customParamData2VelocityWindow();
                
                //add InputWindow to canvas
                getMainCanvas().addWindow(velocityWindow);

            }
        });

        
        diffusionBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                diffusionWindow = new UserMatrixWindow(diffusionModel,
                        UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());

                 customParamData2DiffusionWindow();
                
                //add InputWindow to canvas
                getMainCanvas().addWindow(diffusionWindow);

            }
        });

    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof UserConvDiffDataTuple) {
            UserConvDiffDataTuple pair = (UserConvDiffDataTuple) o;
            subsetInput.setText(pair.getSubset());
        }
    }

    private UserMatrixWindow getDiffusionWindow() {
        if (diffusionWindow == null) {
            diffusionWindow = new UserMatrixWindow(diffusionModel,
                    UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());
        }
        return diffusionWindow;
    }
    
    private UserVectorWindow getVelocityWindow() {
        if (velocityWindow == null) {
            velocityWindow = new UserVectorWindow(velocityModel,
                    UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());
        }
        return velocityWindow;
    }
    
    private UserNumberWindow getSourceWindow() {
        if (sourceWindow == null) {
            sourceWindow = new UserNumberWindow(sourceModel,
                    UserConvDiffDataTupleType.this, "User Data Input", getMainCanvas());
        }
        return sourceWindow;
    }
    

    private void customParamData2DiffusionWindow() {

        if (getCustomData() != null) {
            Object o = getCustomData().get(diffusionModel.getModelKey());

            if (o instanceof UserMatrixModel) {

                UserMatrixModel diffusionModel =  (UserMatrixModel) o;
                getDiffusionWindow().updateWindow(diffusionModel);

            }
        }
    }
    

    private void customParamData2VelocityWindow() {

        if (getCustomData() != null) {
            Object o = getCustomData().get(velocityModel.getModelKey());

            if (o instanceof UserVectorModel) {

                UserVectorModel velocityModel =  (UserVectorModel) o;
                getVelocityWindow().updateWindow(velocityModel);
            }
        }
    }
    

    private void customParamData2SourceWindow() {

        if (getCustomData() != null) {
            Object o = getCustomData().get(sourceModel.getModelKey());

            if (o instanceof UserNumberModel) {

                UserNumberModel sourceModel =  (UserNumberModel) o;
                getSourceWindow().updateWindow(sourceModel);
            }
        }
    }
    
    @Override
    public Object getViewValue() {
        
        System.out.println("UserConvDiffData.getViewValue");

        I_UserNumber sourceData = null;
        I_UserVector velocityData = null;
        I_UserMatrix diffusionData = null;

//        UserNumberModel sourceModel = null;

        customParamData2SourceWindow();
//        sourceModel = getSourceWindow().getModel();


        try {
            boolean isConst = sourceModel.isConstData();

            if (isConst) {
                I_ConstUserNumber number = new ConstUserNumber(sourceModel.getData());

                sourceData = number;
            } else {
                int dim = sourceModel.getDimension();
                switch (dim) {
                    case 1:
                        I_VRLUserNumber1d number1d = new VRLUserNumber1d();
                        number1d.data(createCode(sourceModel.getCode(), dim, 0));
                        sourceData = number1d;
                        break;
                    case 2:
                        I_VRLUserNumber2d number2d = new VRLUserNumber2d();
                        number2d.data(createCode(sourceModel.getCode(), dim, 0));
                        sourceData = number2d;
                        break;
                    case 3:
                        I_VRLUserNumber3d number3d = new VRLUserNumber3d();
                        number3d.data(createCode(sourceModel.getCode(), dim, 0));
                        sourceData = number3d;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            //
        }

//        UserVectorModel velocityModel = null;

        customParamData2VelocityWindow();
//        velocityModel = getVelocityWindow().getModel();


        try {
            boolean isConst = velocityModel.isConstData();

            if (isConst) {
                velocityData = arrayToUserVector(velocityModel.getData());
            } else {
                int dim = sourceModel.getDimension();
                  switch (dim) {
                    case 1:
                        I_VRLUserVector1d vector1d = new VRLUserVector1d();
                        vector1d.data(createCode(velocityModel.getCode(), dim, 1));
                        velocityData = vector1d;
                        break;
                    case 2:
                        I_VRLUserVector2d vector2d = new VRLUserVector2d();
                        vector2d.data(createCode(velocityModel.getCode(), dim, 1));
                        velocityData = vector2d;
                        break;
                    case 3:
                        I_VRLUserVector3d vector3d = new VRLUserVector3d();
                        vector3d.data(createCode(velocityModel.getCode(), dim, 1));
                        velocityData = vector3d;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
        }
        
//        UserMatrixModel diffusionModel = null;


        customParamData2DiffusionWindow();
//        diffusionModel = getDiffusionWindow().getModel();


        try {
            boolean isConst = diffusionModel.isConstData();

            if (isConst) {
                diffusionData = arrayToUserMatrix(diffusionModel.getData());
            } else {
                int dim = diffusionModel.getDimension();
                switch (dim) {
                    case 1:
                        I_VRLUserMatrix1d matrix1d = new VRLUserMatrix1d();
                        matrix1d.data(createCode(diffusionModel.getCode(), dim, 2));
                        diffusionData = matrix1d;
                        break;
                    case 2:
                        I_VRLUserMatrix2d matrix2d = new VRLUserMatrix2d();
                        matrix2d.data(createCode(diffusionModel.getCode(), dim, 2));
                        diffusionData = matrix2d;
                        break;
                    case 3:
                        I_VRLUserMatrix3d matrix3d = new VRLUserMatrix3d();
                        matrix3d.data(createCode(diffusionModel.getCode(), dim, 2));
                        diffusionData = matrix3d;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
        }
        
        return new UserConvDiffDataTuple(subsetInput.getText(), diffusionData, 
                                         velocityData, sourceData);
    }
    

    /**
     * 
     * @param code
     * @param dim       world dimension
     * @param type      type of data [0=Number, 1=Vector, 2=Matrix]
     * @return 
     */
    private String createCode(String code, int dim, int type) {

        ArrayList<String> paramNames = new ArrayList<String>();

        if(dim >= 1) paramNames.add("x");
        if(dim >= 2) paramNames.add("y");
        if(dim >= 3) paramNames.add("z");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, type,
                paramNames, UserData.returnTypes[type]);

        return code;
    }
    
    private static I_ConstUserMatrix createMatrix(int dim) {
        if (dim == 1) {
            return new ConstUserMatrix1d();
        } else if (dim == 2) {
            return new ConstUserMatrix2d();
        } else if (dim == 3) {
            return new ConstUserMatrix3d();
        }

        return null;
    }
   
    private static I_ConstUserMatrix arrayToUserMatrix(Double[][] data) {

        I_ConstUserMatrix result = createMatrix(data.length);

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                result.set_entry(i, j, data[i][j]);
            }
        }

        return result;
    }

    private static I_ConstUserVector createVector(int dim) {
        if (dim == 1) {
            return new ConstUserVector1d();
        } else if (dim == 2) {
            return new ConstUserVector2d();
        } else if (dim == 3) {
            return new ConstUserVector3d();
        }

        return null;
    }

    private static I_ConstUserVector arrayToUserVector(Double[] data) {

        I_ConstUserVector result = createVector(data.length);

        for (int i = 0; i < data.length; i++) {
            result.set_entry(i, data[i]);
        }

        return result;
    }
    
    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

}
