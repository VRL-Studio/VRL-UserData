package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
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
        
        nFct = ((UserDependentSubsetModel) model).getNFct();
        // this should be prevented by the construction of "theName" in UserDataTupleType
        if (name.length != nFct)
            throw new RuntimeException("UserDependentSubsetView: Not enough function designators given.");
        
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
                        // (must be done by UserDataTuple, for it holds the array index)
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
        
        /*
        button = new VButton(name);
        defaultColor = button.getBackground();
        defaultTextColor = button.getForeground();
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (window == null || window.isDisposed())
                {
                    window = new SubsetSelectionWindow(this);
                    window.getTitleBar().addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mousePressed(MouseEvent e)
                        {
                            button.setForeground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                                    CanvasWindow.UPPER_ACTIVE_TITLE_COLOR_KEY));
                        }

                        @Override
                        public void mouseReleased(MouseEvent e)
                        {
                            button.setForeground(defaultTextColor);
                        }
                    });

                    tuple.getMainCanvas().addWindow(window);
                }

                if (model.getStatus() != UserDataModel.Status.INVALID) model.setStatus(Status.VALID);
                
                adjustView(model.getStatus());

                Point loc = VSwingUtil.getAbsPos(tuple);

                Point buttonLoc = new Point(loc.x + button.getX(),
                        loc.y + button.getY());

                window.setLocation(buttonLoc);

                tuple.getMainCanvas().setComponentZOrder(window, 0);
            }
        });

        // tuning tooltip to be shown all the time on mouse-over
        button.addMouseListener(new MouseAdapter()
        {
            private int defaultDismissDelay;
            private int defaultInitialDelay;
            private int defaultReshowDelay;

            @Override
            public void mouseEntered(MouseEvent me)
            {
                defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
                defaultInitialDelay = ToolTipManager.sharedInstance().getInitialDelay();
                defaultReshowDelay = ToolTipManager.sharedInstance().getReshowDelay();
                ToolTipManager.sharedInstance().setDismissDelay(60000000);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                ToolTipManager.sharedInstance().setReshowDelay(0);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ToolTipManager.sharedInstance().setDismissDelay(defaultDismissDelay);
                ToolTipManager.sharedInstance().setInitialDelay(defaultInitialDelay);
                ToolTipManager.sharedInstance().setReshowDelay(defaultReshowDelay);
            }
        });
        
        
        horizBox.add(button);
        */
        
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
        if (nFct != ((UserDependentSubsetModel) model).getNFct())
        {
            throw new RuntimeException("UserDependentSubsetView: Number of "
                    + "functions in view and model are not identical: model has "
                    + ((UserDependentSubsetModel) model).getNFct() + ", view "
                    + nFct + ". (UserDatatuple )" + tuple.getValueOptions());
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
        
        List<String> thisList = fctList;        

        // delete empty string from list
        if (thisList != null)
        {
            for (int i=0; i<thisList.size(); i++)
                if (thisList.get(i).equals("")) thisList.remove(i);
        }
        
        if (thisList != null && !thisList.isEmpty())
        {
            for (int i=0; i<nFct; i++)
            {
                // add all fct items to combobox
                for (String fct : thisList) fctSelection[i].addItem(fct);

                if (model != null)
                {
                    fctSelection[i].setSelectedIndex(((UserDependentSubsetModel)model).getSelectedFunctionIndices()[i]);
                    //fctSelection[i].setSelectedItem(((UserDependentSubsetModel)model).getSelectedFunction(i));
                }
                
                // if nothing is selected (can that happen at all?)
                if (fctSelection[i].getSelectedItem() == null) 
                {
                    ((UserDependentSubsetModel)model).setSelectedFunction(i,"", -1);
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
        ((UserDependentSubsetModel)model).adjustFunctionData(fctData);
        adjustFunctionView(fctData);
    }

    @Override
    public int[] getSelectedFunctionIndices()
    {
        // forward this
        return ((UserDependentSubsetModel)model).getSelectedFunctionIndices();
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
