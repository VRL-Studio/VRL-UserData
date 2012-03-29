/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import java.io.File;
import java.io.Serializable;

/**
 * A parameter group.
 * Used paramters controls geometry setting.
 *
 * @author Night
 */
public class SkinModelGeometry implements Serializable{
    private static final long serialVersionUID = 1L;

    private File lgmFile;
    private GeomDefSelection geomDefSel1;
    private GeomDefSelection geomDefSel2;
    private File finiteconc;
    private File finitecor;

    public SkinModelGeometry(File lgmFile, GeomDefSelection geomDefSel1, GeomDefSelection geomDefSel2, File finiteconc, File finitecor) {
        this.lgmFile = lgmFile;
        this.geomDefSel1 = geomDefSel1;
        this.geomDefSel2 = geomDefSel2;
        this.finiteconc = finiteconc;
        this.finitecor = finitecor;
    }

    /**
     * @return the lgmFile
     */
    public File getLgmFile() {
        return lgmFile;
    }

    /**
     * @param lgmFile the lgmFile to set
     */
    public void setLgmFile(File lgmFile) {
        this.lgmFile = lgmFile;
    }

    /**
     * @return the geomDefSel1
     */
    public GeomDefSelection getGeomDefSel1() {
        return geomDefSel1;
    }

    /**
     * @param geomDefSel1 the geomDefSel1 to set
     */
    public void setGeomDefSel1(GeomDefSelection geomDefSel1) {
        this.geomDefSel1 = geomDefSel1;
    }

    /**
     * @return the geomDefSel2
     */
    public GeomDefSelection getGeomDefSel2() {
        return geomDefSel2;
    }

    /**
     * @param geomDefSel2 the geomDefSel2 to set
     */
    public void setGeomDefSel2(GeomDefSelection geomDefSel2) {
        this.geomDefSel2 = geomDefSel2;
    }

    /**
     * @return the finiteconc
     */
    public File getFiniteconc() {
        return finiteconc;
    }

    /**
     * @param finiteconc the finiteconc to set
     */
    public void setFiniteconc(File finiteconc) {
        this.finiteconc = finiteconc;
    }

    /**
     * @return the finitecor
     */
    public File getFinitecor() {
        return finitecor;
    }

    /**
     * @param finitecor the finitecor to set
     */
    public void setFinitecor(File finitecor) {
        this.finitecor = finitecor;
    }
}
