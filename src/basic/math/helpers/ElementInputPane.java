/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import basic.math.elements.interfaces.VisualElementInterface;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.StyleChangedListener;
import eu.mihosoft.vrl.visual.Style;
import eu.mihosoft.vrl.visual.TransparentPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Contains the logic how to display the values/entries of a VisualElement e.g.
 * a matrix or vector.
 * The visualization is done with a with the help of a JTable.
 *
 * @author Night
 */
public class ElementInputPane extends TransparentPanel {

    /**
     * If the Element has a third dimension, this variable contains the information
     * at which position/deepness we are in rispect of the third dimensions
     */
    private Integer elementCurrentDeep;
    /**
     * the table show the data/entries of the element in the first two dimension
     */
    private JTable table;
    /**
     * the data model which is used of the table. The model contanis the information
     * how many cols and rows the table need to have depending on the element
     * that should be visualized.
     */
    private DefaultTableModel datamodel;
    /**
     * the visual element which data should be shown.
     */
    private VisualElementInterface visualElement;

    /**
     * @param elementInputWindow ElementInputWindow is needed to know the current
     * style of VRL and adapt this ElementInputPane
     *
     * @param visualElement contains the needed data element
     */
    public ElementInputPane(ElementInputWindow elementInputWindow, VisualElementInterface visualElement) {

        elementCurrentDeep = 0;

        if (visualElement.getVisualDimensions().length > 1) {

            datamodel = new DefaultTableModel(
                    visualElement.getVisualDimensions()[0],
                    visualElement.getVisualDimensions()[1]);

        } else if (visualElement.getVisualDimensions().length == 1) {

            datamodel = new DefaultTableModel(
                    visualElement.getVisualDimensions()[0],
                    1);
        }

        table = new SimpleTable(datamodel);

        setUpNumberEditor(table);

        //allow only selecting/changing one cell
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setFocusable(true);

        //add table to pane
        add(table);

        //set default color config for table
        updateColorValues(elementInputWindow.getMainCanvas().getStyle());

        //add a VRL-Studio StyleListener for adapting the table colors by changing the style int the vrl-studio
        elementInputWindow.getMainCanvas().getStyleManager().addStyleChangedListener(
                new StyleChangedListener() {

                    @Override
//                    public void styleChanged(CanvasStyle style) {
                            public void styleChanged(Style style) {
                        updateColorValues(style);
                    }
                });
    }

//    /**
//     * This method sets the color configuration for the used table.
//     * @param style the currently used style in VRL-Studio
//     */
//    private void updateColorValues(CanvasStyle style) {
//        Color background = style.getObjectUpperBackground();
//
//        background = new Color(background.getRed(), background.getGreen(), background.getBlue(), 45);
//
//        table.setBackground(background);
//        table.setForeground(style.getTextColor());
//    }
    
    /**
     * This method sets the color configuration for the used table.
     * @param style the currently used style in VRL-Studio
     */
    private void updateColorValues(Style style) {
        Color background = style.getBaseValues().getColor(Canvas.BACKGROUND_COLOR_KEY);

        background = new Color(background.getRed(), background.getGreen(), background.getBlue(), 45);

        table.setBackground(background);
        table.setForeground(style.getBaseValues().getColor(Canvas.TEXT_COLOR_KEY));
    }

