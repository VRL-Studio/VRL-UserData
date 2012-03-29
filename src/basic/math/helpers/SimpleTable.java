/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Because of different visualization behaviour on difffrent operating systems
 * there some corrections for the cell sizes and spaces between the cell needed.
 *
 * This class is at first a normal JTable which correctes the above named
 * problem by setting some variable if needed.
 *
 * @author Night
 */
public class SimpleTable extends JTable {

    SimpleTable(DefaultTableModel datamodel) {
        super(datamodel);

        /**
         * if the programm doesn't run on a mac some corrections for the cell
         * sizes / space between the cell are needed.
         */
        if (!System.getProperty("os.name").contains("Mac OS X")) {
            //additional size setting for table cells before selecting and
            //values are entered
            // width = 4 and hight = 6 is a good combination on WinXP
            setIntercellSpacing(new Dimension(4, 6));//in pixeln
        }
    }

    /**
     * if the programm doesn't run on a mac some corrections for the cell
     * sizes / space between the cell are needed.
     * {@inheritDoc}
     */
    @Override
    public int getRowHeight() {

        if (System.getProperty("os.name").contains("Mac OS X")) {
            return super.getRowHeight();
        }

        return rowHeight + 2 * rowMargin;
    }
}
