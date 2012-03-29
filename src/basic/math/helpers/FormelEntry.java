/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;

import java.io.Serializable;

/**<p>
 * FormelEntry contains the information of a part of a Formel.
 * </p><p>
 * FormalEntry stores the following:<br>
 * Is the entry editable or not. And if it is editable:<br>
 *  - The data of an entry e.g. the values of a matrix<br>
 *  - And the size of the entry e.g. matrix rows=n and cols=n and a scalar rows=1 and cols=1
 * </p>
 * @author Night
 */
public class FormelEntry implements Serializable{
    private static final long serialVersionUID = 1;
    
    /**
     *  the name of FormelEntry
     */
    private String formel;

    /**
     *  the short name of FormelEnty
     */
    private String shortName;

    /**
     *  contains the values of editable FormelEntry
     */
    private String data; 

    // Stores the size of the FormelEntry by default 0,0 just text wihtout data
    // 1,1 Scalar;  3,1 a vector of lenght 3
    private int rows=0;
    private int cols=0;

    /** Stores the information whether the FormalEntry should be editabel or not.
     *  If it is editable the data, the number of rows and columns can change.
     */
    private boolean editable;

    /**
     * @return the formel
     */
    public String getFormel() {
        return formel;
    }

    /**
     * @param formel the formel to set
     */
    public void setFormel(String formel) {
        this.formel = formel;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * @param cols the cols to set
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
