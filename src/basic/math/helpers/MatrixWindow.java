/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

/**<p>
 * MatrixWindow is the Window where values of one FormelEntry e.g. a matrix
 * are displayed and can be changed. The area where the values are displayed is
 * a <code>MatrixPane</code>. Changes on the values will be only take over to the
 * FormelEntry if the "ok" button was pressed.
 * </p><p>
 * It shows to which FormelEntry it belongs by showing a label at the top
 * with the short name of the FormelEntry.
 * </p>
 * @author Night
 */
public class MatrixWindow extends CanvasWindow {

    private MatrixPane matrix;
    private FormelEntry formelEntry;

    public MatrixWindow(String title, Canvas mainCanvas, final FormelEntry formelEntry) {
        super(title, mainCanvas);

        this.formelEntry = formelEntry;

        matrix = new MatrixPane(this, formelEntry, formelEntry.getRows(), formelEntry.getCols());
        matrix.setFocusable(true);
        if (formelEntry.getData() != null) {
            matrix.setData(formelEntry.getData());
        }

        add(matrix);

        matrix.setAlignmentX(0.5f);

        VButton button = new VButton("Ok");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                VButton o = (VButton) e.getSource();
                o.requestFocus();

                // matrix.setFocusable(false);
//                MessageBox mBox = getMainCanvas().getMessageBox();
//                mBox.addMessage("Data:", matrix.getData(), null, MessageType.INFO);
                formelEntry.setData(matrix.getData());
            }
        });

        button.setAlignmentX(0.5f);

        VContainer container = new VContainer(button);
        container.setBorder(new EmptyBorder(3, 0, 5, 0));

        add(container);
    }

    /**
     * @return the formelEntry
     */
    public FormelEntry getFormelEntry() {
        return formelEntry;
    }
}
