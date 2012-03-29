/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Contains the logic how to display the values of a FormelEntry e.g. a matrix or vector.
 * The visulation is done with a with the help of a JTable.
 * @author Night
 */
public class MatrixPane extends JPanel {

    private JTable table;
    private DefaultTableModel datamodel;
    private FormelEntry formelEntry;
    private MatrixWindow matrixWindow; // the matrixwindow where an instance will be used

    public MatrixPane(MatrixWindow matrixWindow, FormelEntry formelEntry, int rows, int cols) {

        setBackground(new Color(0, 0, 0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        datamodel = new DefaultTableModel(rows, cols);
        table = new JTable(datamodel);

        fillWithZero();

        //Set up real input validation for the integer column.
        setUpNumberEditor(table);

//        table.setDefaultRenderer(Double.class, new ColoredTableCellRenderer());
//        table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION  );
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);

        table.setFocusable(true);
        add(table);

        this.formelEntry = formelEntry;
        this.matrixWindow = matrixWindow;

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

    /**<p>
     * Returns a sorted string with the data of a <code>FormelEntry</code>.
     * Additional informations are added to each value of a <code>FormelEntry</code>.
     * The additional Information is for the use with UG necessary.
     * </p><p>
     * The folowing informations are added without whitespace:<br>
     * 1.)$<br>
     * 2.)the short name of the <code>FormelEntry</code><br>
     * 3.)row position x, of the value in e.g. the matrix<br>
     * 4.)col position y, of the value in e.g. the matrix<br>
     * </p><p>
     * The string is typical sorted how you could image it, first by rows (from 0 to n)
     * and each row is sorted by columns (from 0 to n).
     * </p>
     * @return the sorted String with all values of the FormelEntry
     */
    public String getData() {
        String result = "";

        int x = 0, y = 0;
        for (y = 0; y < table.getRowCount(); y++) {
            for (x = 0; x < table.getColumnCount(); x++) {


                table.editCellAt(y, x);
                result += "$" + getFormelEntry().getShortName() + x + y + " " +
                        table.getValueAt(y, x).toString() + " ";

            }
        }

        return result;
    }

    /**
     * Splits the given string into pieces by using whitespaces as cut position.
     * Removes the addtional information and writte the values in the correct
     * position of a JTable.
     *
     * @param s String which contains data and additional informations of a FormelEntry
     */
    public void setData(String s) {
        String[] werte = s.split("\\s");
        Vector<String> data = new Vector<String>();

        for (int i = 0; i < werte.length; i++) {

            if (((werte[i].substring(0, 1)).compareTo("$") != 0)) 
            {
                data.add(werte[i]);
            }
        }
        int i = 0;

        for (int y = 0; y < table.getRowCount(); y++) {
            for (int x = 0; x < table.getColumnCount(); x++) {
                //table.setValueAt(werte[2*i+1], y, x);
                table.setValueAt(data.get(i), y, x);
                i++;
            }
        }
    }

    /**
     * @return the formelEntry
     */
    public FormelEntry getFormelEntry() {
        return formelEntry;
    }
}
