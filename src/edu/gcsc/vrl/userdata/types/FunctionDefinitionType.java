package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.FunctionDefinition;
import edu.gcsc.vrl.userdata.FunctionDefinitionObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObserver;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
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
import javax.swing.text.Position;

/**
 * FunctionDefinitionType class.
 * This type is used to represent a function definition class.
 * It is meant to be used in a class that will enable the user to pick a
 * geometry and then define functions on subsets of this geometry.
 * 
 * It is crucial to the implementation of the FunctionDefinitionType that the
 * loading dialog feeding the LoadUGXFileObservable be in the same class
 * representation window as the FunctionDefinitions.
 * 
 * @author mbreit
 */
@TypeInfo(type = FunctionDefinition.class, input = true, output = false, style = "default")
public class FunctionDefinitionType extends TypeRepresentationBase implements Serializable, LoadUGXFileObserver
{
    private static final long serialVersionUID = 1L;
    
    protected String name = null;
    protected VTextField fctNameField = null;
    protected JList subsetList = null;
    DefaultListModel subsetListModel = null;
    FunctionDefinition fd;
    boolean internalAdjustment;
    
    // index for identification of array position in function def array
    // (to be provided by FunctionDefinitionObservable)
    int arrayIndex;  
    
    private String ugx_tag = null;
    private String fct_tag = null;
      
    
    public FunctionDefinitionType()
    {
        // hide connector 
        setHideConnector(true);
        fd = new FunctionDefinition();
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
        //add(horizBox);
        
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
                if (subsetList.getSelectedIndices() != null && fctNameField.getText() != null)
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    /*List<String> selSubsets = new ArrayList<String>();
                    for (int i: subsetList.getSelectedIndices()) selSubsets.add((String) subsetList.getModel().getElementAt(i));
                    fd.setFctData(new FunctionDefinitionObservable.FctData(fctNameField.getText(), selSubsets));
                    */
                    fd.getFctData().fctName = fctNameField.getText();
                    //storeCustomParamData();
                    notifyFunctionDefinitionObservable();
                }
            }
        });
        
        add(fctNameField);
        
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
                if (internalAdjustment) return;
                if (subsetList.getSelectedIndices() != null && fctNameField.getText() != null)
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    List<String> selSubsets = new ArrayList<String>();
                    for (int i: subsetList.getSelectedIndices()) selSubsets.add((String) subsetList.getModel().getElementAt(i));
                    fd.getFctData().subsetList = selSubsets;
                    
                    //storeCustomParamData();
                    notifyFunctionDefinitionObservable();
                }
            }
        });
        add(subsetList);
    }
    
    @Override
    public void setViewValue(Object o)
    {
        if (o instanceof FunctionDefinition)
        {
            // copy fct def data here
            FunctionDefinition fctDef = (FunctionDefinition) o;
            fd = fctDef;
            
            // the available subsets might not be up to date
            if (ugx_tag != null)
            {
                int objectID = this.getParentMethod().getParentObject().getObjectID();
                //Object obj = ((VisualCanvas) getMainCanvas()).getInspector().getObject(objectID);
                int windowID = 0;
                LoadUGXFileObservable.getInstance().notifyObserver(this, ugx_tag, objectID, windowID);
            }
            else adjustView(); // this also notifies observable
        }
    }

    @Override
    public Object getViewValue()
    {
        return fd;
    }
    
    
     @Override
    protected void evaluateContract()
    {
        if (isValidValue()) super.evaluateContract();
    }
    

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
    
    /*
    @Override
    public void evaluateCustomParamData()
    {
        super.evaluateCustomParamData();
        FunctionDefinition tmp = (FunctionDefinition) getCustomData().get("FctDef:" + arrayIndex);
        if (tmp != null)
        {
            fd = tmp;
            adjustView();
        }
        else
        {
//                throw new RuntimeException("UserDataTupleType:evaluateCustomParamData:"
//                        + " cannot read custom data correctly.");
        }

    }

    public void storeCustomParamData()
    {
        CustomParamData pData = getCustomData();

        if (pData == null) pData = new CustomParamData();
        
        pData.put("FctDef:" + arrayIndex, fd);
        
        setCustomData(pData);
    }
    */
    
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
        int objectID = this.getParentMethod().getParentObject().getObjectID();
        int windowID = 0;
        
        if (ugx_tag != null)
            LoadUGXFileObservable.getInstance().addObserver(this, ugx_tag, objectID, windowID);
        
        // get an index from functionDefinitionObservable (array index)
        if (fct_tag != null)
            arrayIndex = FunctionDefinitionObservable.getInstance().receiveArrayIndex(fct_tag, windowID);
        
        // update subset list
        LoadUGXFileObservable.getInstance().notifyObserver(this, ugx_tag, objectID, windowID);
        
        //if (!getMainCanvas().isLoadingSession()) storeCustomParamData();
    }

     
    /**
     * Disposes additional resources, e.g., Java 3D render threads. It will be
     * called when the parent canvas window is closed.
     */
    @Override
    public void dispose()
    {
        if (ugx_tag != null)
        {
            int objectID = this.getParentMethod().getParentObject().getObjectID();
            int windowID = 0;
            LoadUGXFileObservable.getInstance().deleteObserver(this, ugx_tag, objectID, windowID);
        }
        
        if (fct_tag != null)
        {
            int windowID = 0;
            FunctionDefinitionObservable.getInstance().revokeArrayIndex(fct_tag, windowID, arrayIndex);
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
        int windowID = 0;
        
        // inform the singleton that function definitions have been made
        if (fd.getFctData() != null)
            FunctionDefinitionObservable.getInstance().setFunctionDefinitions(fd.getFctData(), fct_tag, windowID, arrayIndex);
        else
            FunctionDefinitionObservable.getInstance().setInvalid(fct_tag, windowID, arrayIndex);
    }

    
    // inherited from LoadUGXFileObserver
    @Override
    public void update(UGXFileInfo info)
    {
        // adjust data for new FileInfo
        adjustView(info);
        //storeCustomParamData();
    }
    
    
    private void adjustView(UGXFileInfo info)
    {
        // adjust displayed subset list
        boolean mem_internalAdjustment = internalAdjustment;
        internalAdjustment = true;
        if (info != null)
        {
            if (!(info instanceof UGXFileInfo))
                throw new RuntimeException("UserSubsetModel: Passed data is not of required type UGXFileInfo.");
            
            subsetListModel.removeAllElements();
            
            if (((UGXFileInfo)info).const__num_grids() != 1)
                throw new RuntimeException("UGX file must contain exactly one grid.");
            
            if (((UGXFileInfo)info).const__num_subset_handlers(0) < 1)
                throw new RuntimeException("UGX file must contain at least one subset handler.");
            
            for (int i = 0; i < ((UGXFileInfo)info).const__num_subsets(0, 0); ++i)
                subsetListModel.addElement(((UGXFileInfo)info).const__subset_name(0, 0, i));
        }
        else
        {
            subsetListModel.removeAllElements();
            subsetListModel.addElement("-- no grid --");   
        }
        internalAdjustment = mem_internalAdjustment;
        
        // adjust selections
        adjustView();
    }
    
    private void adjustView()
    {
        boolean mem_internalAdjustment = internalAdjustment;
        internalAdjustment = true;
        
        fctNameField.setText("");
            
        /*if (fd == null)// || fd.getFctData().subsetList.isEmpty())
        {
            subsetList.clearSelection();
        }
        // if no valid geometry given, keep selections in model
        // (in case the geometry can be recovered later)
        else )
        {
            subsetList.clearSelection();
        }*/
        // update avail. subsets list only if new geometry is valid
        if (!(subsetListModel.size() == 1 && "-- no grid --".equals(subsetListModel.get(0))))
        {
            fctNameField.setText(fd.getFctData().fctName);
            
            // find indices of fd's subsets in subsetList
            List<Integer> indexList = new ArrayList<Integer>();
            boolean allFound = true;
            List<String> toBeRemoved = new ArrayList<String>();
            for (String ss: fd.getFctData().subsetList)
            {
                if (subsetList.getModel().getSize() > 0)
                {
                    int firstMatch = subsetList.getNextMatch(ss, 0, Position.Bias.Forward);
                    while (firstMatch != -1 && !ss.equals(subsetList.getModel().getElementAt(firstMatch)))
                        firstMatch = subsetList.getNextMatch(ss, firstMatch, Position.Bias.Forward);
                    
                    if (firstMatch != -1) indexList.add(new Integer(firstMatch));
                    else
                    {
                        // we need to remove this subset from the selection,
                        // as it seems no longer to be available
                        toBeRemoved.add(ss);
                        allFound = false;
                    }
                }
            }
            
            // remove subsets no longer available
            for (String ss: toBeRemoved)
                fd.getFctData().subsetList.remove(ss);
            
            // set the selection
            if (!indexList.isEmpty())
            {
                int[] indices = new int[indexList.size()];
                for (int i=0; i<indexList.size(); i++) indices[i] = indexList.get(i).intValue();

                subsetList.setSelectedIndices(indices);
            
                // warn if not all selected functions found
                if (!allFound)
                {
                    eu.mihosoft.vrl.system.VMessage.warning("Missing subsets", "Not "
                            + "all subsets stored in FunctionDefinitionType are "
                            + "present in the current UGX file.");
                }
            }
            
            // notify observable
            notifyFunctionDefinitionObservable();
        }

        // set the maximum size to preferred size in order to avoid stretched drop-downs
        Dimension max = subsetList.getMaximumSize();
        Dimension pref = subsetList.getPreferredSize();
        max.height = pref.height;
        subsetList.setMaximumSize(max);

        subsetList.revalidate();
        
        internalAdjustment = mem_internalAdjustment;
    }
    
    @Override
    public String getValueAsCode()
    {
        // TODO: This is a stub. The real code will eventually have to be implemented!
        return "null";
    }
    
}