    /**
     * Uses the logic from the class <code>NumberEditField</code> to allow
     * only accepted changings / manipulations on the values of an <code>Element</code>
     * 
     * @param table the table were the values of an element are displayed
     */
    private void setUpNumberEditor(final JTable table) {

        //Set up the editor for the double cells.  (is a adapted JTextField)
        final NumberEditField doubleField = new NumberEditField(0, 5);

        doubleField.setHorizontalAlignment(NumberEditField.RIGHT);

        DefaultCellEditor doubleEditor = new DefaultCellEditor(doubleField) {

            /**
             * Override DefaultCellEditor's getCellEditorValue() method to 
             * return the value of the editing cell as Double and not a String.
             *
             * For efficiency the value is additionally set directly/simultaneously
             * into the data element
             */
            @Override
            public Double getCellEditorValue() {

                //get the data that is current written in the field
//                System.out.println("doubleFiled: " + doubleField.getValue());
//                System.out.println("table row: " + table.getSelectedRow());
//                System.out.println("table column: " + table.getSelectedColumn());
//                System.out.println("currentdeep: " + ElementInputPane.this.elementCurrentDeep);


                /**
                 * for efficiently adding data into an Element set an entry
                 * directly after changing the value and not all at once, after
                 * selecting a new currentdeep and the values which hasn't changed too
                 */
                ElementInputPane.this.setElementEntry(table.getSelectedRow(), table.getSelectedColumn(), new Double(doubleField.getValue()));

                return new Double(doubleField.getValue());
            }

            /**
             * Override to costumize the behavior of the current edited cell and
             * the navigation from cell to cell with the keyboard.
             */
            @Override
            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int rows, int cols) {

                Component result = super.getTableCellEditorComponent(
                        table, value, isSelected, rows, cols);

                final JTextField field = (JTextField) result;

                field.setSize(doubleField.getSize());

                //side effect: select a field by one click:
                //the value in the field (complete field dark blue) would be erased
                //entry one number, the number is selected (light blue) tipping a
                //second number without clicking again or use the arrow keys would eraese
                //the first tipped number too
//                field.selectAll();

                field.setFocusable(true);
                field.getCaret().setVisible(true);

                //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4192625
                //allows to use the arrowkeys int the field to navigate with the caret
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        field.requestFocusInWindow();
//                        field.selectAll();
                    }
                });

                //get the text that was in the field before it was selected
