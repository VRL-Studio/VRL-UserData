/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.visual.CanvasWindow;
import java.io.Serializable;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author markus
 */
class SubsetSelectionWindow extends CanvasWindow implements Serializable
{
    private transient Box outerBox = null;
    private transient UserDataWindowPane constantPane = null;
    private transient JComponent editBox;
    private transient SubsetSelectionView ssView = null;
    private transient SubsetSelectionModel ssModel = null;
    protected boolean internalAdjustment = false;

    public SubsetSelectionWindow(SubsetSelectionView view)
    {
        super(view.getName(), view.getCanvas());

        ssView = view;
        ssModel = ssView.getModel();

        // must be visible
        setVisible(true);

        // create an outer box, where we put in all other components
        outerBox = Box.createVerticalBox();
        add(outerBox);

        // create a box for the menu
        Box menuBox = Box.createHorizontalBox();
        Border border1 = new EmptyBorder(0, 5, 0, 5);
        menuBox.setBorder(border1);
        outerBox.add(menuBox);
        
        // here, the changed view is added
        editBox = Box.createHorizontalBox();
        outerBox.add(editBox);
        
        // JList instead?
        
        /*
        constantPane = new UserDataWindowPane(ssView);
        
        VConstrainedScrollPane vScrollPane = new VConstrainedScrollPane(editor);
        vScrollPane.setVisible(true);
        vScrollPane.setMaximumSize(new Dimension(680, 800));
        vScrollPane.setMinimumSize(new Dimension(200, 100));

        VContainer editorPane = new VContainer(editor);
        editorPane.setMinPreferredWidth(300);
        editorPane.setMinPreferredHeight(200);
        editorPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        codeCommment = new JLabel("", JLabel.LEFT);

        codePane = Box.createVerticalBox();
        codePane.add(codeCommment);
        codePane.add(editorPane);
        */
        // all done, now update the window with the current data in the model
        updateWindow(ssModel);
    }

    protected void storeData()
    {
        if (ssModel.getStatus() != UserDataModel.Status.INVALID) ssModel.setStatus(UserDataModel.Status.VALID);
        
        ssView.adjustView(ssModel.getStatus());
        ssView.updateToolTipText();
        
        // What does this do?
        //ssView.getUserDataTupleType().storeCustomParamData();
    }

    /**
     * Stores all informations which are visualized in the UserDataWindow
     * in a corresponding UserDataModel and calls
     * <code>storeCustomParamData()</code>.
     *
     * @see #storeCustomParamData()
     */
    public void updateModel(UserMathDataModel model)
    {
        // copy data from window into model
        
        // TODO: replace this
        //if (!model.isCondition()) model.setDataFromTable(constantPane.getTableModel());
        
        storeData();
    }

    /**
     * Get all informations which value should be visualized in the
     * SubsetSelectionWindow
     * from the corresponding UserDataModel and sets the values in the window.
     *
     * @param model that stores the informations that should be visualized
     */
    final public void updateWindow(SubsetSelectionModel model)
    {
        internalAdjustment = true;
        
        // TODO: anpassen
        //constantPane.updateModel(ssModel);

        revalidate();
        
        internalAdjustment = false;
    }

    /**
     * @return the model
     */
    public SubsetSelectionModel getModel()
    {
        return ssModel;
    }
}
