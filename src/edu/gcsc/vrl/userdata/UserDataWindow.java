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
package edu.gcsc.vrl.userdata;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import eu.mihosoft.vrl.lang.CompilerProvider;
import eu.mihosoft.vrl.lang.visual.EditorProvider;
import eu.mihosoft.vrl.visual.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
public class UserDataWindow extends CanvasWindow implements Serializable {

    private transient Box outerBox = null;
    private transient UserDataWindowPane constantPane = null;
    private transient VCodeEditor editor = null;
    private transient JLabel codeCommment;
    private transient Box codePane;
    private transient JComponent editBox;
    private transient JComboBox dimChooser;
    protected transient JRadioButton constant;
    protected transient JRadioButton code;
    private transient UserMathDataView mathDataView = null;
    private transient UserMathDataModel model = null;
    protected boolean internalAdjustment = false;

    public UserDataWindow(UserMathDataView view) {

        super(view.getName(), view.getUserDataTupleType().getMainCanvas());

        mathDataView = view;
        model = mathDataView.getUserMathDataModel();

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

        // setup dim chooser
        Integer[] dims = {1, 2, 3};
        dimChooser = new JComboBox(dims);
        Dimension prefSize = dimChooser.getPreferredSize();
        Dimension maxSize = dimChooser.getMaximumSize();
        maxSize.width = prefSize.width;
        dimChooser.setMaximumSize(maxSize);
        dimChooser.setPrototypeDisplayValue(3);
        dimChooser.setSelectedItem(model.getDimension());

        // add dim chooser id needed
        if (!model.isExternTriggered()) {
            menuBox.add(dimChooser);
        }

        //Group of radio buttons.
        constant = new JRadioButton("Constant");
        code = new JRadioButton("Code");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(constant);
        buttonGroup.add(code);

        menuBox.add(constant);
        menuBox.add(code);

        // here is the changed view added
        editBox = Box.createHorizontalBox();
        outerBox.add(editBox);

        constantPane = new UserDataWindowPane(mathDataView);

        editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);
        editor.getEditor().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (internalAdjustment) {
                    return;
                }
                model.setCode(editor.getEditor().getText());
                storeData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (internalAdjustment) {
                    return;
                }
                model.setCode(editor.getEditor().getText());
                storeData();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }
        });

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

        // all done, now update the window with the current data in the model
        updateWindow(model);

        dimChooser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (internalAdjustment) {
                    return;
                }

                editBox.remove(constantPane);
                int dim = (Integer) dimChooser.getSelectedItem();

                model.setDimensionWithAdjust(dim);
                constantPane.updateModel(model);
                codeCommment.setText(getCodeComment(dim, model.getCategory()));

                if (constant.isSelected()) {
                    editBox.removeAll();
                    editBox.add(constantPane);
                } else {
                    editBox.removeAll();
                    editBox.add(codePane);
                }

                storeData();
                revalidate();
            }
        });

        constant.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (internalAdjustment) {
                    return;
                }
                if (constant.isSelected()) {
                    editBox.add(constantPane);
                    editBox.remove(codePane);
                    model.setInputType(UserMathDataModel.InputType.CONSTANT);

                    storeData();
                    revalidate();
                }
            }
        });

        code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (internalAdjustment) {
                    return;
                }
                if (code.isSelected()) {
                    editBox.remove(constantPane);
                    editBox.add(codePane);
                    model.setInputType(UserMathDataModel.InputType.CODE);

                    storeData();
                    revalidate();
                }
            }
        });
    }

    protected void storeData() {
        if (model.getStatus() != UserDataModel.Status.INVALID) {
            model.setStatus(UserDataModel.Status.VALID);
        }
        mathDataView.adjustView(model.getStatus());
        mathDataView.updateToolTipText();
        mathDataView.getUserDataTupleType().storeCustomParamData();
    }

    /**
     * Stores all informations which are visualized in the UserDataWindow
     * in a corresponding UserDataModel and calls
     * <code>storeCustomParamData()</code>.
     *
     * @see #storeCustomParamData()
     */
    public void updateModel(UserMathDataModel model) {

        if (constant.isSelected()) {
            model.setInputType(UserMathDataModel.InputType.CONSTANT);
        } else if (code.isSelected()) {
            model.setInputType(UserMathDataModel.InputType.CODE);
        } else {
            throw new RuntimeException("UserDataWindow: Selection wrong.");
        }

        // copy dimension into model, if selectable
        if (!model.isExternTriggered()) {
            model.setDimensionWithAdjust((Integer) dimChooser.getSelectedItem());
        }

        // copy table data from window into model
        if (!model.isCondition()) {
            model.setDataFromTable(constantPane.getTableModel());
        }

        // copy code from window into model
        model.setCode(editor.getEditor().getText());

        storeData();
    }

    /**
     * Get all informations which value should be visualized in the
     * UserDataWindow
     * from the corresponding UserDataModel and sets the values in the window.
     *
     * @param model that stores the informations that should be visualized
     */
    final public void updateWindow(UserMathDataModel model) {
        internalAdjustment = true;

        editBox.removeAll();
        constantPane.updateModel(model);
        editor.getEditor().setText(model.getCode());

        if (model.isCondition()) {
            constant.setEnabled(false);
            constant.setVisible(false);
            code.setSelected(true);
            code.setVisible(false);
        }

        switch (model.getInputType()) {
            case CONSTANT:
                constant.setSelected(true);
                editBox.add(constantPane);
                break;
            case CODE:
                code.setSelected(true);
                editBox.add(codePane);
                break;
            default:
                throw new RuntimeException("UserDataWindow: InputType wrong.");
        }

        int dim = model.getDimension();
        UserDataModel.Category cat = model.getCategory();
        dimChooser.setSelectedItem(dim);
        codeCommment.setText(getCodeComment(dim, cat));

        revalidate();
        internalAdjustment = false;
    }

    /**
     * @return the model
     */
    public UserMathDataModel getModel() {
        return model;
    }

    protected static String getCodeComment(int dim, UserDataModel.Category cat) {

        String[] spaceParam = {"x", "y", "z"};

        String Text = "<html><left>";
        Text += "<b>Parameters:</b> ";
        for (int d = 0; d < dim; d++) {
            if (d > 0) {
                Text += ", ";
            }
            Text += spaceParam[d];
        }
        Text += ", t, si";
        Text += "<br>";

        switch (cat) {
            case NUMBER:
                Text += "<b>Return:</b> value (as double)";
                break;
            case VECTOR:
                Text += "<b>Return:</b> [";
                for (int d = 0; d < dim; d++) {
                    if (d > 0) {
                        Text += ", ";
                    }
                    Text += "<b>v</b><font size=-1><sub>" + spaceParam[d] + "</sub></font>";
                }
                Text += "]";
                break;

            case MATRIX:
                Text += "<table border=\"0\" cellspacing=\"0\">";
                Text += "<tr>";
                Text += "<td><b>Return:</b></td>";
                Text += "<td>[</td><td>";
                for (int d1 = 0; d1 < dim; d1++) {
                    Text += "[";
                    for (int d2 = 0; d2 < dim; d2++) {
                        if (d2 > 0) {
                            Text += ", ";
                        }
                        Text += "<b>D</b><font size=-2>" + spaceParam[d1] + spaceParam[d2] + "</font>";
                    }
                    if (d1 < dim - 1) {
                        Text += "],</td><td></td></tr> <tr><td></td><td></td><td>";
                    } else {
                        Text += "]</td><td>]</td></tr>";
                    }
                }
                Text += "<table>";
                break;
        }
        Text += "</left></html>";

        return Text;
    }
}
