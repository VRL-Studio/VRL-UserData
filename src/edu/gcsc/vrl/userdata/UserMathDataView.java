/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VSwingUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ToolTipManager;

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
    protected Color defaultTextColor = null;

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
        this.model = (UserMathDataModel) theModel;
        this.tuple = theTuple;
        button = new VButton(name);
        updateToolTipText();
        defaultColor = button.getBackground();
        defaultTextColor = button.getForeground();
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (window == null || window.isDisposed()) {

                    window = new UserDataWindow(UserMathDataView.this);

                    window.getTitleBar().addMouseListener(new MouseAdapter() {

                        @Override
                        public void mousePressed(MouseEvent e) {
                            button.setForeground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                                    CanvasWindow.UPPER_ACTIVE_TITLE_COLOR_KEY));
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            button.setForeground(defaultTextColor);
                        }
                    });

                    tuple.getMainCanvas().addWindow(window);
                }

                if (model.getStatus() != UserDataModel.Status.INVALID) {
                    model.setStatus(Status.VALID);
                }
                adjustView(model.getStatus());

                Point loc = VSwingUtil.getAbsPos(tuple);

                Point buttonLoc = new Point(loc.x + button.getX(),
                        loc.y + button.getY());

                window.setLocation(buttonLoc);

                tuple.getMainCanvas().setComponentZOrder(window, 0);
            }
        });

        // tuning tooltip to be shown all the time on mouse-over
        button.addMouseListener(new MouseAdapter() {

            private int defaultDismissDelay;
            private int defaultInitialDelay;
            private int defaultReshowDelay;

            @Override
            public void mouseEntered(MouseEvent me) {
                defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
                defaultInitialDelay = ToolTipManager.sharedInstance().getInitialDelay();
                defaultReshowDelay = ToolTipManager.sharedInstance().getReshowDelay();
                ToolTipManager.sharedInstance().setDismissDelay(60000000);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                ToolTipManager.sharedInstance().setReshowDelay(0);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(defaultDismissDelay);
                ToolTipManager.sharedInstance().setInitialDelay(defaultInitialDelay);
                ToolTipManager.sharedInstance().setReshowDelay(defaultReshowDelay);
            }
        });

    }

    @Override
    public Component getComponent() {
        return button;
    }

    @Override
    public void adjustView(UserDataModel theModel) {

        this.model = (UserMathDataModel) theModel;

        updateToolTipText();

        adjustView(model.getStatus());

        if (window != null) {
            window.updateWindow(model);
        }
    }

    @Override
    public void adjustView(Object info) {
        if (info != null) {
            if (window != null) {
                window.updateWindow(model);
            }
        }

        updateToolTipText();

        adjustView(model.getStatus());

        if (window != null) {
            window.updateWindow(model);
        }
    }

    @Override
    public void adjustView(Status status) {
        switch (status) {
            case VALID:
                button.setBackground(defaultColor);
                break;
            case WARNING:
                button.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                break;
            case INVALID:
            default:
                button.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }

    @Override
    public void closeView() {
        if (window != null && !window.isDisposed()) {
            window.close();
            window = null;
        }
    }
}
