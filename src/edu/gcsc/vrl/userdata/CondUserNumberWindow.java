/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.types.CondUserNumberType;
import edu.gcsc.vrl.userdata.helpers.Dimensions;
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
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CondUserNumberWindow extends CanvasWindow implements Serializable {

    private static final long serialVersionUID = 1;
    private transient Box outter = null;
    private transient VCodeEditor editor = null;
    private transient VContainer editorPane;
    private transient JComponent parent;
    private transient CondUserNumberModel model = new CondUserNumberModel();
    private transient JComboBox dimsCoose;
    private transient TypeRepresentationBase tRep;

    public CondUserNumberWindow(TypeRepresentationBase tRep, String title, Canvas canvas) {

        super(title, canvas);

        this.tRep = tRep;

        init();

        setVisible(true);

    }

    private void init() {
        
        int startDim = Dimensions.TWO;
        //NO defaultdata like {0.0,0.0} because only code is allowed
        
        outter = Box.createVerticalBox();
        add(outter);

        Box inner1 = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);

        inner1.setBorder(border1);
        outter.add(inner1);

        Integer[] dims = {Dimensions.ONE, Dimensions.TWO, Dimensions.THREE};
        dimsCoose = new JComboBox(dims);
        dimsCoose.setSelectedItem(startDim);

        inner1.add(dimsCoose);

        // here is the changed view added
        final Box inner2 = Box.createHorizontalBox();
        outter.add(inner2);

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
//        dimsCoose.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                parent.add(editorPane);
//
//                revalidate();
//
//            }
//        });




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

        getModel().setCode(editor.getEditor().getText());

        getModel().setDimension((Integer) dimsCoose.getSelectedItem());

        CustomParamData pData = new CustomParamData();
        pData.put(CondUserNumberType.getMODEL_KEY(), getModel());
        tRep.setCustomData(pData);
    }

    /**
     * @return the model
     */
    public CondUserNumberModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(CondUserNumberModel model) {
        this.model = model;
    }

    
}
