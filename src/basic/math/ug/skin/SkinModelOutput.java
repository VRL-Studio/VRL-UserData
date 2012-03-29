/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.skin;

import java.io.Serializable;

/**
 * A parameter group.
 * Used paramters controls output setting.
 *
 * @author Night
 */
public class SkinModelOutput implements Serializable{
    private static final long serialVersionUID = 1L;

    private Boolean xdr;
    private Boolean vtk;

    public SkinModelOutput(Boolean xdr, Boolean vtk) {
        this.xdr = xdr;
        this.vtk = vtk;
    }

    /**
     * @return the xdr
     */
    public Boolean getXdr() {
        return xdr;
    }

    /**
     * @param xdr the xdr to set
     */
    public void setXdr(Boolean xdr) {
        this.xdr = xdr;
    }

    /**
     * @return the vtk
     */
    public Boolean getVtk() {
        return vtk;
    }

    /**
     * @param vtk the vtk to set
     */
    public void setVtk(Boolean vtk) {
        this.vtk = vtk;
    }



}
