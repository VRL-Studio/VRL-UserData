/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import java.io.Serializable;

/**
 * A parameter group.
 * Used paramters controls model parameter.
 *
 * @author Night
 */
public class SkinModelSpecificParameters implements Serializable{
    private static final long serialVersionUID = 1L;

    private Double dlib;
    private Double dcor0;
    private Double dcor1;
    private Double depi;
    private Double ddon;
    private Double kcor0;
    private Double kcor1;
    private Double kepi;
    private Double kdon;
    private Double idon;

    public SkinModelSpecificParameters(Double dlib, Double dcor0, Double dcor1,
            Double depi, Double ddon, Double kcor0, Double kcor1, Double kepi,
            Double kdon, Double idon) {

        this.dlib = dlib;
        this.dcor0 = dcor0;
        this.dcor1 = dcor1;
        this.depi = depi;
        this.ddon = ddon;
        this.kcor0 = kcor0;
        this.kcor1 = kcor1;
        this.kepi = kepi;
        this.kdon = kdon;
        this.idon = idon;
    }

    /**
     * @return the dlib
     */
    public Double getDlib() {
        return dlib;
    }

    /**
     * @param dlib the dlib to set
     */
    public void setDlib(Double dlib) {
        this.dlib = dlib;
    }

    /**
     * @return the dcor0
     */
    public Double getDcor0() {
        return dcor0;
    }

    /**
     * @param dcor0 the dcor0 to set
     */
    public void setDcor0(Double dcor0) {
        this.dcor0 = dcor0;
    }

    /**
     * @return the dcor1
     */
    public Double getDcor1() {
        return dcor1;
    }

    /**
     * @param dcor1 the dcor1 to set
     */
    public void setDcor1(Double dcor1) {
        this.dcor1 = dcor1;
    }

    /**
     * @return the depi
     */
    public Double getDepi() {
        return depi;
    }

    /**
     * @param depi the depi to set
     */
    public void setDepi(Double depi) {
        this.depi = depi;
    }

    /**
     * @return the ddon
     */
    public Double getDdon() {
        return ddon;
    }

    /**
     * @param ddon the ddon to set
     */
    public void setDdon(Double ddon) {
        this.ddon = ddon;
    }

    /**
     * @return the kcor0
     */
    public Double getKcor0() {
        return kcor0;
    }

    /**
     * @param kcor0 the kcor0 to set
     */
    public void setKcor0(Double kcor0) {
        this.kcor0 = kcor0;
    }

    /**
     * @return the kcor1
     */
    public Double getKcor1() {
        return kcor1;
    }

    /**
     * @param kcor1 the kcor1 to set
     */
    public void setKcor1(Double kcor1) {
        this.kcor1 = kcor1;
    }

    /**
     * @return the kepi
     */
    public Double getKepi() {
        return kepi;
    }

    /**
     * @param kepi the kepi to set
     */
    public void setKepi(Double kepi) {
        this.kepi = kepi;
    }

    /**
     * @return the kdon
     */
    public Double getKdon() {
        return kdon;
    }

    /**
     * @param kdon the kdon to set
     */
    public void setKdon(Double kdon) {
        this.kdon = kdon;
    }

    /**
     * @return the idon
     */
    public Double getIdon() {
        return idon;
    }

    /**
     * @param idon the idon to set
     */
    public void setIdon(Double idon) {
        this.idon = idon;
    }
}
