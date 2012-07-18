package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.Selection;
import eu.mihosoft.vrl.types.SelectionInputType;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import groovy.lang.Script;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author andreasvogel
 */
@TypeInfo(type = String.class, input = true, output = false, style = "ugx-subset-selection")
public class StringSubsetSelectionInputType extends SelectionInputType implements LoadUGXFileObserver {

    /// the tag of the grid file passed via annotations
    private String tag = null;

    /**
     * constructor
     */
    public StringSubsetSelectionInputType() {
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.PAGE_AXIS);
        setLayout(layout);

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        getSelectionView().setAlignmentX(Component.LEFT_ALIGNMENT);

        add(nameLabel);
        add(getSelectionView());

        setValueName("UG4-Subset:");

        setHideConnector(true);

    }

    @Override
    public Object getViewValue() {
        if (!getMainCanvas().isSavingSession()) {
            if (getSelectionView().getSelectedItem() != null) {
                return getSelectionView().getSelectedItem().toString();
            }
        } else {
            return super.getViewValue();
        }
        
 /*       if (tag == null) {
            throw new RuntimeException("StringSubsetSelectionInputType: no tag"
                    + " specified in ParamInfo options.");
        }
*/
        return null;
    }

    @Override
    protected void evaluationRequest(Script script) {
        Object property = null;


        if (getValueOptions() != null) {

            if (getValueOptions().contains("tag")) {
                property = script.getProperty("tag");
            }

            if (property != null) {
                if (getViewValueWithoutValidation() == null) {

                    tag = (String) property;
                }
            }
        }


        if(tag == null){
            getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option", 
                    "ParamInfo for ugx-subset-selection requires tag in options", 
                    getConnector(), MessageType.ERROR);
        }
        
        //  register this as observer at the Observable using tag
        registerAtLoadUGXFileObservable();
    }

    @Override()
    public String getValueAsCode() {
        String result = "null";

        Object o = getValue();

        if (o != null) {
            result = "\"" + o.toString() + "\"";
        }

        return result;
    }

    protected void registerAtLoadUGXFileObservable() {

        // we register at the oberver for all ugx-file-loads
        LoadUGXFileObservable.getInstance().addObserver(this, tag);

        // update with current data
        update(LoadUGXFileObservable.getInstance().getSelectedData(tag));
    }

    @Override
    public void update(UGXFileInfo data) {
        ArrayList<String> subsetList = new ArrayList<String>();

        JComboBox box = super.getSelectionView();

        if (data != null) {
            for(int i = 0; i < data.const__num_subsets(0, 0); ++i){
                subsetList.add(data.const__subset_name(0, 0, i));
            }
            box.setBackground(Color.GREEN);
            
        } else {
            subsetList.add("-- No Grid Choosen --");
            box.setBackground(Color.RED);
        }

        super.setViewValue(new Selection(subsetList));
    }
}
