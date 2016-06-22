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
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
public class DataLinkerWindow extends CanvasWindow implements Serializable {

    protected final class CodePanel {

        private transient Box outerBox = null;
        private transient VCodeEditor editor = null;
        private transient JLabel codeCommment;
        private transient Box codePane;
        private transient JComponent editBox;
        private transient JComboBox dimChooser;
    }
    private transient DataLinkerView dataLinkerView = null;
    private transient DataLinkerModel model = null;
    private transient JTabbedPane tabbedPane = null;
    protected boolean internalAdjustment = false;
    protected ArrayList<CodePanel> codePanels = new ArrayList<CodePanel>();

    public class PanelDocumentListener implements DocumentListener {

        CodePanel codePanel = null;
        int i;

        public PanelDocumentListener(CodePanel codePanel, int i) {
            this.codePanel = codePanel;
            this.i = i;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (internalAdjustment) {
                return;
            }
            model.setTheCode(i, codePanel.editor.getEditor().getText());
            storeData();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (internalAdjustment) {
                return;
            }
            model.setTheCode(i, codePanel.editor.getEditor().getText());
            storeData();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
        }
    };

    public class PanelActionListener implements ActionListener {

        CodePanel codePanel = null;

        public PanelActionListener(CodePanel codePanel) {
            this.codePanel = codePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (internalAdjustment) {
                return;
            }

            int dim = (Integer) codePanel.dimChooser.getSelectedItem();

            codePanel.codeCommment.setText(getCodeComment(dim, model.getCategory()));

            codePanel.editBox.removeAll();
            codePanel.editBox.add(codePanel.codePane);

            storeData();
            revalidate();
        }
    };

    private void initCodePanel(CodePanel codePanel, int i) {

         // create an outer box, where we put in all other components
        codePanel.outerBox = Box.createVerticalBox();

        // create a box for the menu
        Box menuBox = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);
        menuBox.setBorder(border1);
        codePanel.outerBox.add(menuBox);

        // setup dim chooser
        Integer[] dims = {1, 2, 3};
        codePanel.dimChooser = new JComboBox(dims);
        Dimension prefSize = codePanel.dimChooser.getPreferredSize();
        Dimension maxSize = codePanel.dimChooser.getMaximumSize();
        maxSize.width = prefSize.width;
        codePanel.dimChooser.setMaximumSize(maxSize);
        codePanel.dimChooser.setPrototypeDisplayValue(3);
        codePanel.dimChooser.setSelectedItem(model.getDimension());

        // add dim chooser id needed
        if (!model.isExternTriggered()) {
            menuBox.add(codePanel.dimChooser);
        }

        // here is the changed view added
        codePanel.editBox = Box.createHorizontalBox();
        codePanel.outerBox.add(codePanel.editBox);

        codePanel.editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);
        codePanel.editor.getEditor().getDocument().addDocumentListener(new PanelDocumentListener(codePanel, i));

        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(codePanel.editor);
        vScrollPane.setVisible(true);
        vScrollPane.setMaximumSize(new Dimension(680, 800));
        vScrollPane.setMinimumSize(new Dimension(200, 100));

        VContainer editorPane = new VContainer(codePanel.editor);
        editorPane.setMinPreferredWidth(300);
        editorPane.setMinPreferredHeight(200);
        editorPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        codePanel.codeCommment = new JLabel("", JLabel.LEFT);

        codePanel.codePane = Box.createVerticalBox();
        codePanel.codePane.add(codePanel.codeCommment);
        codePanel.codePane.add(editorPane);

        codePanel.dimChooser.addActionListener(new PanelActionListener(codePanel));
    }

    public DataLinkerWindow(DataLinkerView view) {

        super(view.getName(), view.getUserDataTupleType().getMainCanvas());

        dataLinkerView = view;
        model = dataLinkerView.getDataLinkerModel();

        // must be visible
        setVisible(true);

        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        CodePanel fctPanel = new CodePanel();
        codePanels.add(fctPanel);
        initCodePanel(fctPanel, 0);
        tabbedPane.addTab("Function", fctPanel.outerBox);

        CodePanel derivPanel = new CodePanel();
        codePanels.add(derivPanel);
        initCodePanel(derivPanel, 1);
        tabbedPane.addTab("Derivative", derivPanel.outerBox);

        // all done, now update the window with the current data in the model
        updateWindow(model);
    }

    protected void storeData() {
        if (model.getStatus() != UserDataModel.Status.INVALID) {
            model.setStatus(UserDataModel.Status.VALID);
        }
        dataLinkerView.adjustView(model.getStatus());
        dataLinkerView.updateToolTipText();
        dataLinkerView.getUserDataTupleType().storeCustomParamData();
    }

    /**
     * Stores all informations which are visualized in the UserDataWindow
     * in a corresponding UserDataModel and calls
     * <code>storeCustomParamData()</code>.
     *
     * @see #storeCustomParamData()
     */
    public void updateModel(DataLinkerModel model) {

        // copy code from window into model
        for (int i = 0; i < codePanels.size(); ++i) {
            model.setTheCode(i, codePanels.get(i).editor.getEditor().getText());

        }

        storeData();
    }

    /**
     * Get all informations which value should be visualized in the
     * UserDataWindow
     * from the corresponding UserDataModel and sets the values in the window.
     *
     * @param model that stores the informations that should be visualized
     */
    final public void updateWindow(DataLinkerModel model) {
        internalAdjustment = true;

        for (int i = 0; i < codePanels.size(); ++i) {

            codePanels.get(i).editBox.removeAll();
            codePanels.get(i).editor.getEditor().setText(model.getTheCode(i));

            codePanels.get(i).editBox.add(codePanels.get(i).codePane);

            int dim = model.getDimension();
            UserDataModel.Category cat = model.getCategory();
            codePanels.get(i).dimChooser.setSelectedItem(dim);
            codePanels.get(i).codeCommment.setText(getCodeComment(dim, cat));
        }
        revalidate();
        internalAdjustment = false;
    }

    /**
     * @return the model
     */
    public DataLinkerModel getModel() {
        return model;
    }

    protected static String getCodeComment(int dim, UserDataModel.Category cat) {

        String[] spaceParam = {"x", "y", "z"};

        String Text = "<html><left>";
        Text += "<b>Parameters: c, </b> ";
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
