/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012–2016 Goethe Universität Frankfurt am Main, Germany
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

import eu.mihosoft.vrl.visual.CanvasWindow;
import java.io.Serializable;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author markus
 */
class SubsetSelectionWindow extends CanvasWindow implements Serializable
{
    private transient Box outerBox = null;
    private transient UserDataWindowPane constantPane = null;
    private transient JComponent editBox;
    private transient SubsetSelectionView ssView = null;
    private transient SubsetSelectionModel ssModel = null;
    protected boolean internalAdjustment = false;

    public SubsetSelectionWindow(SubsetSelectionView view)
    {
        super(view.getName(), view.getCanvas());

        ssView = view;
        ssModel = ssView.getModel();

        // must be visible
        setVisible(true);

        // create an outer box, where we put in all other components
        outerBox = Box.createVerticalBox();
        add(outerBox);

        // create a box for the menu
        Box menuBox = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);
        menuBox.setBorder(border1);
        outerBox.add(menuBox);
        
        // here, the changed view is added
        editBox = Box.createHorizontalBox();
        outerBox.add(editBox);
        
        // JList instead?
        
        /*
        constantPane = new UserDataWindowPane(ssView);
        
        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(editor);
        vScrollPane.setVisible(true);
        vScrollPane.setMaximumSize(new Dimension(680, 800));
        vScrollPane.setMinimumSize(new Dimension(200, 100));

        VContainer editorPane = new VContainer(editor);
        editorPane.setMinPreferredWidth(300);
        editorPane.setMinPreferredHeight(200);
        editorPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        codeCommment = new JLabel("", JLabel.LEFT);

        codePane = Box.createVerticalBox();
        codePane.add(codeCommment);
        codePane.add(editorPane);
        */
        // all done, now update the window with the current data in the model
        updateWindow(ssModel);
    }

    protected void storeData()
    {
        if (ssModel.getStatus() != UserDataModel.Status.INVALID) ssModel.setStatus(UserDataModel.Status.VALID);
        
        ssView.adjustView(ssModel.getStatus());
        ssView.updateToolTipText();
        
        // What does this do?
        //ssView.getUserDataTupleType().storeCustomParamData();
    }

    /**
     * Stores all informations which are visualized in the UserDataWindow
     * in a corresponding UserDataModel and calls
     * <code>storeCustomParamData()</code>.
     *
     * @see #storeCustomParamData()
     */
    public void updateModel(UserMathDataModel model)
    {
        // copy data from window into model
        
        // TODO: replace this
        //if (!model.isCondition()) model.setDataFromTable(constantPane.getTableModel());
        
        storeData();
    }

    /**
     * Get all informations which value should be visualized in the
     * SubsetSelectionWindow
     * from the corresponding UserDataModel and sets the values in the window.
     *
     * @param model that stores the informations that should be visualized
     */
    final public void updateWindow(SubsetSelectionModel model)
    {
        internalAdjustment = true;
        
        // TODO: anpassen
        //constantPane.updateModel(ssModel);

        revalidate();
        
        internalAdjustment = false;
    }

    /**
     * @return the model
     */
    public SubsetSelectionModel getModel()
    {
        return ssModel;
    }
}
