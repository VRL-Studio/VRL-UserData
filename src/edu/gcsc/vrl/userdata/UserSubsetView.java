/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.Selection;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author andreasvogel
 */
public class UserSubsetView extends UserDataView {

    protected UserDataModel model;
    protected UserDataTupleType tuple;
    protected String name;
    protected JComboBox selectionView = null;
    protected Selection selection = null;
    protected Color defaultColor = null;
    protected boolean externTriggered;
    protected VTextField textView = null;

    public UserSubsetView(String theName, UserDataModel theModel,
            UserDataTupleType theTuple) {
        this.name = theName;
        this.model = theModel;
        this.tuple = theTuple;

        externTriggered = model.isExternTriggered();

        if (externTriggered) {
            selectionView = new JComboBox();
            selectionView.setAlignmentX(Component.LEFT_ALIGNMENT);
            defaultColor = selectionView.getBackground();
            selectionView.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean isLoading = false;

                    // check whether this action event is a mouse event or if it is
                    // caused by session loading
                    if (tuple.getMainCanvas() != null) {
                        VisualCanvas canvas = (VisualCanvas) tuple.getMainCanvas();
                        isLoading = canvas.isLoadingSession();
                    }

                    // if the event is a mouse event and the selection model exists
                    // change the model according to the view
                    if (selection != null && !isLoading) {
                        selection.setSelectedIndex(selectionView.getSelectedIndex());
                        selection.setSelectedObject(selectionView.getSelectedItem());
                        if (selectionView.getSelectedItem() != null) {
                            model.setData(selectionView.getSelectedItem());
                        }
                        selectionView.setBackground(defaultColor);
                        tuple.setDataOutdated();
                        tuple.storeCustomParamData();
                    }
                }
            });
        } else {
            textView = new VTextField("");
            textView.setFont(new Font("SansSerif", Font.PLAIN, 11));
            textView.setAlignmentX(Component.LEFT_ALIGNMENT);

            int height = (int) textView.getMinimumSize().getHeight();
            textView.setSize(new Dimension(120, height));
            textView.setMinimumSize(new Dimension(120, height));
            textView.setMaximumSize(new Dimension(120, height));
            textView.setPreferredSize(new Dimension(120, height));
            textView.setEditable(true);

            textView.setAlignmentY(0.5f);
            textView.setAlignmentX(0.0f);

            textView.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    model.setData(textView.getText());
                    tuple.setDataOutdated();
                    tuple.storeCustomParamData();
                }
            });


        }
    }

    @Override
    public Component getComponent() {
        if (externTriggered) {
            return selectionView;
        } else {
            return textView;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateView(UserDataModel theModel) {
        this.model = theModel;
        externTriggered = model.isExternTriggered();

        if (textView != null) {
            textView.setText((String) model.getData());
        }

        int index = 0;
        boolean modelConsistent = true;

        if (selection != null) {

            index = ((ArrayList<String>) selection.getCollection()).indexOf(model.getData());
            if (index < 0) {
                index = 0;
                modelConsistent = false;
            }

            selection.setSelectedIndex(index);
        }

        if (selectionView != null) {
            selectionView.setSelectedIndex(index);
        }

        if (modelConsistent) {
            selectionView.setBackground(defaultColor);
        } else {
            selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                    TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
        }

    }

    @Override
    public void adjustView(UGXFileInfo info, UserDataModel.Status modelStatus) {
        String currentSubset = "";
        if (model != null) {
            currentSubset = (String) model.getData();
        }

        int index = 0;

        if (info != null) {
            ArrayList<String> subsetList = new ArrayList<String>();
            for (int i = 0; i < info.const__num_subsets(0, 0); ++i) {
                subsetList.add(info.const__subset_name(0, 0, i));
            }

            index = subsetList.indexOf(currentSubset);
            if (index < 0) {
                index = 0;
            }

            selection = new Selection(subsetList);
        } else {
            ArrayList<String> subsetList = new ArrayList<String>();
            subsetList.add("-- No Grid --");
            selection = new Selection(subsetList);
        }

        selectionView.removeAllItems();
        for (Object object : selection.getCollection()) {
            selectionView.addItem(object);
        }
        selectionView.setSelectedIndex(index);

        // set the maximum size to prefered size in order to avoid stretched drop-downs
        Dimension max = selectionView.getMaximumSize();
        Dimension pref = selectionView.getPreferredSize();
        max.height = pref.height;
        selectionView.setMaximumSize(max);


        selection.setSelectedIndex(index);
        selection.setSelectedObject(index);
        if (selectionView.getSelectedItem() != null) {
            model.setData(selectionView.getSelectedItem());
        }
        tuple.setDataOutdated();
        tuple.storeCustomParamData();


        if (modelStatus == UserDataModel.Status.VALID) {
            selectionView.setBackground(defaultColor);
        } else if (modelStatus == UserDataModel.Status.WARNING) {
            selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                    TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
        } else if (modelStatus == UserDataModel.Status.INVALID) {
            selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                    TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }
}
