/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.skin;

import java.io.Serializable;

/**
 * A parameter group.
 * Used paramters controls time setting.
 *
 * @author Night
 */
public class SkinModelExecute implements Serializable{
    private static final long serialVersionUID = 1L;

    private Double dt;
    private Double kappa;
    private Long endtime;
    private Long maxSteps;

    public SkinModelExecute(Double dt, Double kappa, Long endtime, Long maxSteps) {
        this.dt = dt;
        this.kappa = kappa;
        this.endtime = endtime;
        this.maxSteps = maxSteps;
    }

    /**
     * @return the dt
     */
    public Double getDt() {
        return dt;
    }

    /**
     * @param dt the dt to set
     */
    public void setDt(Double dt) {
        this.dt = dt;
    }

    /**
     * @return the kappa
     */
    public Double getKappa() {
        return kappa;
    }

    /**
     * @param kappa the kappa to set
     */
    public void setKappa(Double kappa) {
        this.kappa = kappa;
    }

    /**
     * @return the endtime
     */
    public Long getEndtime() {
        return endtime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    /**
     * @return the maxSteps
     */
    public Long getMaxSteps() {
        return maxSteps;
    }

    /**
     * @param maxSteps the maxSteps to set
     */
    public void setMaxSteps(Long maxSteps) {
        this.maxSteps = maxSteps;
    }



}
