package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.system.VMessage;
import eu.mihosoft.vrl.visual.VButton;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
    protected String[] name;
    protected JComboBox[] fctSelection;
    protected JList ssSelection = null;
    protected DefaultListModel ssSelectionModel = null;
    //protected SubsetSelectionWindow window = null;
    protected VButton button = null;
    protected Box horizBox;
    protected Color defaultColor = null;
    protected Color defaultTextColor = null;
    protected boolean internalAdjustment;
    protected int nFct;
    
    // store position in array of UserDataTuples
    // requested at FunctionSubsetCoordinator
    protected int observerIndex;
    protected String fct_tag;
    
    public UserDependentSubsetView(String theName, UserDataModel theModel, UserDataTupleType theTuple)
    {
        this.name = theName.split(",");

        this.model = theModel;
        this.tuple = theTuple;
        
        nFct = ((UserDependentSubsetModel) model).getnFct();
        // this should be prevented by the construction of "theName" in UserDataTupleType
        if (name.length != nFct)
        {
            throw new RuntimeException("UserDependentSubsetView: Number of function "
                    + "designators incorrect: Model has "+nFct+", but "
                    + name.length+" given here.");
        }
        
        fctSelection = new JComboBox[nFct];
        
        this.observerIndex = -1;
        this.fct_tag = tuple.getFctTag();
        
        // create the box that will accomodate both functions and subsets
        horizBox = Box.createHorizontalBox();
        horizBox.setAlignmentX(LEFT_ALIGNMENT);
        
        // create vertical box for function selections
        Box vBox = Box.createVerticalBox();
        
        for (int i=0; i<nFct; i++)
        {
            fctSelection[i] = new JComboBox();
            fctSelection[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            defaultColor = fctSelection[i].getBackground();
            fctSelection[i].setActionCommand(""+i);
            fctSelection[i].addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    int j = Integer.parseInt(e.getActionCommand());
                    if (fctSelection[j].getSelectedItem() != null && !internalAdjustment)
                    {
                        ((UserDependentSubsetModel)model).setSelectedFunction(
                            j, (String) fctSelection[j].getSelectedItem(), fctSelection[j].getSelectedIndex());

                        // update subset list accordingly
                        notifySubsetObserver();

                        adjustView(model.getStatus());

                        tuple.setDataOutdated();
                        tuple.storeCustomParamData();
                    }
                }
            });
            
            Box hBoxLv2 = Box.createHorizontalBox();
            hBoxLv2.setAlignmentX(RIGHT_ALIGNMENT);
            hBoxLv2.add(new JLabel(name[i]));
            hBoxLv2.add(fctSelection[i]);
            vBox.add(hBoxLv2);
        }
        
        horizBox.add(vBox);
        
        
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
                    int[] selInd = ssSelection.getSelectedIndices();
                    String[] selSubsets = new String[selInd.length];
                    for (int i=0; i<selInd.length; i++) selSubsets[i] = ((String) ssSelection.getModel().getElementAt(selInd[i]));
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
        if (nFct != ((UserDependentSubsetModel) model).getnFct())
        {
            throw new RuntimeException("UserDependentSubsetView: Number of "
                    + "functions in view and model are not identical: model has "
                    + ((UserDependentSubsetModel) model).getnFct() + ", view "
                    + nFct + ". (UserDatatuple " + tuple.getValueOptions() + ")");
        }
        
        if (fctSelection != null)
        {
            internalAdjustment = true;

            if (model.getStatus() == UserDataModel.Status.INVALID)
            {
                for (int i=0; i<nFct; i++)
                {
                    fctSelection[i].removeAllItems();
                    fctSelection[i].addItem("-- no fct def --");
                }
            }
            else
            {
                String[] selFcts = ((UserDependentSubsetModel)model).getSelectedFunctions();
                for (int i=0; i<nFct; i++) fctSelection[i].setSelectedItem(selFcts[i]);
            }
            
            internalAdjustment = false;
            
            // update subset list accordingly
            notifySubsetObserver();

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
         throw new RuntimeException("UserDependentSubsetView: Call to adjustView() which is not allowed.\n");
    }
    
    
    /**
     *  Adjusting the viewable object according to function definition data.
     * 
     * @param fctList list of defined functions
     */
    public synchronized void adjustFunctionView(List<String> fctList)
    {
        boolean saveIntAdjState = internalAdjustment;
        internalAdjustment = true;
        
        for (int i=0; i<nFct; i++) fctSelection[i].removeAllItems();  

        // delete empty string from list
        /*if (fctList != null)
        {
            for (int i=0; i<fctList.size(); i++)
                if (fctList.get(i).equals("")) fctList.remove(i);
        }
        */
        if (fctList != null && !fctList.isEmpty())
        {
            for (int i=0; i<nFct; i++)
            {
                // add all fct items to combobox
                for (String fct : fctList) fctSelection[i].addItem(fct);

                if (model != null)
                {
                    try
                    {
                        fctSelection[i].setSelectedIndex(((UserDependentSubsetModel)model).getSelectedFunctionIndices()[i]);
                    }
                    // if one of the indices is out of bounds
                    // (should not happen, as model is always updated beforehand
                    // except when loading project)
                    catch (java.lang.IllegalArgumentException ex)
                    {
                        VMessage.warning("Illegal Argument in UserDependentSubsetModel",
                                        "The model holds a function index as selected which does not exist in the corresponding view"
                                        + "(index is " + ((UserDependentSubsetModel)model).getSelectedFunctionIndices()[i]
                                        + ", but only " + fctSelection[i].getItemCount()
                                        + " item(s) in the view. "
                                        + "This is an error, strictly-speaking. Please report it.");
                    }
                }
                // if nothing is selected
                if (fctSelection[i].getSelectedItem() == null) 
                {
                    ((UserDependentSubsetModel)model).setSelectedFunction(i,((UserDependentSubsetModel)model).getSelectedFunction(i), -1);
                    ((UserDependentSubsetModel)model).adjustSubsetData(null);
                    adjustSubsetView(null);
                    model.setStatus(UserDataModel.Status.INVALID);
                    adjustView(model.getStatus());
                }
            }
            
            // update subset list accordingly
            notifySubsetObserver();
        }
        else
        {
            for (int i=0; i<nFct; i++)
            {
                fctSelection[i].addItem("-- no fct def --");
                List<String> emptyList = new ArrayList<String>();
                ((UserDependentSubsetModel)model).adjustSubsetData(emptyList);
                adjustSubsetView(emptyList);
                model.setStatus(Status.INVALID);
                adjustView(model.getStatus());
            }
        }

        // set the maximum size to preferred size in order to avoid stretched drop-downs
        for (int i=0; i<nFct; i++)
        {
            Dimension max = fctSelection[i].getMaximumSize();
            Dimension pref = fctSelection[i].getPreferredSize();
            max.height = pref.height;
            fctSelection[i].setMaximumSize(pref);
        
            fctSelection[i].revalidate();
        }
        
        internalAdjustment = saveIntAdjState;
    }
    
    
    /**
     *  Adjusting the viewable object according to subset definition data.
     * 
     * @param ssList list of defined functions
     */
    public void adjustSubsetView(List<String> ssList)
    {
        boolean saveIntAdjState = internalAdjustment;
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

            /*
            if (ssSelection.getSelectedIndices().length == 0) 
            {
                ((UserDependentSubsetModel)model).setSelectedSubsets(new String[]{}, new int[]{});
                if (model.getStatus() != UserDataModel.Status.INVALID) model.setStatus(UserDataModel.Status.WARNING);
                adjustView(model.getStatus());
            }
                    */
        }

        // set the maximum size to prefered size in order to avoid stretched drop-downs
        Dimension max = ssSelection.getMaximumSize();
        Dimension pref = ssSelection.getPreferredSize();
        max.height = pref.height;
        ssSelection.setMaximumSize(pref);

        ssSelection.revalidate();

        internalAdjustment = saveIntAdjState;
    }

    @Override
    public void adjustView(UserDataModel.Status status)
    {
        switch (status)
        {
            case VALID:
                for (int i=0; i<nFct; i++) fctSelection[i].setBackground(defaultColor);
                ssSelection.setBackground(defaultColor);
                break;
            case WARNING:
                for (int i=0; i<nFct; i++) fctSelection[i].setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                ssSelection.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                break;
            case INVALID:
            default:
                for (int i=0; i<nFct; i++) fctSelection[i].setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
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
            int windowID = 0;
            observerIndex = FunctionSubsetCoordinator.getInstance().requestKey(this, this, fct_tag, windowID);
        }
        else
        {
            throw new RuntimeException("Requested an Object of type UserDependentSubsetView (\"S\") in UserDataTuple,"
                                        + "but no fct_tag specified in the options!\n");
        }
    }
    
    public void removeAsObserver()
    {
        // register at the FunctionSubsetCoordinator if fct_tag given
        if (fct_tag != null)
        {
            int windowID = 0;
            
            FunctionSubsetCoordinator.getInstance().revokeKey(fct_tag, windowID, observerIndex);
        }
    }
    
        
    // Implementation of FunctionSubsetCoordinatorFunctionObserver
    @Override
    public void updateFunctions(List<String> fctData)
    {
        // add "" to the head of the list
        fctData.add(0, "");
        
        ((UserDependentSubsetModel)model).adjustFunctionData(fctData);
        adjustFunctionView(fctData);
    }

    @Override
    public String[] getSelectedFunctions()
    {
        // forward this
        return ((UserDependentSubsetModel)model).getSelectedFunctions();
    }
    
    public void notifySubsetObserver()
    {
        if (observerIndex != -1)
        {
            int windowID = 0;

            FunctionSubsetCoordinator.getInstance().notifySubsetObserver(observerIndex, fct_tag, windowID);
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
