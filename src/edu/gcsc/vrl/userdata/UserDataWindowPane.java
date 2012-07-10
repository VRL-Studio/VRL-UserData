/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.NumberEditField;
import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
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
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataWindowPane extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public UserDataWindowPane(int dim, UserDataCategory category) {
        
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        createTableModel(dim, category);

        table = new JTable(getTableModel());

        fillWithZero();

        //Set up real input validation for the integer column.
        setUpNumberEditor(table);

        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);

        table.setFocusable(true);
        add(table);
    }

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
    protected void createTableModel(int dim, UserDataCategory category) {

        if (category.equals(UserDataCategory.NUMBER)
                || category.equals(UserDataCategory.COND_NUMBER)) {
            tableModel = new DefaultTableModel(1, 1);


        } else if (category.equals(UserDataCategory.VECTOR)) {
            tableModel = new DefaultTableModel(dim, 1);

        } else if (category.equals(UserDataCategory.MATRIX)) {
            tableModel = new DefaultTableModel(dim, dim);

        } else {
            throw new IllegalArgumentException("Error in "
                    + UserDataWindowPane.class.getSimpleName() + ".createTableModel().\n"
                    + "Category need to be (COND_)NUMBER, VECTOR or MATRIX");
        }

    }

    /**
     * Writes zero in all entries of the used table for the to be displayed
     * FormelEntry
     */
    private void fillWithZero() {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt(0.0, i, j);
            }
        }
    }

    /**
     * <p> Uses the logic from the class
     * <code>NumberEditField</code> to allow only accepted changings /
     * manipulations on the values of a
     * <code>FormelEntry</code> </p>
     *
     * @param table here are the several values of a FormelEntry displayed and
     * temporary stored
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

    /**
     * @return the datamodel
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        remove(table);
        table = new JTable(tableModel);
        this.tableModel = tableModel;
    }
}
