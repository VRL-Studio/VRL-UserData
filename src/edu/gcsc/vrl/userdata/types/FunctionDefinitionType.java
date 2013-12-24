/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.FunctionDefinition;
import edu.gcsc.vrl.userdata.FunctionDefinitionObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObserver;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import groovy.lang.Script;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author mbreit
 */
@TypeInfo(type = FunctionDefinition.class, input = true, output = false, style = "default")
public class FunctionDefinitionType extends TypeRepresentationBase implements Serializable, LoadUGXFileObserver
{
    protected String name = null;
    protected VTextField fctNameField = null;
    protected JList subsetList = null;
    DefaultListModel subsetListModel = null;
    private FunctionDefinitionObservable.FctData fctData = null;
    
    // index for identification of array position in function def array
    // (to be provided by FunctionDefinitionObservable)
    int arrayIndex;  
    
    private String ugx_tag = null;
    private String fct_tag = null;
      
    
    public FunctionDefinitionType()
    {
        // hide connector 
        setHideConnector(true);
    }
    
    
    public void init()
    {
        removeAll();
        
        // create a VBoxLayout and set it as layout
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
        setLayout(layout);
        setLayoutType(LayoutType.STATIC);
        
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(nameLabel);

        // elements are horizontally aligned
        Box horizBox = Box.createHorizontalBox();
        horizBox.setAlignmentX(LEFT_ALIGNMENT);
        add(horizBox);
        
        // function naming field
        fctNameField = new VTextField("");
        fctNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        fctNameField.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (subsetList.getSelectedIndices() != null && fctNameField.getText() != null && !fctNameField.getText().equals(""))
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    List<String> selSubsets = new ArrayList<String>();
                    for (int i: subsetList.getSelectedIndices()) selSubsets.add((String) subsetList.getModel().getElementAt(i));
                    fctData = new FunctionDefinitionObservable.FctData(fctNameField.getText(), selSubsets);
                    notifyFunctionDefinitionObservable();
                }
            }
        });
        
        horizBox.add(fctNameField);
        
        // subset selection list
        subsetListModel = new DefaultListModel();
        subsetList = new JList(subsetListModel);
        subsetList.setSelectionModel(new DefaultListSelectionModel()
        {
            private static final long serialVersionUID = 1L;
            boolean gestureStarted = false;

            @Override
                public void setSelectionInterval(int index0, int index1)
                {
                    if(!gestureStarted)
                        if (isSelectedIndex(index0)) super.removeSelectionInterval(index0, index1);
                        else super.addSelectionInterval(index0, index1);
                    gestureStarted = true;
                }

                @Override
                public void setValueIsAdjusting(boolean isAdjusting)
                {
                    if (isAdjusting == false) gestureStarted = false;
                }
        });
        subsetList.setAlignmentX(Component.LEFT_ALIGNMENT);
        subsetList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (subsetList.getSelectedIndices() != null && fctNameField.getText() != null && !fctNameField.getText().equals(""))
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    List<String> selSubsets = new ArrayList<String>();
                    for (int i: subsetList.getSelectedIndices()) selSubsets.add((String) subsetList.getModel().getElementAt(i));
                    fctData = new FunctionDefinitionObservable.FctData(fctNameField.getText(), selSubsets);
                    notifyFunctionDefinitionObservable();
                }
            }
        });
        horizBox.add(subsetList);
    }
    
    /*
    @Override
    public void setViewValue(Object o)
    {
        
    }

    @Override
    public Object getViewValue()
    {
        
    }
    
    @Override
    protected void evaluateContract()
    {
        if (isValidValue()) super.evaluateContract();
    }
    

    */

    /**
    * Requests evaluation of the value options that are usually specified in
    * {@link eu.mihosoft.vrl.annotation.ParamInfo}.
    */
    @Override
    protected void evaluationRequest(Script script)
    {
        super.evaluationRequest(script);

        // read the ugx_tag
        if (getValueOptions() != null && getValueOptions().contains("ugx_tag"))
        {
            Object property = script.getProperty("ugx_tag");
            if (property != null) ugx_tag = (String) property;
        }

        // read the fct_tag
        if (getValueOptions() != null && getValueOptions().contains("fct_tag"))
        {
            Object property = script.getProperty("fct_tag");
            if (property != null) fct_tag = (String) property;
        }
    }
    
     /**
     * This method is called after this typerepresentation has been added to a
     * method representation (including setting connector etc.). It may be used
     * to perform custom initialization based on option evaluation etc.
     */
    @Override
    public void addedToMethodRepresentation()
    {
        super.addedToMethodRepresentation();
        
        // init the views 
        init();
        
        // register at the observable for ugx-file-loads if ugx_tag given
        if (ugx_tag != null)
        {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            LoadUGXFileObservable.getInstance().addObserver(this, ugx_tag, o, windowID);
        }
        
        // get an index from functionDefinitionObservable (array index)
        if (fct_tag != null)
        {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            arrayIndex = FunctionDefinitionObservable.getInstance().receiveArrayIndex(fct_tag, o, windowID);
        }
    }
    
    /**
    * This method is called after this typerepresentation has been removed from
    * method representation (including unsetting connector etc.). It may be
    * used to perform custom finalization based on option evaluation etc.
    */
    @Override
    public void removedFromMethodRepresentation()
    {
        // revoke index from functionDefinitionObservable (array index)
        if (fct_tag != null)
        {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            FunctionDefinitionObservable.getInstance().revokeArrayIndex(fct_tag, o, windowID, arrayIndex);
        }
        
        super.removedFromMethodRepresentation();
    }

     
    /**
     * Disposes additional resources, e.g., Java 3D render threads. It will be
     * called when the parent canvas window is closed.
     */
    @Override
    public void dispose()
    {
        if (ugx_tag != null) LoadUGXFileObservable.getInstance().deleteObserver(this);
        
        if (fct_tag != null)
        {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            FunctionDefinitionObservable.getInstance().setInvalidData(fct_tag, o, windowID);
        }
        
        super.dispose();
    }
    
    /*
    @Override
    public String getValueAsCode()
    {
        // TODO this is only to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
    */
    
    protected void notifyFunctionDefinitionObservable()
    {
        int id = this.getParentMethod().getParentObject().getObjectID();
        Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
        int windowID = 0;
        
        // inform the singleton that function definitions have been made
        if (fctData != null)
            FunctionDefinitionObservable.getInstance().setFunctionDefinitions(fctData, fct_tag, o, windowID, arrayIndex);
        else
            FunctionDefinitionObservable.getInstance().setInvalidData(fct_tag, o, windowID);
    }

    
    // inherited from LoadUGXFileObserver
    @Override
    public void update(UGXFileInfo info)
    {
        // adjust data for new FileInfo
        adjustData(info);
    }
    
    
    private void adjustData(UGXFileInfo info)
    {
        // adjust model
        if (info != null)
        {
            if (!(info instanceof UGXFileInfo))
                throw new RuntimeException("UserSubsetModel: Passed data is not of required type UGXFileInfo.");
            
            subsetListModel.removeAllElements();
            
            for (int i = 0; i < ((UGXFileInfo)info).const__num_subsets(0, 0); ++i)
                subsetListModel.addElement(((UGXFileInfo)info).const__subset_name(0, 0, i));

            // reset fctData
            if (fctData != null) fctData.subsetList = null;

            // reset view in subset list
            subsetList.clearSelection();
            
            /* this is more complicated, maybe later
            for (int i = 0; i < ((UGXFileInfo)info).const__num_subsets(0, 0); ++i)
            {
                String newSubset = ((UGXFileInfo)info).const__subset_name(0, 0, i);

                // in this case we can stay with the old selected subset
                if (newSubset.equals(data))
                {
                    if (getStatus() != UserDataModel.Status.VALID) {
                        setStatus(UserDataModel.Status.WARNING);
                    }
                    return;
                }
            }
            */
        }
        else
        {
            subsetListModel.removeAllElements();
            subsetListModel.addElement("-- no grid --");
            //model.setStatus(Status.INVALID);
            //adjustView(model.getStatus());
        }

        // set the maximum size to preferred size in order to avoid stretched drop-downs
        Dimension max = subsetList.getMaximumSize();
        Dimension pref = subsetList.getPreferredSize();
        max.height = pref.height;
        subsetList.setMaximumSize(max);

        subsetList.revalidate();
    }
    
}
