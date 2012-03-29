/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.helpers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class MatrixPane extends JPanel{
    
    private JTable table;
    private DefaultTableModel datamodel;
    private Dimensions dim;

    public MatrixPane(Dimensions d) {
        dim = d;
        
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        datamodel = new DefaultTableModel(d.ordinal()+1, d.ordinal()+1);
        table = new JTable(datamodel);

        fillWithZero();

        //Set up real input validation for the integer column.
        setUpNumberEditor(table);

        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);

        table.setFocusable(true);
        add(table);
    }
    
    /**
     * Writes zero in all entries of the used table
     * for the to be displayed FormelEntry
     */
    private void fillWithZero() {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt(0, i, j);
            }
        }
    }
    
    /**<p>
     * Uses the logic from the class <code>NumberEditField</code> to allow
     * only accepted changings / manipulations on the values of a <code>FormelEntry</code>
     * </p>
     * @param table here are the several values of a FormelEntry displayed
     *        and temporary stored
     */
    private void setUpNumberEditor(JTable table) {
        //Set up the editor for the integer cells.
        final NumberEditField doubleField = new NumberEditField(0, 5);
        doubleField.setHorizontalAlignment(NumberEditField.RIGHT);

        DefaultCellEditor doubleEditor =
                new DefaultCellEditor(doubleField) {
                    //Override DefaultCellEditor's getCellEditorValue method
                    //to return an Integer, not a String:

                    @Override
                    public Object getCellEditorValue() {
                        return new Double(doubleField.getValue());
                    }

                    @Override
                    public Component getTableCellEditorComponent(
                            JTable table, Object value, boolean isSelected, int rows, int cols) {

                        Component result = super.getTableCellEditorComponent(
                                table, value, isSelected, rows, cols);

                        JTextField field = (JTextField) result;
                        field.selectAll();
                        return result;
                    }
                };
        table.setDefaultEditor(Object.class, doubleEditor);
    }

    
}
