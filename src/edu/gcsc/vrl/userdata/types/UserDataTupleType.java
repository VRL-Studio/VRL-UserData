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
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
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
    }
    // list of userdata
    protected ArrayList<Data> datas = new ArrayList<Data>();
    // tag (if triggered externally)
    protected String tag = null;
    protected String globalTag = null;

    public UserDataTupleType() {
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
            data.model = UserDataFactory.createModel(data.category);

            // set userdata to external triggering depending on presence of tag
            if (tag == null && globalTag == null) {
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
            } catch (Exception e) {
            }
        }

        return tuple;
    }

    @Override
    protected void evaluateContract() {

        for (int i = 0; i < datas.size(); i++) {
            Data data = datas.get(i);

            if ((tag != null || globalTag != null)
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

            UserDataModel tmpModel = (UserDataModel) getCustomData().get("UserDataTuple:" + i);
            if (tmpModel != null) {

                data.model.setModel(tmpModel);
                data.view.adjustView(data.model);
            } else {
//                throw new RuntimeException("UserDataTupleType:evaluateCustomParamData:"
//                        + " cannot reaf custom data correctly.");
            }
        }

    }

    public void storeCustomParamData() {

        CustomParamData pData = getCustomData();

        if (pData == null) {
            pData = new CustomParamData();
        }

        for (int i = 0; i < datas.size(); i++) {

            Data data = datas.get(i);

            pData.put("UserDataTuple:" + i, data.model);
        }

        setCustomData(pData);
    }

    @Override
    protected void evaluationRequest(Script script) {

        super.evaluationRequest(script);

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

        twoParts[0].trim();
        String[] nameArray = twoParts[1].split(",");

        // parse category and name
        datas.clear();
        int nameCnt = 0;
        for (int i = 0; i < twoParts[0].length(); ++i) {

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

            if (nameCnt < nameArray.length) {
                newData.name = nameArray[nameCnt++].trim();
            }
            datas.add(newData);
        }

        if (datas.size() != nameArray.length) {
            throw new RuntimeException("UserDataTupleType: number of categories does "
                    + "not match number of names.");
        }

        // read the tag
        if (getValueOptions() != null) {

            if (getValueOptions().contains("tag")) {
                Object property = script.getProperty("tag");
                if (property != null) {
                    tag = (String) property;
                }
            }
        }

        // read the tag
        if (getValueOptions() != null) {

            if (getValueOptions().contains("globalTag")) {
                Object property = script.getProperty("globalTag");
                if (property != null) {
                    globalTag = (String) property;
                }
            }
        }

        if (tag != null && globalTag != null) {
            throw new RuntimeException("UserDataTupleType: simultaneous usage of"
                    + " 'tag' and 'globalTag' not allowed.");
        }

        // init also the views 
        init();
    }

    @Override
    public void addedToMethodRepresentation() {
        super.addedToMethodRepresentation();

        // register at the observable for ugx-file-loads if tag given
        if (tag != null) {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            LoadUGXFileObservable.getInstance().addObserver(this, tag, o, windowID);
        }

        if (globalTag != null) {
            LoadUGXFileObservable.getInstance().addObserver(this, globalTag);
        }

        if (!getMainCanvas().isLoadingSession()) {
            storeCustomParamData();
        }
    }

    @Override
    public void dispose() {
        if (tag != null || globalTag != null) {
            LoadUGXFileObservable.getInstance().deleteObserver(this);
        }

        for (Data theData : datas) {
            theData.view.closeView();
        }

        super.dispose();
    }

    @Override
    public void update(UGXFileInfo info) {

        // adjust Data for new FileInfo in model and view
        for (Data theData : datas) {

            theData.model.adjustData(info);
            theData.view.adjustView(info);

            storeCustomParamData();
        }
    }

    @Override
    public String getValueAsCode() {

        Object obj = getValue();

        if (obj == null) {
            return "null as " + getType().getName();
        }

        if (obj instanceof UserDataTuple) {
            UserDataTuple value = (UserDataTuple) obj;

//       // to get the idea how to implement the string version
//            //WRONG
//         new UserDataTuple().add(((UserDataTuple)getValue()).getData(0));
//            
////            to get the idea whats inside a userdatatuple at pos i
//            for (int i = 0; i < ((UserDataTuple)getValue()).size(); i++) {          
//                System.out.println("getData "+i+" className = "+((UserDataTuple)getValue()).getData(i).getClass().getName() );
//            }
//            
//            to generate the correct string:
//            1) we need first the value at position i
//            2) get the specific classtype of the value at position i
//            3) cast into these specific classtype
//            4) call the sub/specialized getValueAsCode() of the value/userdata at position i
//            5) add the return value of sub-getValueAsCode() to our string
                                 
            StringBuilder sb = new StringBuilder();

            //to get the complete name with package path
            sb.append("new ").append(value.getClass().getPackage().getName()).
                    append(".").append(value.getClass().getSimpleName()).append("()");

            for (int i = 0; i < value.size(); i++) {
                //type cast is save because  we know getValue() is not null
//            sb.append(".add(((UserDataTuple)getValue()).getData(").append(i).append("))");
                
                //nearly there but we want the values and not the description to get the values in Main.groovy
                sb.append(".add((( ")
                        // the specific subtype we want to cast into  see 3)
                        .append(value.getData(i).getClass().getPackage().getName()).append(".").append(value.getData(i).getClass().getSimpleName())
                        //get the specific subtype see 2) and close the typecast
                        .append(" )getValue().getData(").append(i).append("))")
                        //call specialized getValueAsCode()
                        .append(".getValueAsCode()")
                        //close the add()
                        .append(")");
            }

            return VLangUtils.addEscapesToCode(sb.toString());

//            System.out.println("D datas.size() = " + datas.size());
//            for (int i = 0; i < datas.size(); i++) {
//                System.out.println(" " + i + " name        = " + datas.get(i).name);
//                System.out.println(" " + i + " category    = " + datas.get(i).category);
//                System.out.println(" " + i + " model.data = " + datas.get(i).model.getData());
//            }
        } else {
            System.err.println("Some derived class has NOT implemented getValueAsCode()."
                    + "FIX IT !!");
            return "null as " + getType().getName();
        }
    }
}
