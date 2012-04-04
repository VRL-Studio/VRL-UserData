/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.Dimensions;
import edu.gcsc.vrl.userdata.helpers.MatrixPane;
import eu.mihosoft.vrl.lang.CompilerProvider;
import eu.mihosoft.vrl.lang.visual.EditorProvider;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.VCodeEditor;
import eu.mihosoft.vrl.visual.VConstrainedScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataWindow extends CanvasWindow implements Serializable {

    private static final long serialVersionUID = 1;
    Box outter = null;
    MatrixPane matrixPane = null;
    VCodeEditor editor = null;

    public UserDataWindow(String title, Canvas canvas) {

        super(title, canvas);

        init();

        setVisible(true);

    }

    void init() {
        outter = Box.createVerticalBox();
        add(outter);


        Box inner1 = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);

        inner1.setBorder(border1);
        outter.add(inner1);

        Dimensions[] dims = {Dimensions.ONE, Dimensions.TWO, Dimensions.THREE};
        final JComboBox dimsCoose = new JComboBox(dims);

        inner1.add(dimsCoose);

        final JRadioButton constant = new JRadioButton("Constant");


        JRadioButton code = new JRadioButton("Code");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(constant);
        group.add(code);

        inner1.add(constant);
        inner1.add(code);

        // here is the changed view added
        final Box inner2 = Box.createHorizontalBox();
        outter.add(inner2);

        matrixPane = new MatrixPane(Dimensions.ONE);
        inner2.add(matrixPane);

        Dimension prefDim = new Dimension(300, 200);
        Dimension maxDim = new Dimension(300, 200);
        
        editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);
        editor.setVisible(true);
        
        editor.setMinimumSize(prefDim);
        editor.setPreferredSize(prefDim);
        editor.setMaximumSize(maxDim);

        
        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(editor);
        vScrollPane.setVisible(true);
        
        vScrollPane.setMaxHeight(maxDim.height);
        vScrollPane.setMaxWidth(maxDim.width);

        vScrollPane.setMinimumSize(prefDim);
        vScrollPane.setPreferredSize(prefDim);
        vScrollPane.setMaximumSize(maxDim);

        inner2.add(vScrollPane);

        
        

        //
        /// LISTENER 
        //

        //
        // WHICH DIM CHOOSEN
        //
        dimsCoose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                inner2.remove(matrixPane);
//                matrixPane = null;
//                updateUI();

                if (dimsCoose.getSelectedItem().equals(Dimensions.ONE)) {
                    matrixPane = new MatrixPane(Dimensions.ONE);


                } else if (dimsCoose.getSelectedItem().equals(Dimensions.TWO)) {
                    matrixPane = new MatrixPane(Dimensions.TWO);


                } else if (dimsCoose.getSelectedItem().equals(Dimensions.THREE)) {
                    matrixPane = new MatrixPane(Dimensions.THREE);

                }

                if (constant.isSelected()) {
                    matrixPane.setVisible(true);
                } else {
                    matrixPane.setVisible(false);
                }

                inner2.add(matrixPane);
                updateUI();

            }
        });

        //
        // IF CONSTANT
        //
        constant.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                matrixPane.setVisible(true);
                editor.setVisible(false);
            }
        });
        constant.doClick();//set as default choosen


        //
        // IF CODE
        // 
        code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                editor.setVisible(true);
                matrixPane.setVisible(false);
            }
        });
    }
}
