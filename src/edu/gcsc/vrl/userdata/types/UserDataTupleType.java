/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObserver;
import edu.gcsc.vrl.userdata.UserDataFactory;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserDataTuple;
import edu.gcsc.vrl.userdata.UserDataView;
import edu.gcsc.vrl.userdata.UserDependentSubsetView;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.CustomParamData;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import groovy.lang.Script;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Box;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = UserDataTuple.class, input = true, output = false, style = "default")
public class UserDataTupleType extends TypeRepresentationBase implements Serializable, LoadUGXFileObserver {

    /**
     * @return the datas
     */
    protected ArrayList<Data> getDatas() {
        return datas;
    }

    protected static class Data {

        UserDataModel.Category category = null;
        String name = null;
        boolean separateBehind = false;
        UserDataModel model = null;
        UserDataView view = null;
        int suppInfo;   // for the UserDependentSubset
    }
    // list of userdata
    protected ArrayList<Data> datas = new ArrayList<Data>();
    // ugx_tag, fct_tag (if triggered externally)
    protected String ugx_tag = null;
    protected String ugx_globalTag = null;
    protected String fct_tag = null;
    protected boolean evalReqPerformed = false;

    public UserDataTupleType()
    {
        // hide connector 
        setHideConnector(true);
    }

    public void init() {

        removeAll();

        // create a VBoxLayout and set it as layout
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
        setLayout(layout);
        setLayoutType(LayoutType.STATIC);

        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(nameLabel);

        // create horizontal box for vertical button columns
        Box horizbox = Box.createHorizontalBox();
        horizbox.setAlignmentX(LEFT_ALIGNMENT);
        add(horizbox);

        // create box for buttons
        Box box = Box.createVerticalBox();

        for (Data data : datas) {

            // create new model
            data.model = UserDataFactory.createModel(data.category, data.suppInfo);
            
            // set userdata to external triggering depending on presence of ugx_tag
            if (ugx_tag == null && ugx_globalTag == null && fct_tag == null) {
                data.model.setExternTriggered(false);
            } else {
                data.model.setExternTriggered(true);
            }

            // create new view
            data.view = UserDataFactory.createView(data.category, data.name, data.model, this);

            // add view to box
            box.add(data.view.getComponent());

            // check if new box needed
            if (data.separateBehind) {

                // add filled box to visualization
                horizbox.add(box);

                // create new (empty box) to be filled with next user data
                box = Box.createVerticalBox();
            }
        }

        // add last box
        horizbox.add(box);
    }

    @Override
    public void setViewValue(Object o) {

        if (o instanceof UserDataTuple) {
            UserDataTuple tuple = (UserDataTuple) o;

            // copy data only if valid size (e.g. not loaded from file)
            if (tuple.size() == datas.size()) {
                for (int i = 0; i < datas.size(); ++i) {
                    Data data = datas.get(i);
                    data.model.setData(tuple.getData(i));
                    data.view.adjustView(data.model);
                }
            }
        }
    }

    @Override
    public Object getViewValue() {

        UserDataTuple tuple = new UserDataTuple();

        for (int i = 0; i < datas.size(); i++) {
            Data data = datas.get(i);

            try {
                tuple.add(data.model.createUserData());
            } catch (Exception e) {e.printStackTrace();}
        }

        return tuple;
    }

    @Override
    protected void evaluateContract() {

        for (int i = 0; i < datas.size(); i++) {
            Data data = datas.get(i);

            if ((ugx_tag != null || ugx_globalTag != null || fct_tag != null)
                    && !getMainCanvas().isLoadingSession()
                    && !getMainCanvas().isSavingSession()) {

                if (data.model.getStatus() == UserDataModel.Status.INVALID) {

                    getMainCanvas().getMessageBox().addMessage("User Data not specified",
                            "User Data '" + data.name + "' has not been specified correctly. Please make sure to select a valid grid and valid user data parameter.",
                            getConnector(), MessageType.ERROR);

                    invalidateValue();
                }

                String checkMsg = data.model.checkUserData();
                if (!checkMsg.isEmpty()) {

                    getMainCanvas().getMessageBox().addMessage("User Data Code invalid",
                            "User Data '" + data.name + "': " + checkMsg,
                            getConnector(), MessageType.ERROR);

                    invalidateValue();
                }

                if (data.model.createUserData() == null) {

                    getMainCanvas().getMessageBox().addMessage("User Data Code invalid",
                            "User Data '" + data.name + "' has been specified with invalid code.",
                            getConnector(), MessageType.ERROR);

                    invalidateValue();
                }
            }

        }

        if (isValidValue()) {
            super.evaluateContract();
        }

    }

