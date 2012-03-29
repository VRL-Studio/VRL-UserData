/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import basic.math.ug.equation.*;

/**
 * ColorButtonGroup is a specialized <code>ToolButtonGroup</code> for
 * ColorButtons.
 *
 * @author night
 */
public class ColorButtonGroup extends ToolButtonGroup {

    public ColorButtonGroup() {
        addActionListener(this);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        super.actionPerformed(e);
//
////        if (getSelectedButton() instanceof ColorButton) {
////
////            color = ((ColorButton) getSelectedButton()).getColor();
////        }
//
//        if (getSelectedButton() instanceof ColorButton) {
//
//            ColorButton colorButton = (ColorButton) getSelectedButton();
//
//            if (e.getActionCommand().equals(ToolButtonMode.DESELECT)) {
//                colorButton.setColor(Color.BLUE);
//            }
//
//            if (e.getActionCommand().equals(ToolButtonMode.DIRICHLET)) {
//                colorButton.setColor(Color.YELLOW);
//            }
//
//            if (e.getActionCommand().equals(ToolButtonMode.NEUMANN)) {
//                colorButton.setColor(Color.GREEN);
//            }
//        } else if (getSelectedButton() instanceof ResetButton) {
//
//        }
//    }
}
