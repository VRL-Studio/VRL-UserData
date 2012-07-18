package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.Selection;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import groovy.lang.Script;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 * This is the visualization for Strings, that should represent a Subset (or a
 * list of subsets) in an ugx-File. Currently it is implemented via a drop-down
 * box but this may change in the future in order to allow selection of several 
 * subsets at the same time.
 * Plese note, that the representation uses a tag identifier in the options 
 * section of the ParamInfo in order to destinguish which grid should be used
 * for the subsets list.
 * 
 * @author andreasvogel
 */
@TypeInfo(type = String.class, input = true, output = false, style = "ugx-subset-selection")
public class StringSubsetSelectionInputType extends TypeRepresentationBase implements LoadUGXFileObserver {

    /// the tag of the grid file passed via annotations
    private String tag = null;
    
    /// default color of the canvas
    private Color defaultColor = null;

    /// view for the selection
    private JComboBox selectionView = new JComboBox();
    
    /// the selection data
    private Selection selectionModel;

    /**
     * constructor
     */
    public StringSubsetSelectionInputType() {
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.PAGE_AXIS);
        setLayout(layout);

        setHideConnector(true);

        setValueName("UG4-Subset:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionView.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(nameLabel);
        add(selectionView);
        
        selectionView.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isLoading = false;

                // check whether this action event is a mouse event or if it is
                // caused by session loading
                if (getMainCanvas() != null) {
                    VisualCanvas canvas = (VisualCanvas) getMainCanvas();
                    isLoading = canvas.isLoadingSession();
                }

                // if the event is a mouse event and the selection model exists
                // change the model according to the view
                if (selectionModel != null && !isLoading) {
                    selectionModel.setSelectedIndex(selectionView.getSelectedIndex());
                    selectionModel.setSelectedObject(selectionView.getSelectedItem());
                    setDataOutdated();
                }
            }
        });

    }

        @Override
    @SuppressWarnings("unchecked") // we must be compatible with 1.6
    public void setViewValue(Object o) {
        emptyView();
        if (o instanceof Selection) {
            selectionModel = (Selection) o;
            for (Object object : selectionModel.getCollection()) {
                selectionView.addItem(object);
            }
            
            int index = 0;
            
            if (selectionModel.getSelectedIndex()!=null) {
                index = selectionModel.getSelectedIndex();
            }
    
            selectionView.setSelectedIndex(index);
        }
    }

    @Override
    public Object getViewValue() {
        
        if (tag == null) {
            throw new RuntimeException("StringSubsetSelectionInputType: no tag"
                    + " specified in ParamInfo options.");
        }

        if (!getMainCanvas().isSavingSession()) {
            if (selectionView.getSelectedItem() != null) {
                return selectionView.getSelectedItem().toString();
            }
        } else {
            return selectionModel;
        }

        return null;
    }

    @Override
    public void emptyView() {
        selectionModel = null;
        selectionView.removeAllItems();
    }

    @Override
    protected void evaluationRequest(Script script) {
        Object property = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("tag")) {
                property = script.getProperty("tag");
            }

            if (property != null) {
                tag = (String) property;
            }
        }

        if (tag == null) {
            getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option",
                    "ParamInfo for ugx-subset-selection requires tag in options",
                    getConnector(), MessageType.ERROR);
        }

        // we register at the observable for ugx-file-loads of this tag
        LoadUGXFileObservable.getInstance().addObserver(this, tag);
    }

    @Override()
    public String getValueAsCode() {
        Object o = getValue();
        if (o != null) {
            return "\"" + o.toString() + "\"";
        }
        else return "null";
    }

    @Override
    public void dispose() {
        // we remove from the observable for ugx-file-loads of this tag
        LoadUGXFileObservable.getInstance().deleteObserver(this, tag);

        super.dispose();
    }

    @Override
    public void update(UGXFileInfo data) {
        ArrayList<String> subsetList = new ArrayList<String>();

        if (data != null) {
            for (int i = 0; i < data.const__num_subsets(0, 0); ++i) {
                subsetList.add(data.const__subset_name(0, 0, i));
            }
            selectionView.setBackground(defaultColor);

        } else {
            subsetList.add("-- No Grid --");
            defaultColor = selectionView.getBackground();
            selectionView.setBackground(getMainCanvas().getStyle().getBaseValues().getColor(
                                TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }

        setViewValue(new Selection(subsetList));
    }
}
