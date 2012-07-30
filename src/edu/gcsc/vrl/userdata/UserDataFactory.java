/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;

/**
 *
 * @author andreasvogel
 */
public class UserDataFactory {
 
    public static UserDataModel createModel(UserDataCategory cat){
        UserDataModel model = null;
        switch (cat) {
                case NUMBER:
                    model = new UserNumberModel();
                    break;
                case VECTOR:
                    model = new UserVectorModel();
                    break;
                case MATRIX:
                    model = new UserMatrixModel();
                    break;
                case COND_NUMBER:
                    model = new CondUserNumberModel();
                    break;
                case SUBSET:
                    model = new UserSubsetModel();
                    break;
                default: throw new RuntimeException("UserDataFactory: Unknown "
                        + " category for UserDataModel requested.");
            }
        return model;
    }
            
    public static UserDataView createView(UserDataCategory cat,
                                            String name, UserDataModel model,
                                            UserDataTupleType tuple) {
        UserDataView view = null;
        switch (cat) {
                case NUMBER:
                case VECTOR:
                case MATRIX:
                case COND_NUMBER:
                    view = new UserMathDataView(name, model, tuple);
                    break;
                case SUBSET:
                    view = new UserSubsetView(name, model, tuple);
                    break;
                default: throw new RuntimeException("UserDataFactory: Unknown "
                        + " category for UserDataView requested.");
            }
        return view;
    }

}
