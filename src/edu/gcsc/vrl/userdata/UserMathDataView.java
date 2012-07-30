/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VSwingUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author andreasvogel
 */
public class UserMathDataView extends UserDataView {

    protected UserMathDataModel model;
    protected UserDataTupleType tuple;
    protected String name;
    protected UserDataWindow window = null;
    protected VButton button = null;
    protected Color defaultColor = null;

    public UserMathDataModel getUserMathDataModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public UserDataTupleType getUserDataTupleType() {
        return tuple;
    }

    public void updateToolTipText() {
        
        button.setToolTipText(model.getToolTipText());
    }
    
    public UserMathDataView(String theName, UserDataModel theModel,
            UserDataTupleType theTuple) {
        this.name = theName;
        this.model = (UserMathDataModel)theModel;
        this.tuple = theTuple;
        button = new VButton(name);
        updateToolTipText();
        defaultColor = button.getBackground();
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (window == null || window.isDisposed()) {

                    window = new UserDataWindow(UserMathDataView.this);

                    tuple.getMainCanvas().addWindow(window);
                }

                button.setBackground(defaultColor);

                Point loc = VSwingUtil.getAbsPos(tuple);

                Point buttonLoc = new Point(loc.x + button.getX(),
                        loc.y + button.getY());

                window.setLocation(buttonLoc);

                tuple.getMainCanvas().setComponentZOrder(window, 0);
            }
        });
    }

    @Override
    public Component getComponent() {
        return button;
    }

    @Override
    public void updateView(UserDataModel theModel) {

        this.model = (UserMathDataModel)theModel;
        updateToolTipText();
        if (window != null) {
            window.updateWindow(model);
        }
    }

    @Override
    public void adjustData(UGXFileInfo info, boolean modelConsistent) {
        if (info != null) {

            if (window != null) {
                window.updateWindow(model);
            }

            if (modelConsistent) {
                button.setBackground(defaultColor);
            } else {
                button.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
            }
        } else {
            button.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                    TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }
    
    void setConsistentStateColor() {
        button.setBackground(defaultColor);
    }
}
