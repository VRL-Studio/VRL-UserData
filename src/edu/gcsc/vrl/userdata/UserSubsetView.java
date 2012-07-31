/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.UserDataModel.Status;
import edu.gcsc.vrl.userdata.types.UserDataTupleType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    protected Color defaultColor = null;
    protected boolean externTriggered;
    protected VTextField textView = null;
    protected boolean internalAdjustment = false;

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

                    if (selectionView.getSelectedItem() != null && !internalAdjustment) {
                        model.setData(selectionView.getSelectedItem());

                        if (model.getStatus() != UserDataModel.Status.INVALID) {
                            model.setStatus(UserDataModel.Status.VALID);
                        }
                        adjustView(model.getStatus());

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
    public void adjustView(UserDataModel theModel) {
        this.model = theModel;
        externTriggered = model.isExternTriggered();

        if (textView != null) {
            textView.setText((String) model.getData());
        }
        
        if (selectionView != null) {

            internalAdjustment = true;

            if (model.getStatus() == UserDataModel.Status.INVALID) {
                selectionView.removeAllItems();
                selectionView.addItem("-- No Grid --");
            } else {
                selectionView.setSelectedItem(model.getData());
            }
            
            internalAdjustment = false;

            adjustView(model.getStatus());
        }
    }

    @Override
    public void adjustView(UGXFileInfo info) {
        internalAdjustment = true;

        selectionView.removeAllItems();
        if (info != null) {

            for (int i = 0; i < info.const__num_subsets(0, 0); ++i) {
                selectionView.addItem(info.const__subset_name(0, 0, i));
            }

            if (model != null) {
                selectionView.setSelectedItem(model.getData());
                adjustView(model.getStatus());
            }

            if (selectionView.getSelectedItem() == null) {
                selectionView.setSelectedIndex(0);
                model.setData(selectionView.getSelectedItem());
                model.setStatus(UserDataModel.Status.WARNING);
                adjustView(model.getStatus());
            }

        } else {
            selectionView.addItem("-- No Grid --");
            model.setStatus(Status.INVALID);
            adjustView(model.getStatus());
        }

        // set the maximum size to prefered size in order to avoid stretched drop-downs
        Dimension max = selectionView.getMaximumSize();
        Dimension pref = selectionView.getPreferredSize();
        max.height = pref.height;
        selectionView.setMaximumSize(max);

        selectionView.revalidate();

        internalAdjustment = false;
    }

    @Override
    public void adjustView(UserDataModel.Status status) {
        switch (status) {
            case VALID:
                selectionView.setBackground(defaultColor);
                break;
            case WARNING:
                selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.WARNING_VALUE_COLOR_KEY));
                break;
            case INVALID:
            default:
                selectionView.setBackground(tuple.getMainCanvas().getStyle().getBaseValues().getColor(
                        TypeRepresentationBase.INVALID_VALUE_COLOR_KEY));
        }
    }

    @Override
    public void closeView() {
    }
}
