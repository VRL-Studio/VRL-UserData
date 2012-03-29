/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import basic.math.ug.equation.helpers.PickUniverseCreator;
import basic.math.ug.equation.*;
import java.awt.event.ActionEvent;

/**
 * The settings of the RotateBehavior and the TranslateBehavior were set
 * depending in which mode the SelectionButtonGroup is.
 *
 * The SelectionButtonGroup is part of the controlling logic which allows only
 * the actions to be performed which button/mode is activated.
 *
 * @author night
 */
public class SelectionButtonGroup extends ToolButtonGroup {

    private PickUniverseCreator universe;
    private boolean universeActive; // used in PickArrayTypeRep...

    public SelectionButtonGroup(PickUniverseCreator universe) {
        addActionListener(this);
        this.universe = universe;
    }

    /**
     *  If a member of the group fires the ActionEvent "mode-changed"
     *  the specific settings for this member will be set and
     *  the buttonMode in KeyMouseListener will be set correct.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if (e.getActionCommand().equals("mode-changed") && isUniverseActive()) {

            if (getMode().equals(ToolButtonMode.TRANSFORMATION)) {

                universe.getRotateBehavior().setFactor(0.01f);
                universe.getTranslateBehavior().setFactor(0.05);
            }

            if (getMode().equals(ToolButtonMode.SELECTION)) {

                universe.getRotateBehavior().setFactor(0.f);
                universe.getTranslateBehavior().setFactor(0);
            }

            if (getMode().equals(ToolButtonMode.PAINT)) {

                universe.getRotateBehavior().setFactor(0.f);
                universe.getTranslateBehavior().setFactor(0);
            }
        }
    }

    /**
     * @return the universeActive
     */
    public boolean isUniverseActive() {
        return universeActive;
    }

    /**
     * @param universeActive the universeActive to set
     */
    public void setUniverseActive(boolean universeActive) {
        this.universeActive = universeActive;
    }
}
