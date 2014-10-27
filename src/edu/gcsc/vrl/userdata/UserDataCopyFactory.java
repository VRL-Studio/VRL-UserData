/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactory {

    public Object createUserDataCopy(int dim, String inputType, String codeData, String codeDeriv, Object data) {

        UserMathDataModel.InputType type = null;

        if (UserMathDataModel.InputType.CODE.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.CODE;
        } else if (UserMathDataModel.InputType.CONSTANT.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.CONSTANT;
        } else if (UserMathDataModel.InputType.SCALAR_CONSTANT.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.SCALAR_CONSTANT;
        } else if (UserMathDataModel.InputType.LINKER.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.LINKER;
        }

        return createUserDataCopy(dim, type, codeData, codeDeriv, data);
    }

    /**
     * This method is a helper for getModelAsCode() to create a copy of an existing UserData
     * with the specified parameters.
     
     @param dim is the dimension of the userdata
     @param inputType declares if the userdata is a SCALAR_CONSTANT, CONSTANT or CODE
     @see edu.gcsc.vrl.userdata.UserMathDataModel#inputType
     @param codeData that is written in the editor of a UserDataWindow for the data
     @param codeDeriv  that is written in the editor of a UserDataWindow for the data
     @param data are the entries of User- Number/Vector/Matrix 
     @return a temporal object which creates the copy of an existing userdata
     */
    public Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {
        return new UnsupportedOperationException("Needs to be implemented by a derived class.");
    }

}
