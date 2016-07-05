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

import edu.gcsc.vrl.userdata.util.DimensionUtil;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
public class UserDataWindowPane extends JPanel {

    private transient JTable tableView;
    private transient TableModel tableModel;
    private transient UserMathDataModel mathDataModel;
    private transient UserDataTupleType userDataTuple;
    private transient UserMathDataView mathDataView = null;
    private transient boolean internalAdjustment = false;

    public UserDataWindowPane(UserMathDataView view) {
        setBackground(new Color(0, 0, 0, 0));
        //setBorder(new EmptyBorder(5, 5, 5, 5));

        mathDataView = view;
        mathDataModel = mathDataView.getUserMathDataModel();
        userDataTuple = mathDataView.getUserDataTupleType();

        tableModel = createTableModel(mathDataModel.getDimension(), mathDataModel.getCategory());
        fillFromModel(tableModel, mathDataModel);
        tableView = createTableView(tableModel);

        add(tableView);
    }

    public class FocusField extends JTextField {

        public FocusField() {
            addFocusListener(
                    new FocusListener() {

                        @Override
                        public void focusGained(FocusEvent e) {
                            selectAll();
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                        }
                    });
        }
    }

    protected class DoubleCellEditor extends DefaultCellEditor {

        public DoubleCellEditor() {
            super(new FocusField());
            ((JTextField) getComponent()).setHorizontalAlignment(JTextField.RIGHT);
        }

        @Override
        public Object getCellEditorValue() {
            String stringValue = (String) super.getCellEditorValue();
            return new Double(stringValue);
        }

        @Override
        public boolean stopCellEditing() {
            String value = ((JTextField) getComponent()).getText();
            if (!value.equals("")) {
                try {
                    this.getCellEditorValue();
                } catch (Exception ex) {
                    Border border = BorderFactory.createLineBorder(Color.red, 1);
                    ((JComponent) getComponent()).setBorder(border);
                    return false;
                }
            }
            ((JComponent) getComponent()).setBorder(null);

            boolean res = super.stopCellEditing();

            if (internalAdjustment) {
                return res;
            }

            mathDataModel.setDataFromTable(tableModel);

            if (mathDataModel.getStatus() != UserDataModel.Status.INVALID) {
                mathDataModel.setStatus(UserDataModel.Status.VALID);
            }
            mathDataView.adjustView(mathDataModel.getStatus());
            mathDataView.updateToolTipText();
            userDataTuple.storeCustomParamData();

            return res;
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected,
                int rows,
                int cols) {
            JTextField tf = (JTextField) super.getTableCellEditorComponent(
                    table, value, isSelected, rows, cols);
            //Border border = BorderFactory.createLoweredBevelBorder();

            ((JTextField) getComponent()).setBorder(null);
            ((JTextField) getComponent()).selectAll();
            return tf;
        }
    }

    protected static class DoubleCellRenderer extends DefaultTableCellRenderer {

        public DoubleCellRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            value = String.format("%s", (Double) value);

            return super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
        }
    }

    protected static class DoubleTableModel extends AbstractTableModel {

        private Double[][] data = null;

        DoubleTableModel(int numRows, int numCols) {
            data = new Double[numRows][numCols];
        }

        @Override
        public int getColumnCount() {
            if (data.length > 0) {
                return data[0].length;
            } else {
                return 0;
            }
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return "";
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return Double.class;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = (Double) value;
            fireTableCellUpdated(row, col);
        }
    };

    /**
     * Method for creating the needed table model in constructur.
     *
     * @param category is the category of the underlying data structure.
     *
     * <br>e.g. </br> 
     * <br>for Matrix a dim x dim table should be created, </br>
     * <br>for Vector a dim x 1 table, </br>
     * <br>for Number a 1 x 1 table. </br>
     *
     */
    protected static TableModel createTableModel(int dim, UserDataModel.Category category) {

        switch (category) {
            case NUMBER:
            case COND_NUMBER:
                return new DoubleTableModel(1, 1);
            case VECTOR:
                return new DoubleTableModel(dim, 1);
            case MATRIX:
                return new DoubleTableModel(dim, dim);
            default:
                throw new IllegalArgumentException("Error in "
                        + UserDataWindowPane.class.getSimpleName() + ".createTableModel()."
                        + "Category need to be (COND_)NUMBER, VECTOR or MATRIX");
        }
    }

    protected JTable createTableView(TableModel model) {
        JTable theTableView = new JTable(model);
        theTableView.setColumnSelectionAllowed(false);
        theTableView.setRowSelectionAllowed(false);
        theTableView.setFocusable(true);
        theTableView.setDefaultRenderer(Double.class, new DoubleCellRenderer());
        theTableView.setDefaultEditor(Double.class, new DoubleCellEditor());
        return theTableView;
    }

    /**
     * Writes zero in all entries of the used table for the to be displayed
     * FormelEntry
     */
    protected static void fillWithZero(TableModel tableModel) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                tableModel.setValueAt(0.0, i, j);
            }
        }
    }

    public static void fillFromModel(TableModel tableModel, UserMathDataModel model) {

        // see docu to know which number stands for which UserDataModel type
        int type = DimensionUtil.getArrayDimension(model.getData());


        if (type == 2) {
            Double[][] data2 = (Double[][]) model.getData();

            for (int j = 0; j < data2.length; j++) {
                for (int i = 0; i < data2[j].length; i++) {
                    tableModel.setValueAt(data2[i][j], i, j);
                }
            }

        } else if (type == 1) {

            Double[] data1 = (Double[]) model.getData();

            for (int i = 0; i < data1.length; i++) {
                tableModel.setValueAt(data1[i], i, 0);
            }
        } else if (type == 0) {
            tableModel.setValueAt(model.getData(), 0, 0);
        } else {
            throw new IllegalStateException(UserDataWindowPane.class.getSimpleName()
                    + ".fillFromModel() have no implementation for the "
                    + "required data structure.");
        }
    }

    /**
     * @return the datamodel
     */
    public TableModel getTableModel() {
        return tableModel;
    }

    public void updateModel(UserMathDataModel model) {
        internalAdjustment = true;

        mathDataModel = model;

        remove(tableView);

        tableModel = createTableModel(model.getDimension(), model.getCategory());
        fillFromModel(tableModel, model);
        tableView = createTableView(tableModel);

        add(tableView);

        revalidate();

        internalAdjustment = false;
    }
}
