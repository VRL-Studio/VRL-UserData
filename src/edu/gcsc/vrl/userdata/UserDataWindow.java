///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.gcsc.vrl.userdata;
//
//import edu.gcsc.vrl.userdata.helpers.Dimensions;
//import edu.gcsc.vrl.userdata.helpers.MatrixPane;
//import eu.mihosoft.vrl.lang.CompilerProvider;
//import eu.mihosoft.vrl.lang.visual.EditorProvider;
//import eu.mihosoft.vrl.visual.*;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.Serializable;
//import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
//
///**
// *
// * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
// */
//public class UserDataWindow extends CanvasWindow implements Serializable {
//
//    private static final long serialVersionUID = 1;
//    private transient Box outter = null;
//    private transient MatrixPane matrixPane = null;
//    private transient VCodeEditor editor = null;
//    private transient VContainer editorPane;
//    private transient JComponent parent;
//    private UserNumberModel model = new UserNumberModel();
//    
//
//    public UserDataWindow(String title, Canvas canvas) {
//
//        super(title, canvas);
//
//        init();
//
//        setVisible(true);
//
//    }
//
//    private void init() {
//        outter = Box.createVerticalBox();
//        add(outter);
//
//        Box inner1 = Box.createHorizontalBox();
//        Border border1 = new EmptyBorder(0, 5, 0, 5);
//
//        inner1.setBorder(border1);
//        outter.add(inner1);
//
//        Integer[] dims = {Dimensions.ONE, Dimensions.TWO, Dimensions.THREE};
//        final JComboBox dimsCoose = new JComboBox(dims);
//
//        inner1.add(dimsCoose);
//
//        final JRadioButton constant = new JRadioButton("Constant");
//
//        JRadioButton code = new JRadioButton("Code");
//
//        //Group the radio buttons.
//        ButtonGroup group = new ButtonGroup();
//        group.add(constant);
//        group.add(code);
//
//        inner1.add(constant);
//        inner1.add(code);
//
//        // here is the changed view added
//        final Box inner2 = Box.createHorizontalBox();
//        outter.add(inner2);
//
//        matrixPane = new MatrixPane(Dimensions.ONE);
//        inner2.add(matrixPane);
//
//        Dimension prefDim = new Dimension(300, 200);
//        Dimension maxDim = new Dimension(300, 200);
//
//        editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);
////        editor.setVisible(true);
//
//        editor.setMinimumSize(prefDim);
//        editor.setPreferredSize(prefDim);
//        editor.setMaximumSize(maxDim);
//
//
//        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(editor);
//        vScrollPane.setVisible(true);
//
//        vScrollPane.setMaxHeight(maxDim.height);
//        vScrollPane.setMaxWidth(maxDim.width);
//
//        vScrollPane.setMinimumSize(prefDim);
//        vScrollPane.setPreferredSize(prefDim);
//        vScrollPane.setMaximumSize(maxDim);
//        
//        
//
//        editorPane = new VContainer(vScrollPane);
//        
//        editorPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        inner2.add(editorPane);
//
//        parent = inner2;
//
//        //
//        /// LISTENER 
//        //
//
//        //
//        // WHICH DIM CHOOSEN
//        //
//        dimsCoose.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                inner2.remove(matrixPane);
////                matrixPane = null;
////                updateUI();
//
//                if (dimsCoose.getSelectedItem().equals(Dimensions.ONE)) {
//                    matrixPane = new MatrixPane(Dimensions.ONE);
//
//
//                } else if (dimsCoose.getSelectedItem().equals(Dimensions.TWO)) {
//                    matrixPane = new MatrixPane(Dimensions.TWO);
//
//
//                } else if (dimsCoose.getSelectedItem().equals(Dimensions.THREE)) {
//                    matrixPane = new MatrixPane(Dimensions.THREE);
//
//                }
//
//                if (constant.isSelected()) {
////                    matrixPane.setVisible(true);
//                    parent.remove(editorPane);
//                    parent.add(matrixPane);
//                    model.setConstData(true);
//                } else {
////                    matrixPane.setVisible(false);
//                    parent.remove(matrixPane);
//                    parent.add(editorPane);
//                    model.setConstData(false);
//                }
//
//                revalidate();
//
//            }
//        });
//
//        //
//        // IF CONSTANT
//        //
//        constant.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                parent.add(matrixPane);
//                parent.remove(editorPane);
//                revalidate();
//            }
//        });
//        constant.doClick();//set as default choosen
//
//
//        //
//        // IF CODE
//        // 
//        code.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                parent.remove(matrixPane);
//                parent.add(editorPane);
//                revalidate();
//            }
//        });
//        
//        
//        addActionListener(new CanvasActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals(CanvasWindow.CLOSE_ACTION)) {
//                    if(model.isConstData()) {
//                        model.setData(matrixPane);
//                    }
//                }
//            }
//        });
//
//    }
//
//    /**
//     * @return the model
//     */
//    public UserNumberModel getModel() {
//        return model;
//    }
//
//}