//                System.out.println("field(" + rows + "," + cols + "): " + field.getText());

                return result;
            }
        };

        table.setDefaultEditor(Object.class, doubleEditor);
    }

    /**
     * Gets all values from the table and puts them into the element in the
     * current selected depth
     *
     * @return the modified element
     */
    public VisualElementInterface getElementDataFromTable() {

        int[] tmp = new int[3];
        tmp[2] = elementCurrentDeep;

        for (int x = 0; x < getTable().getRowCount(); x++) {
            tmp[0] = x;
            for (int y = 0; y < getTable().getColumnCount(); y++) {
                tmp[1] = y;
                getTable().editCellAt(x, y);
                visualElement.setEntry(tmp, getTable().getValueAt(x, y));
            }
        }

        //by changing the deep to loose the focus on the last edited field
        getTable().getCellEditor().stopCellEditing();

        return visualElement;
    }

    /**
     * Writes the values of the currentdeep of the to visualizing element into the JTable
     *
     * @param visualElement which contains the data
     */
    public void setElementDataIntoTable(VisualElementInterface visualElement) {

        //not the first time the table is called
        if (getTable().getCellEditor() != null) {
            //by changing the deep to loose the focus on the last edited field
            getTable().getCellEditor().cancelCellEditing();
        }

        this.visualElement = visualElement;

        int[] tmp = new int[3];
        tmp[2] = elementCurrentDeep;

        for (int x = 0; x < getTable().getRowCount(); x++) {

            tmp[0] = x;
            for (int y = 0; y < getTable().getColumnCount(); y++) {

                tmp[1] = y;
                getTable().setValueAt(visualElement.getEntry(tmp), x, y);
            }
        }

    }

    /**
     * Use this method if you want get an entry of the element from the current
     * selected deep.
     *
     * @param row the row index
     * @param col the column index
     * @return the value at (row, col, currentDeep)
     */
    public Object getElementEntry(Integer row, Integer col) {
        return visualElement.getEntry(new int[]{row, col, elementCurrentDeep});
    }

    /**
     * Use this method if you want set an entry of the Element at the current
     * selected deep.
     * Additionally all InputWindows with the same underlying data element will
     * be updated.
     *
     * @param row the row index
     * @param col the column index
     * @param value the value to be set at (row, col, currentDeep)
     */
    public void setElementEntry(Integer row, Integer col, Object value) {
        visualElement.setEntry(new int[]{row, col, elementCurrentDeep}, value);

        //
        //------ update now outdated InputWindows
        //
        InputWindowManager.getInputWindowManager().
                updateOtherInputWindowsWithSameDataElement(
                    visualElement.getElement(), visualElement);
    }

    /**
     * @return the table were the entries of the data element are shown
     */
    public JTable getTable() {
        return table;
    }

    /**
     * @return the visualElement which data elemnet would be shown in the table
     */
    public VisualElementInterface getVisualElement() {
        return visualElement;
    }

    /**
     * @param visualElement the visualElement to set
     */
    public void setVisualElement(VisualElementInterface visualElement) {
        this.visualElement = visualElement;
    }

    /**
     * @return the elementCurrentDeep
     */
    public Integer getElementCurrentDeep() {
        return elementCurrentDeep;
    }

    /**
     * @param elementCurrentDeep the elementCurrentDeep to set
     */
    public void setElementCurrentDeep(Integer elementCurrentDeep) {
        this.elementCurrentDeep = elementCurrentDeep;
    }
}
////  -----------------------------------------------------------
////  ||                                                       ||
////  || OLD METHODS, PROBABLE NOT NEED ANY MORE, BUT NOT SURE ||
////  ||                                                       ||
////  -----------------------------------------------------------
//    /**<p>
//     * Returns a sorted string with the data of a <code>FormelEntry</code>.
//     * Additional informations are added to each value of a <code>FormelEntry</code>.
//     * The additional Information is for the use with UG necessary.
//     * </p><p>
//     * The folowing informations are added without whitespace:<br>
//     * 1.)$<br>
//     * 2.)the short name of the <code>FormelEntry</code><br>
//     * 3.)row position x, of the value in e.g. the matrix<br>
//     * 4.)col position y, of the value in e.g. the matrix<br>
//     * </p><p>
//     * The string is typical sorted how you could image it, first by rows (from 0 to n)
//     * and each row is sorted by columns (from 0 to n).
//     * </p>
//     * @return the sorted String with all values of the FormelEntry
//     */
//    public String getData() {
//        String result = "";
//
//        int y = 0, x = 0;
//
//        for (x = 0; x < table.getRowCount(); x++) {
//            for (y = 0; y < table.getColumnCount(); y++) {
//
//
//                table.editCellAt(x, y);
//                result += "$" + getFormelEntry().getShortName() + y + x + " " +
//                        table.getValueAt(x, y).toString() + " ";
//
//            }
//        }
//
//        return result;
//    }
//    /**
//     * Splits the given string into pieces by using whitespaces as cut position.
//     * Removes the addtional information and writte the values in the correct
//     * position of a JTable.
//     *
//     * @param s String which contains data and additional informations of a FormelEntry
//     */
//    public void setData(String s) {
//        String[] werte = s.split("\\s");
//        Vector<String> data = new Vector<String>();
//
//        for (int i = 0; i < werte.length; i++) {
//
//            if (((werte[i].substring(0, 1)).compareTo("$") != 0)) {
//                data.add(werte[i]);
//            }
//        }
//        int i = 0;
//
//        for (int x = 0; x < table.getRowCount(); x++) {
//            for (int y = 0; y < table.getColumnCount(); y++) {
//
//                table.setValueAt(data.get(i), x, y);
//                i++;
//            }
//        }
//    }
//    /**
//     * Gets all values from the table and puts them into the element in the current selected depth
//     *
//     * @return the modified element
//     */
//    public ElementInterface getElementDataFromTable() {
//
//        int[] tmp = new int[3];
//        tmp[2] = elementCurrentDeep;
//
//        for (int x = 0; x < getTable().getRowCount(); x++) {
//            tmp[0] = x;
//            for (int y = 0; y < getTable().getColumnCount(); y++) {
//                tmp[1] = y;
//                getTable().editCellAt(x, y);
////                element.setEntry(tmp, getTable().getValueAt(x, y));//new int[]{x, y, elementCurrentDeep}
//                visualElement.setEntry(tmp, getTable().getValueAt(x, y));//new int[]{x, y, elementCurrentDeep}
//            }
//        }
//
//        //by changing the deep to loose the focus on the last edited field
//        getTable().getCellEditor().stopCellEditing();
//
//        return element;
//    }

//    /**
//     * Writes the values of the currentdeep of the to visualizing element into the JTable
//     *
//     * @param element which contains the data
//     */
//    public void setElementDataIntoTable(ElementInterface element) {
//
//        //not the first time table is called
//        if (getTable().getCellEditor() != null) {
//            //by changing the deep to loose the focus on the last edited field
//            getTable().getCellEditor().cancelCellEditing();
//        }
//
//        this.element = element;
//
//        int[] tmp = new int[3];
//        tmp[2] = elementCurrentDeep;
//
//        for (int x = 0; x < getTable().getRowCount(); x++) {
//
//            tmp[0] = x;//+1;//+1 because of starts to count with 1
//            for (int y = 0; y < getTable().getColumnCount(); y++) {
//
//                tmp[1] = y;//+1;//+1 because of starts to count with 1
//                getTable().setValueAt(element.getEntry(tmp), x, y);//new int[]{x, y, elementCurrentDeep}
//            }
//        }
//
//    }