    @Override
    public void evaluateCustomParamData() {
        super.evaluateCustomParamData();

        for (int i = 0; i < datas.size(); i++) {

            Data data = datas.get(i);
            //if (data.category != UserDataModel.Category.DEPENDENT_SUBSET)
            {
                UserDataModel tmpModel = (UserDataModel) getCustomData().get("UserDataTuple:" + i);
                if (tmpModel != null) {

                    data.model.setModel(tmpModel);
                    data.view.adjustView(data.model);
                } else {
    //                throw new RuntimeException("UserDataTupleType:evaluateCustomParamData:"
    //                        + " cannot read custom data correctly.");
                }
            }
        }

    }

    public void storeCustomParamData() {

        // never meddle with the loading of stored params
        if (getMainCanvas().isLoadingSession()) return;
            
        CustomParamData pData = getCustomData();

        if (pData == null) {
            pData = new CustomParamData();
        }

        for (int i = 0; i < datas.size(); i++)
        {
            Data data = datas.get(i);
            //if (data.category != UserDataModel.Category.DEPENDENT_SUBSET)
            pData.put("UserDataTuple:" + i, data.model);
        }

        setCustomData(pData);
    }

    @Override
    protected void evaluationRequest(Script script) {

        super.evaluationRequest(script);

        // do this only once
        if (evalReqPerformed) return;
        
        String type = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("type")) {
                Object property = script.getProperty("type");
                if (property != null) {
                    type = (String) property;
                }
            }
        }
        
        if (type == null || type.isEmpty()) {
            throw new RuntimeException("UserDataTupleType: no or empty 'type' info in options.");
        }

        String[] twoParts = type.split(":");
        if (twoParts.length != 2) {
            throw new RuntimeException("UserDataTupleType: wrong format in 'type'.");
        }
        twoParts[0] = twoParts[0].trim();
        String[] nameArray = twoParts[1].split(",");

        // parse category and name
        datas.clear();
        int nameCnt = 0;
        int i = -1;
        while (i+1 < twoParts[0].length())
        {
            i++;
            Data newData = new Data();

            switch (twoParts[0].charAt(i)) {
                case 'n':
                    newData.category = UserDataModel.Category.NUMBER;
                    break;
                case 'v':
                    newData.category = UserDataModel.Category.VECTOR;
                    break;
                case 'm':
                    newData.category = UserDataModel.Category.MATRIX;
                    break;
                case 'c':
                    newData.category = UserDataModel.Category.COND_NUMBER;
                    break;
                case 's':
                    newData.category = UserDataModel.Category.SUBSET;
                    break;
                case 'S':
                    i++;
                    int nFct = -2;
                    if (i < twoParts[0].length()) nFct = Character.digit(twoParts[0].charAt(i),10);
                    if (nFct < 1) // -1 if conversion failed, -2 if no char to convert
                    {
                        throw new RuntimeException("A decimal digit > 0 representing the number of functions involved "
                                                    + "is required after definition of a UserDependentSubset type ('S').\n");
                    }
                    
                    // construct name data
                    newData.name = nameArray[nameCnt++].trim();
                    for (int j=1; j<nFct; j++)
                    {
                        if (nameCnt < nameArray.length)
                            newData.name = newData.name + "," + nameArray[nameCnt++].trim();     
                    }
                    newData.suppInfo = nFct;
                    newData.category = UserDataModel.Category.DEPENDENT_SUBSET;
                    break;
                case 'l':
                    newData.category = UserDataModel.Category.LINKER;
                    break;
                case '|':
                    if (datas.size() > 0) {
                        datas.get(datas.size() - 1).separateBehind = true;
                    }
                    continue;
                default:
                    throw new RuntimeException("UserDataTupleType: invalid type identifier");
            }

            if (nameCnt < nameArray.length && twoParts[0].charAt(i-1) != 'S') {
                newData.name = nameArray[nameCnt++].trim();
            }
            datas.add(newData);
        }

        if (nameCnt != nameArray.length)
        {
            throw new RuntimeException("UserDataTupleType: number of categories does "
                    + "not match number of names.");
        }

        // read the ugx_tag
        if (getValueOptions() != null) {

            if (getValueOptions().contains("ugx_tag")) {
                Object property = script.getProperty("ugx_tag");
                if (property != null) {
                    ugx_tag = (String) property;
                }
            }
        }

        // read the ugx_tag
        if (getValueOptions() != null) {

            if (getValueOptions().contains("ugx_globalTag")) {
                Object property = script.getProperty("ugx_globalTag");
                if (property != null) {
                    ugx_globalTag = (String) property;
                }
            }
        }
        
        // read the fct_tag
        if (getValueOptions() != null && getValueOptions().contains("fct_tag"))
        {
            Object property = script.getProperty("fct_tag");
            if (property != null) fct_tag = (String) property;
        }

        // prohibit ugx_tag and ugx_globalTag together
        if (ugx_tag != null && ugx_globalTag != null) {
            throw new RuntimeException("UserDataTupleType: simultaneous usage of"
                    + " 'ugx_tag' and 'ugx_globalTag' not allowed.");
        }
        
        evalReqPerformed = true;
    }

    @Override
    public void addedToMethodRepresentation() {
        super.addedToMethodRepresentation();
        
        // init the views 
        init();
        
        // register at the observable for ugx-file-loads if ugx_tag given
        if (ugx_tag != null) {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            LoadUGXFileObservable.getInstance().addObserver(this, ugx_tag, o, windowID);
        }

        if (ugx_globalTag != null) {
            LoadUGXFileObservable.getInstance().addObserver(this, ugx_globalTag);
        }
        
        // if a DEPENDENT_SUBSET object is present, add it as FunctionSubsetCoordinatorObserver
        for (Data theData : datas)
        {
            if (theData.category == UserDataModel.Category.DEPENDENT_SUBSET)
            {   
                ((UserDependentSubsetView)theData.view).addAsObserver();
                break;
            }
        }
        
        if (!getMainCanvas().isLoadingSession()) {
            storeCustomParamData();
        }
    }
    

    @Override
    public void dispose() {
        if (ugx_tag != null || ugx_globalTag != null) {
            LoadUGXFileObservable.getInstance().deleteObserver(this);
        }
        
        // if a DEPENDENT_SUBSET object is present, remove it as FunctionSubsetCoordinatorObserver
        for (Data theData : datas)
        {
            if (theData.category == UserDataModel.Category.DEPENDENT_SUBSET)
            {   
                ((UserDependentSubsetView)theData.view).removeAsObserver();
                break;
            }
        }

        for (Data theData : datas) {
            theData.view.closeView();
        }

        super.dispose();
    }

    // inherited from LoadUGXFileObserver
    @Override
    public void update(UGXFileInfo info) {
        // adjust Data for new FileInfo in model and view
        for (Data theData : datas) {

            // bit of a hack to incorporate DEPENDENT_SUBSET into UserDataTuple;
            // it is necessary to exclude this case here, because its update
            // method does not expect a UGXFileInfo!
            if (theData.category != UserDataModel.Category.DEPENDENT_SUBSET)
            {
                theData.model.adjustData(info);
                theData.view.adjustView(info);
            }
            
            storeCustomParamData();
        }
    }

    /**
     * @return the tupel's fct_tag
     */
    public String getFctTag()
    {
        return fct_tag;
    }

    
    
    
    
    @Override
    public String getValueAsCode() {
        StringBuilder sb = new StringBuilder();

        sb.append("new ").append(UserDataTuple.class.getName()).append("()");

        for (int i = 0; i < getDatas().size(); i++) {
            sb.append(".add(").append(getDatas().get(i).model.getModelAsCode()).append(")");
        }

        return sb.toString();
        //return VLangUtils.addEscapesToCode(sb.toString());
//        return "null as " + getType().getName();
    }
}
