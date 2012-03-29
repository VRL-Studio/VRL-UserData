/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.Constants;
import edu.gcsc.vrl.userdata.helpers.Dimensions;
import edu.gcsc.vrl.userdata.helpers.MatrixPane;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.lang.CompilerProvider;
import eu.mihosoft.vrl.lang.visual.EditorProvider;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.VCodeEditor;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@ObjectInfo(name="UserDataWindow")
@ComponentInfo(name = "UserDataWindow", category = Constants.CATEGORY)
public class UserDataWindow extends CanvasWindow {

    public UserDataWindow(String title, Canvas mainCanvas) {
        super(title, mainCanvas);

        Box outter = Box.createHorizontalBox();
        add(outter);

        Dimensions[] dims = {Dimensions.ONE, Dimensions.TWO, Dimensions.THREE};
        JComboBox dimsCoose = new JComboBox(dims);

        outter.add(dimsCoose);

        JRadioButton constant = new JRadioButton("Constant");
        constant.doClick();//set as default choosen
        JRadioButton code = new JRadioButton("Code");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(constant);
        group.add(code);

        outter.add(constant);
        outter.add(code);

        // here is the changed view added
        Box inner = Box.createHorizontalBox();

        outter.add(inner);

        //?? make a default choise or do it in an init() after user has choosen ??

        //
        // IF CONSTANT
        // 
        MatrixPane matrixPane = new MatrixPane(Dimensions.ONE);

        matrixPane.setVisible(constant.isSelected());

        inner.add(matrixPane);
        
        
        //
        // IF CODE
        // 
        
        VCodeEditor editor = EditorProvider.getEditor(
                CompilerProvider.LANG_GROOVY, this);
        
        
        editor.setVisible(code.isSelected());

        inner.add(editor);
    }
}
