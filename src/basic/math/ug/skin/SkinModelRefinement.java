/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.skin;

import java.io.Serializable;

/**
 * A parameter group.
 * Used paramters controls refinement setting.
 *
 * @author Night
 */
public class SkinModelRefinement implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer baseLevel;
    private Integer anisoLevel;
    private Integer bndLevel;
    private Integer isoLevel;

    public SkinModelRefinement(Integer baseLevel, Integer anisoLevel, Integer bndLevel, Integer isoLevel) {
        this.baseLevel = baseLevel;
        this.anisoLevel = anisoLevel;
        this.bndLevel = bndLevel;
        this.isoLevel = isoLevel;
    }

    /**
     * @return the baseLevel
     */
    public Integer getBaseLevel() {
        return baseLevel;
    }

    /**
     * @param baseLevel the baseLevel to set
     */
    public void setBaseLevel(Integer baseLevel) {
        this.baseLevel = baseLevel;
    }

    /**
     * @return the anisoLevel
     */
    public Integer getAnisoLevel() {
        return anisoLevel;
    }

    /**
     * @param anisoLevel the anisoLevel to set
     */
    public void setAnisoLevel(Integer anisoLevel) {
        this.anisoLevel = anisoLevel;
    }

    /**
     * @return the bndLevel
     */
    public Integer getBndLevel() {
        return bndLevel;
    }

    /**
     * @param bndLevel the bndLevel to set
     */
    public void setBndLevel(Integer bndLevel) {
        this.bndLevel = bndLevel;
    }

    /**
     * @return the isoLevel
     */
    public Integer getIsoLevel() {
        return isoLevel;
    }

    /**
     * @param isoLevel the isoLevel to set
     */
    public void setIsoLevel(Integer isoLevel) {
        this.isoLevel = isoLevel;
    }



}
