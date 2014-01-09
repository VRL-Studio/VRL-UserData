/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserMatrix;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_UserVector;
import edu.gcsc.vrl.ug.api.I_VRLUserLinkerNumberNumber;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andreas Vogel
 */
public class UserDataTuple implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Object> data = new ArrayList<Object>();

    // if active, error while loading, apparently,
    // xml export not working 100% correctly then
    //public ArrayList<Object> getData() {return data;}
    //public void setData(ArrayList<Object> data) {this.data = data;}

    public UserDataTuple() {
    }

    /**
     * returns the number of elements in the tuple
     */
    public int size() {
        return data.size();
    }

    /**
     * clears all data of the tuple. This also sets the size to zero.
     */
    public void clear() {
        data.clear();
    }

    public void add(Object userData) {
        data.add(userData);
    }

    public Object getData(int i) {
        if (data.get(i) != null) {
            return data.get(i);
        } else {
            throw new RuntimeException("UserDataTuple: no data stored at position "
                    + i + " of tuple.");
        }
    }

    public String getSubset(int i) {
        Object o = getData(i);
        if (o instanceof String) {
            return (String) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a Subset, but " + o.getClass().getName());
        }
    }

    public I_UserNumber getNumberData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserNumber) {
            return (I_UserNumber) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserNumber, but " + o.getClass().getName());
        }
    }

    public I_UserVector getVectorData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserVector) {
            return (I_UserVector) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserVector, but " + o.getClass().getName());
        }
    }

    public I_UserMatrix getMatrixData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserMatrix) {
            return (I_UserMatrix) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserMatrix, but " + o.getClass().getName());
        }
    }

    public I_VRLUserLinkerNumberNumber getDataLinkerNumberNumber(int i) {
        Object o = getData(i);
        if (o instanceof I_VRLUserLinkerNumberNumber) {
            return (I_VRLUserLinkerNumberNumber) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a VRLUserLinkerNumberNumber, but " + o.getClass().getName());
        }
    }
}
