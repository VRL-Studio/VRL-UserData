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
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.dialogs.FileDialogManager;
import eu.mihosoft.vrl.io.VFileFilter;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import groovy.lang.Script;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = String.class, input = true, output = false, style = "ugx-load-dialog")
public class LoadUGXFileStringType extends TypeRepresentationBase {

    /// the text field to display
    private VTextField input;
    /// filter to restrict to ugx file
    private VFileFilter fileFilter = new VFileFilter();
    /// the current ugx_tag
    private String ugx_tag = null;

    /**
     * Constructor.
     */
    public LoadUGXFileStringType() {

        // create a Box and set it as layout
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
        setLayout(layout);
        setLayoutType(LayoutType.STATIC);

        // set the name label
        nameLabel.setText("File Name (*.ugx):");
        nameLabel.setAlignmentX(0.0f);
        add(nameLabel);
        
        // elements are horizontally aligned
        Box horizBox = Box.createHorizontalBox();
        horizBox.setAlignmentX(LEFT_ALIGNMENT);
        add(horizBox);
        
        // create input field
        input = new VTextField(this, "");
        input.setHorizontalAlignment(JTextField.RIGHT);
        int height = (int) this.input.getMinimumSize().getHeight();
        input.setSize(new Dimension(120, height));
        input.setMinimumSize(new Dimension(120, height));
        input.setMaximumSize(new Dimension(120, height));
        input.setPreferredSize(new Dimension(120, height));
        input.setEditable(true);
        input.setAlignmentY(0.5f);
        input.setAlignmentX(0.0f);
        input.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setViewValue(input.getText());
            }
        });
        horizBox.add(input);

        // hide connector, since no external data allowed
        setHideConnector(true);

        // set to ugx ending only
        ArrayList<String> endings = new ArrayList<String>();
        endings.add("ugx");
        fileFilter.setAcceptedEndings(endings);
        fileFilter.setDescription("*.ugx");

        // create a file manager
        final FileDialogManager fileManager = new FileDialogManager();

        // create a load button
        JButton button = new JButton("...");
        button.setMaximumSize(new Dimension(50, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File directory = null;
                if (getViewValueWithoutValidation() != null) {
                    directory = new File(getViewValueWithoutValidation().toString());
                    if (!directory.isDirectory()) {
                        directory = directory.getParentFile();
                    }
                }

                File file = fileManager.getLoadFile(getMainCanvas(),
                        directory, fileFilter, false);

                if (file != null) {
                    setViewValue(file.toString());
                }
            }
        });

        horizBox.add(button);
    }

    @Override
    public void setViewValue(Object o) {
        input.setText(o.toString());
        input.setCaretPosition(input.getText().length());
        input.setToolTipText(o.toString());
        input.setHorizontalAlignment(JTextField.RIGHT);

        //  Here we inform the Singleton, that the file has been scheduled
        notifyLoadUGXFileObservable();
    }

    @Override
    public Object getViewValue() {
        return input.getText();
    }

    @Override
    public void emptyView() {
        input.setText("");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void evaluationRequest(Script script) {
        super.evaluationRequest(script);

        Object property = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("ugx_tag")) {
                property = script.getProperty("ugx_tag");
            }

            if (property != null) {
                ugx_tag = (String) property;
            }
        }

        if (ugx_tag == null) {
            getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option",
                    "ParamInfo for ugx-subset-selection requires ugx_tag in options",
                    getConnector(), MessageType.ERROR);
        }

    }

    @Override
    public void addedToMethodRepresentation() {
        super.addedToMethodRepresentation();

        // register at Observable using ugx_tag
        notifyLoadUGXFileObservable();
    }
 
    protected void notifyLoadUGXFileObservable() {
        File file = new File(input.getText());
            int objectID = this.getParentMethod().getParentObject().getObjectID();
            int windowID = 0;

        //  Here we inform the Singleton, that the file no scheduled
        if (!file.getAbsolutePath().isEmpty() && file.isFile()) {
            String msg = LoadUGXFileObservable.getInstance().setSelectedFile(file, ugx_tag, objectID, windowID);

            if (!msg.isEmpty() && !getMainCanvas().isLoadingSession()) {
                getMainCanvas().getMessageBox().addMessage("Invalid ugx-File",
                        msg, getConnector(), MessageType.ERROR);
            }

        } else {
            LoadUGXFileObservable.getInstance().setInvalidFile(ugx_tag, objectID, windowID);
            
            if (!input.getText().isEmpty() && !getMainCanvas().isLoadingSession()) {
                getMainCanvas().getMessageBox().addMessage("Invalid ugx-File",
                        "Specified filename invalid: " + file.toString(),
                        getConnector(), MessageType.ERROR);

            }
        }
    }

    @Override
    public String getValueAsCode() {
        return "\""
                + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
    }
}
