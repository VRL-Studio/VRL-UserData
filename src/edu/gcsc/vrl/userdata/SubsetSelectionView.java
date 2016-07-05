/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.visual.Canvas;

/**
 *
 * @author markus
 */
public abstract class SubsetSelectionView extends UserDataView
{
    public abstract String getName();
    public abstract Canvas getCanvas();
    public abstract SubsetSelectionModel getModel();
    public abstract String updateToolTipText();
}
