package edu.gcsc.vrl.userdata;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import edu.gcsc.vrl.userdata.*;
import edu.gcsc.vrl.userdata.helpers.Dimensions;
import edu.gcsc.vrl.userdata.helpers.MatrixPane;
import edu.gcsc.vrl.userdata.helpers.VectorPane;
import eu.mihosoft.vrl.lang.CompilerProvider;
import eu.mihosoft.vrl.lang.visual.EditorProvider;
import eu.mihosoft.vrl.reflection.CustomParamData;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserMatrixWindow extends CanvasWindow implements Serializable {

    private static final long serialVersionUID = 1;
    private transient Box outter = null;
    private transient MatrixPane matrixPane = null;
    private transient VCodeEditor editor = null;
    private transient VContainer editorPane;
    private transient JComponent parent;
    private transient UserMatrixModel model = new UserMatrixModel();
    private transient JComboBox dimsCoose;
    private transient TypeRepresentationBase tRep;
    private transient JRadioButton constant;
    private transient JRadioButton code;

    public UserMatrixWindow(TypeRepresentationBase tRep, String title, Canvas canvas) {

        super(title, canvas);

        this.tRep = tRep;

        init();

        setVisible(true);

    }

    private void init() {
        outter = Box.createVerticalBox();
        add(outter);

        Box inner1 = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);

        inner1.setBorder(border1);
        outter.add(inner1);

        Integer[] dims = {Dimensions.ONE, Dimensions.TWO, Dimensions.THREE};
        dimsCoose = new JComboBox(dims);

        inner1.add(dimsCoose);

        constant = new JRadioButton("Constant");

        code = new JRadioButton("Code");

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
        Dimension maxDim = new Dimension(680, 800);

        editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);
//        editor.setVisible(true);

//        editor.setMinimumSize(prefDim);
//        editor.setPreferredSize(prefDim);
//        editor.setMaximumSize(maxDim);


        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(editor);
        vScrollPane.setVisible(true);

        vScrollPane.setMaxHeight(maxDim.height);
        vScrollPane.setMaxWidth(maxDim.width);

        vScrollPane.setMinimumSize(prefDim);


        editorPane = new VContainer(editor);

        // add minimum editor width functionality
        editorPane.setMinPreferredWidth(300);
        editorPane.setMinPreferredHeight(200);

        editorPane.setBorder(new EmptyBorder(5, 5, 5, 5));


        inner2.add(editorPane);

        parent = inner2;

        VButton btn = new VButton("OK");
        btn.setAlignmentX(CENTER_ALIGNMENT);

        add(btn);

        add(Box.createVerticalStrut(5));

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
            }
        });

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

                if (dimsCoose.getSelectedItem().equals(Dimensions.ONE)) {
                    matrixPane = new MatrixPane(Dimensions.ONE);


                } else if (dimsCoose.getSelectedItem().equals(Dimensions.TWO)) {
                    matrixPane = new MatrixPane(Dimensions.TWO);


                } else if (dimsCoose.getSelectedItem().equals(Dimensions.THREE)) {
                    matrixPane = new MatrixPane(Dimensions.THREE);
                }

                if (constant.isSelected()) {
                    parent.remove(editorPane);
                    parent.add(matrixPane);
                } else {
                    parent.remove(matrixPane);
                    parent.add(editorPane);
                }

                revalidate();

            }
        });


        //
        // IF CONSTANT
        //
        constant.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (constant.isSelected()) {
                    parent.add(matrixPane);
                    parent.remove(editorPane);
                    revalidate();
                    getModel().setConstData(true);
                }
            }
        });
        constant.doClick();//set as default choosen


        //
        // IF CODE
        // 
        code.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (code.isSelected()) {
                    parent.remove(matrixPane);
                    parent.add(editorPane);
                    revalidate();
                    getModel().setConstData(false);
                }
            }
        });



        addActionListener(new CanvasActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(CanvasWindow.CLOSE_ACTION)) {
                    updateModel();
                }
            }
        });

    }

    public void updateModel() {
        if (getModel().isConstData()) {
            getModel().setData(modelToMatrix(matrixPane.getDataModel()));
        } else {
            getModel().setCode(editor.getEditor().getText());
        }
        getModel().setDimension((Integer) dimsCoose.getSelectedItem());

        CustomParamData pData = new CustomParamData();
        pData.put(UserMatrixType.MODEL_KEY, getModel());
        tRep.setCustomData(pData);
    }

    /**
     * @return the model
     */
    public UserMatrixModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(UserMatrixModel model) {
        this.model = model;

        dimsCoose.setSelectedIndex(model.getDimension() - 1);

        DefaultTableModel dataModel = matrixPane.getDataModel();
        matrixToModel(dataModel, model.getData());

        editor.getEditor().setText(model.getCode());

        if (model.isConstData()) {
            constant.setSelected(true);
        } else {
            code.setSelected(true);
        }
    }

    private static void matrixToModel(DefaultTableModel dataModel, Double[][] data) {
        dataModel.setRowCount(data.length);

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                dataModel.setValueAt(data[i][j], i, j);
            }
        }
    }

    private static Double[][] modelToMatrix(DefaultTableModel dataModel) {
        Double[][] data = new Double[dataModel.getRowCount()][dataModel.getColumnCount()];

        for (int j = 0; j < dataModel.getColumnCount(); j++) {
            for (int i = 0; i < dataModel.getRowCount(); i++) {
                data[i][j] = (Double) dataModel.getValueAt(i, j);
            }
        }
        return data;
    }
}
