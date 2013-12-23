package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author mbreit
 */
public class UserDependentSubsetView extends UserDataView implements FunctionSubsetCoordinatorFunctionObserver, FunctionSubsetCoordinatorSubsetObserver
{
    protected UserDataModel model;
    protected UserDataTupleType tuple;
    protected String name;    
    protected JComboBox fctSelection = null;
    protected JList ssSelection = null;
    DefaultListModel ssSelectionModel = null;
    protected Box horizBox;
    protected Color defaultColor = null;
    protected boolean internalAdjustment = false;
    
    // store position in array of UserDataTuples
    // requested at FunctionSubsetCoordinator
    protected int observerIndex;
    protected String fct_tag;
    
    public UserDependentSubsetView(String theName, UserDataModel theModel, UserDataTupleType theTuple)
    {
        this.name = theName;
        this.model = theModel;
        this.tuple = theTuple;
        
        this.observerIndex = -1;
        this.fct_tag = tuple.getFctTag();
        
        // create the box that will accomodate both ComboBoxes
        horizBox = Box.createHorizontalBox();
        horizBox.setAlignmentX(LEFT_ALIGNMENT);
        
        fctSelection = new JComboBox();
        fctSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
        defaultColor = fctSelection.getBackground();
        fctSelection.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fctSelection.getSelectedItem() != null && !internalAdjustment)
                {
                    ((UserDependentSubsetModel)model).setSelectedFunction(
                        (String) fctSelection.getSelectedItem(), fctSelection.getSelectedIndex());
                    
                    // update subset list accordingly
                    // (must be done by UserDataTuple, for it holds the array index)
                    notifySubsetObserver();

                    adjustView(model.getStatus());

                    tuple.setDataOutdated();
                    tuple.storeCustomParamData();
                }
            }
        });        
        horizBox.add(fctSelection);
        
        ssSelectionModel = new DefaultListModel();
        ssSelection = new JList(ssSelectionModel);
        ssSelection.setSelectionModel(new DefaultListSelectionModel()
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
        ssSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
        defaultColor = ssSelection.getBackground();
        ssSelection.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {   
                if (ssSelection.getSelectedIndices() != null && !internalAdjustment)
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    List<String> selSubsets = new ArrayList<String>();
                    for (int i: ssSelection.getSelectedIndices()) selSubsets.add((String) ssSelection.getModel().getElementAt(i));
                    ((UserDependentSubsetModel)model).setSelectedSubsets(selSubsets, ssSelection.getSelectedIndices());

                    adjustView(model.getStatus());

                    tuple.setDataOutdated();
                    tuple.storeCustomParamData();
                }
            }
        });
        horizBox.add(ssSelection);
    }

    
    @Override
    public Component getComponent()
    {
        return horizBox;
    }

    
    @Override
    @SuppressWarnings("unchecked")
    public void adjustView(UserDataModel theModel)
    {
        this.model = theModel;
        
        if (fctSelection != null)
        {
            internalAdjustment = true;

            if (model.getStatus() == UserDataModel.Status.INVALID)
            {
                fctSelection.removeAllItems();
                fctSelection.addItem("-- no fct def --");
            }
            else fctSelection.setSelectedItem(((UserDependentSubsetModel)model).getSelectedFunction());
            
            internalAdjustment = false;

            adjustView(model.getStatus());
        }
        
        if (ssSelection != null)
        {
            internalAdjustment = true;

            if (model.getStatus() == UserDataModel.Status.INVALID)
            {
                ssSelectionModel.removeAllElements();
                ssSelectionModel.addElement("-- no fct sel --");
            }
            else ssSelection.setSelectedIndices(((UserDependentSubsetModel)model).getSelectedSubsetIndices());
            
            internalAdjustment = false;

            adjustView(model.getStatus());
        }
    }

    
    /**
     * Not to be used.
     * Use adjustFunctionView() and adjustSubsetView() instead;
     * @param fctDefData
     */
    @Override
    public void adjustView(Object fctDefData)
    {
         throw new RuntimeException("UserDependentSubsetView: Call to adjustView() which is not allowed.");
    }
    
    
    /**
     *  Adjusting the viewable object according to function definition data.
     * 
     * @param fctList list of defined functions
     */
    public void adjustFunctionView(List<String> fctList)
    {
        internalAdjustment = true;

        fctSelection.removeAllItems();
        
        if (fctList != null && !fctList.isEmpty())
        {
            for (String fct : fctList) fctSelection.addItem(fct);

            if (model != null)
            {
                fctSelection.setSelectedItem(((UserDependentSubsetModel)model).getSelectedFunction());
                // update subset list accordingly
                notifySubsetObserver();
            }

            if (fctSelection.getSelectedItem() == null) 
            {
                ((UserDependentSubsetModel)model).setSelectedFunction("", -1);
                ((UserDependentSubsetModel)model).adjustSubsetData(null);
                adjustSubsetView(null);
                if (model.getStatus() != UserDataModel.Status.INVALID) model.setStatus(UserDataModel.Status.WARNING);
                adjustView(model.getStatus());
            }
        }
        else
        {
            fctSelection.addItem("-- no fct def --");
            List<String> emptyList = new ArrayList<String>();
            ((UserDependentSubsetModel)model).adjustSubsetData(emptyList);
            adjustSubsetView(emptyList);
            model.setStatus(Status.INVALID);
            adjustView(model.getStatus());
        }

        // set the maximum size to preferred size in order to avoid stretched drop-downs
        Dimension max = fctSelection.getMaximumSize();
        Dimension pref = fctSelection.getPreferredSize();
        max.height = pref.height;
        fctSelection.setMaximumSize(pref);

        fctSelection.revalidate();

        internalAdjustment = false;
    }
    
    
    /**
     *  Adjusting the viewable object according to subset definition data.
     * 
     * @param ssList list of defined functions
     */
    public void adjustSubsetView(List<String> ssList)
    {
        internalAdjustment = true;

        ssSelectionModel.removeAllElements();
        
        if (ssList == null)
        {
            ssSelectionModel.addElement("-- no fct sel --");
            model.setStatus(Status.INVALID);
            adjustView(model.getStatus());
        }
        else if (ssList.isEmpty())
        {
            ssSelectionModel.addElement("-- no subsets --");
            model.setStatus(Status.INVALID);
            adjustView(model.getStatus());
        }
        else
        {
            for (String ss : ssList) ssSelectionModel.addElement(ss);

            if (model != null)
            {
                ssSelection.setSelectedIndices(((UserDependentSubsetModel)model).getSelectedSubsetIndices());
                model.setStatus(Status.VALID);
                adjustView(model.getStatus());
            }

            if (ssSelection.getSelectedIndices().length == 0) 
            {
                ((UserDependentSubsetModel)model).setSelectedSubsets(new ArrayList<String>(), new int[]{});
                if (model.getStatus() != UserDataModel.Status.INVALID) model.setStatus(UserDataModel.Status.WARNING);
                adjustView(model.getStatus());
            }
        }

        // set the maximum size to prefered size in order to avoid stretched drop-downs
        Dimension max = ssSelection.getMaximumSize();
        Dimension pref = ssSelection.getPreferredSize();
        max.height = pref.height;
        ssSelection.setMaximumSize(pref);

        ssSelection.revalidate();

        internalAdjustment = false;
    }

    @Override
    public void adjustView(UserDataModel.Status status)
    {
        switch (status)
        {
            case VALID:
                fctSelection.setBackground(defaultColor);
                ssSelection.setBackground(defaultColor);
                break;
            case WARNING:
                fctSelection.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                ssSelection.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                break;
            case INVALID:
            default:
                fctSelection.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
                ssSelection.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }

    @Override
    public void closeView() {}

    
    public void addAsObserver()
    {
        // register at the FunctionSubsetCoordinator if fct_tag given
        if (fct_tag != null)
        {
            int id = tuple.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) tuple.getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            observerIndex = FunctionSubsetCoordinator.getInstance().requestArrayIndex(this, this, fct_tag, o, windowID);
        }
        else
        {
            throw new RuntimeException("Requested an Object of type UserDependentSubsetView (\"S\") in UserDataTuple,\n"
                                        + "but no fct_tag specified in the options!");
        }
    }
    
    public void removeAsObserver()
    {
        // register at the FunctionSubsetCoordinator if fct_tag given
        if (fct_tag != null)
        {
            int id = tuple.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) tuple.getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            
            FunctionSubsetCoordinator.getInstance().revokeArrayIndex(fct_tag, o, windowID, observerIndex);
        }
    }
    
        
    // Implementation of FunctionSubsetCoordinatorFunctionObserver
    @Override
    public void updateFunctions(List<String> fctData)
    {
        ((UserDependentSubsetModel)model).adjustFunctionData(fctData);
        adjustFunctionView(fctData);
    }

    @Override
    public int getSelectedFunction()
    {
        // forward this
        return ((UserDependentSubsetModel)model).getSelectedFunctionIndex();
    }
    
    public void notifySubsetObserver()
    {
        if (observerIndex != -1)
        {
            int id = tuple.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) tuple.getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;

            FunctionSubsetCoordinator.getInstance().notifySubsetObserver(observerIndex, fct_tag, o, windowID);
        }
    }
    
    // Implementation of FunctionSubsetCoordinatorSubsetObserver
    @Override
    public void updateSubsets(List<String> ssData)
    {
        ((UserDependentSubsetModel)model).adjustSubsetData(ssData);
        adjustSubsetView(ssData);

    }

}
