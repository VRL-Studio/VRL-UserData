package edu.gcsc.vrl.userdata;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
import edu.gcsc.vrl.userdata.managers.DimensionManager;
import edu.gcsc.vrl.userdata.types.UserMatrixType;
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
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public abstract class UserDataWindow extends CanvasWindow implements Serializable {

    private static final long serialVersionUID = 1;
    private transient Box outter = null;
    private transient UserDataWindowPane windowPane = null;
    private transient VCodeEditor editor = null;
    private transient VContainer editorPane;
    private transient JComponent parent;
    private transient UserDataModel model = null;//new UserMatrixModel();
    private transient JComboBox dimsChoose;
    private transient TypeRepresentationBase tRep;
    protected transient JRadioButton constant;
    protected transient JRadioButton code;

    public UserDataWindow(UserDataModel model,
            TypeRepresentationBase tRep,
            String title,
            Canvas canvas) {

        super(title, canvas);

        this.tRep = tRep;

        this.model = model;

        init();

        setVisible(true);

    }

    private void init() {

        int startdim = DimensionManager.TWO;//model.getDimension();
        System.out.println("UserDataWindow.init(): startdim = " + startdim);
//        model.setData(createDefaultData());
////        updateModel();
//        checkCustomData();

        outter = Box.createVerticalBox();
        add(outter);

        Box menuBox = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);

        menuBox.setBorder(border1);
        outter.add(menuBox);

        Integer[] dims = {DimensionManager.ONE, DimensionManager.TWO, DimensionManager.THREE};
        dimsChoose = new JComboBox(dims);
        dimsChoose.setSelectedItem(startdim);

        menuBox.add(dimsChoose);

        constant = new JRadioButton("Constant");

        code = new JRadioButton("Code");

        //Group the radio buttons.
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(constant);
        buttonGroup.add(code);

        menuBox.add(constant);
        menuBox.add(code);

        // here is the changed view added
        final Box paneBox = Box.createHorizontalBox();
        outter.add(paneBox);

        windowPane = new UserDataWindowPane(startdim, model);
        paneBox.add(windowPane);

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


        paneBox.add(editorPane);

        parent = paneBox;

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

        // SHOW the last information in window
        // that the user views/choose before closing
        // previous window for the corresponding UserData
        if (model.isConstData()) {
            constant.setSelected(true);
        } else {
            code.setSelected(true);
        }
        revalidate();

        //
        /// LISTENER 
        //

        //
        // WHICH DIM CHOOSEN
        //
        dimsChoose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                paneBox.remove(windowPane);

                if (dimsChoose.getSelectedItem().equals(DimensionManager.ONE)) {
                    windowPane = new UserDataWindowPane(DimensionManager.ONE, model);


                } else if (dimsChoose.getSelectedItem().equals(DimensionManager.TWO)) {
                    windowPane = new UserDataWindowPane(DimensionManager.TWO, model);


                } else if (dimsChoose.getSelectedItem().equals(DimensionManager.THREE)) {
                    windowPane = new UserDataWindowPane(DimensionManager.THREE, model);
                }

                //Make sure a pane is shown at opening window
                //without changing dim or clicking on radio button
                if (constant.isSelected()) {
                    model.setConstData(true);
                    parent.remove(editorPane);
                    parent.add(windowPane);
                } else {
                    model.setConstData(false);
                    parent.remove(windowPane);
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
                    parent.add(windowPane);
                    parent.remove(editorPane);
                    revalidate();
                    getModel().setConstData(true);
                }
            }
        });

        //
        // IF CODE
        // 
        code.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (code.isSelected()) {
                    parent.remove(windowPane);
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

        // SAVE the last information in window
        // that the user views/choose before closing
        // previous window for the corresponding UserData
        if (constant.isSelected()) {
            model.setConstData(true);
        } else {
            model.setConstData(false);
        }

        windowData2ModelData(this, getModel());
        windowCode2ModelCode(this, model);

        getModel().setDimension((Integer) dimsChoose.getSelectedItem());

        checkCustomData();
    }

    public void checkCustomData() {
        CustomParamData pData = tRep.getCustomData();

        if (pData == null) {
            pData = new CustomParamData();
        }

        pData.put(model.getModelKey(), getModel());

        tRep.setCustomData(pData);
    }

    /**
     * @return the model
     */
    public UserDataModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void updateWindow(UserDataModel model) {

        // SHOW the last information in window
        // that the user views/choose before closing
        // previous window for the corresponding UserData
        if (model.isConstData()) {
            constant.setSelected(true);
            parent.add(windowPane);
            parent.remove(editorPane);
            revalidate();
        } else {
            code.setSelected(true);
            parent.remove(windowPane);
            parent.add(editorPane);
            revalidate();
        }

//        dimsChoose.setSelectedIndex(model.getDimension() - 1);
        switch (model.getDimension()) {

            case 1:
                dimsChoose.setSelectedItem(DimensionManager.ONE);
                break;
            case 2:
                dimsChoose.setSelectedItem(DimensionManager.TWO);
                break;
            case 3:
                dimsChoose.setSelectedItem(DimensionManager.THREE);
                break;

            default:
                System.out.println(">> UserDataWindow: UserDataModel has invalid dimension!");
                break;
        }

        modelData2WindowData(model, this);

//        editor.getEditor().setText(model.getCode());
        modelCode2WindowCode(model, this);

    }

    protected DefaultTableModel getTableModel() {
        return windowPane.getTableModel();
    }

    private String getCode() {
        return editor.getEditor().getText();
    }

    private void setCode(String code) {
        editor.getEditor().setText(code);
    }

    public void modelCode2WindowCode(UserDataModel model, UserDataWindow window) {
        window.setCode(model.getCode());
    }

    public void windowCode2ModelCode(UserDataWindow window, UserDataModel model) {
        model.setCode(window.getCode());
    }

//    private static void matrixToModel(DefaultTableModel dataModel, Double[][] data) {
//        dataModel.setRowCount(data.length);
//
//        for (int j = 0; j < data.length; j++) {
//            for (int i = 0; i < data[j].length; i++) {
//                dataModel.setValueAt(data[i][j], i, j);
//            }
//        }
//    }
//    private static Double[][] modelToMatrix(DefaultTableModel dataModel) {
//        Double[][] data = new Double[dataModel.getRowCount()][dataModel.getColumnCount()];
//
//        for (int j = 0; j < dataModel.getColumnCount(); j++) {
//            for (int i = 0; i < dataModel.getRowCount(); i++) {
//                data[i][j] = (Double) dataModel.getValueAt(i, j);
//            }
//        }
//        return data;
//    }
    public abstract void modelData2WindowData(UserDataModel model, UserDataWindow window);//DefaultTableModel tableModel); //TODO use window instead table
//    {//Double[][] data
//        Double[][] data = (Double[][]) model.getData();
//                
//        tableModel.setRowCount(data.length);
//
//        for (int j = 0; j < data.length; j++) {
//            for (int i = 0; i < data[j].length; i++) {
//                tableModel.setValueAt(data[i][j], i, j);
//            }
//        }
//    }

    public abstract void windowData2ModelData(UserDataWindow window, UserDataModel model); //TODO use window instead table
//    {
//        
//        Double[][] data = new Double[tableModel.getRowCount()][tableModel.getColumnCount()];
//
//        for (int j = 0; j < tableModel.getColumnCount(); j++) {
//            for (int i = 0; i < tableModel.getRowCount(); i++) {
//                data[i][j] = (Double) tableModel.getValueAt(i, j);
//            }
//        }
//        return data;
//    }
}
