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

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

/**
 *
 * @author andreasvogel
 */
public class UserSubsetView extends UserDataView {

    protected UserDataModel model;
    protected UserDataTupleType tuple;
    protected String name;
    protected JComboBox selectionView = null;
    protected Color defaultColor = null;
    protected boolean externTriggered;
    protected VTextField textView = null;
    protected boolean internalAdjustment = false;

    public UserSubsetView(String theName, UserDataModel theModel,
            UserDataTupleType theTuple) {
        this.name = theName;
        this.model = theModel;
        this.tuple = theTuple;

        externTriggered = model.isExternTriggered();

        if (externTriggered) {
            selectionView = new JComboBox();
            selectionView.setAlignmentX(Component.LEFT_ALIGNMENT);
            defaultColor = selectionView.getBackground();
            selectionView.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (selectionView.getSelectedItem() != null && !internalAdjustment) {
                        model.setData(selectionView.getSelectedItem());

                        if (model.getStatus() != UserDataModel.Status.INVALID) {
                            model.setStatus(UserDataModel.Status.VALID);
                        }
                        adjustView(model.getStatus());

                        tuple.setDataOutdated();
                        tuple.storeCustomParamData();
                    }
                }
            });
        } else {
            textView = new VTextField("");
            textView.setFont(new Font("SansSerif", Font.PLAIN, 11));
            textView.setAlignmentX(Component.LEFT_ALIGNMENT);

            int height = (int) textView.getMinimumSize().getHeight();
            textView.setSize(new Dimension(120, height));
            textView.setMinimumSize(new Dimension(120, height));
            textView.setMaximumSize(new Dimension(120, height));
            textView.setPreferredSize(new Dimension(120, height));
            textView.setEditable(true);

            textView.setAlignmentY(0.5f);
            textView.setAlignmentX(0.0f);

            textView.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (internalAdjustment) return;
                    model.setData(textView.getText());
                    tuple.setDataOutdated();
                    tuple.storeCustomParamData();
                }
            });


        }
    }

    @Override
    public Component getComponent() {
        if (externTriggered) {
            return selectionView;
        } else {
            return textView;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void adjustView(UserDataModel theModel) {
        this.model = theModel;
        externTriggered = model.isExternTriggered();

        if (textView != null) {
            textView.setText((String) model.getData());
        }
        
        if (selectionView != null) {

            internalAdjustment = true;

            if (model.getStatus() == UserDataModel.Status.INVALID) {
                selectionView.removeAllItems();
                selectionView.addItem("-- No Grid --");
            } else {
                selectionView.setSelectedItem(model.getData());
            }
            
            internalAdjustment = false;

            adjustView(model.getStatus());
        }
    }

    @Override
    public void adjustView(Object info) {
        
        internalAdjustment = true;

        selectionView.removeAllItems();
        if (info != null) {
            if (!(info instanceof UGXFileInfo))
                throw new RuntimeException("DataLinkerView: Passed data is not of required type UGXFileInfo.");
        
            for (int i = 0; i < ((UGXFileInfo)info).const__num_subsets(0, 0); ++i) {
                selectionView.addItem(((UGXFileInfo)info).const__subset_name(0, 0, i));
            }

            if (model != null) {
                selectionView.setSelectedItem(model.getData());
                adjustView(model.getStatus());
            }

            if (selectionView.getSelectedItem() == null) {
                selectionView.setSelectedIndex(0);
                model.setData(selectionView.getSelectedItem());
                model.setStatus(UserDataModel.Status.WARNING);
                adjustView(model.getStatus());
            }

        } else {
            selectionView.addItem("-- No Grid --");
            model.setStatus(Status.INVALID);
            adjustView(model.getStatus());
        }

        // set the maximum size to prefered size in order to avoid stretched drop-downs
        Dimension max = selectionView.getMaximumSize();
        Dimension pref = selectionView.getPreferredSize();
        max.height = pref.height;
        selectionView.setMaximumSize(max);

        selectionView.revalidate();

        internalAdjustment = false;
    }

    @Override
    public void adjustView(UserDataModel.Status status) {
        switch (status) {
            case VALID:
                selectionView.setBackground(defaultColor);
                break;
            case WARNING:
                selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                break;
            case INVALID:
            default:
                selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }

    @Override
    public void closeView() {
    }
}
