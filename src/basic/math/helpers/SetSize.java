/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.IgnoreNotSerializableWarnings;
import eu.mihosoft.vrl.reflection.IgnoreObjectTree;
import eu.mihosoft.vrl.reflection.VisualCanvas;


/**
 * This class contains the functionality change the size/dimension of a <code>FormelEntry</code>
 *
 * @author night
 */
public class SetSize implements IgnoreNotSerializableWarnings,
        SetSizeInterface, IgnoreObjectTree {

    private static final long serialVersionUID = 1;
    private FormelEntry entry;
    transient VisualCanvas mainCanvas;

    

    public SetSize(FormelEntry entry) {
        setFormelEntry(entry);
    }

    /**
     * Set the number of rows and columns and set the data of the <code>FormelEntry</code>
     * to null.
     *
     * @param r number of rows
     * @param c number of cols
     */
    public void setSize(
            @ParamInfo(options = "hideConnector=true", name = "number of rows:") Integer r,
            @ParamInfo(options = "hideConnector=true", name = "number of cols:") Integer c) {
        entry.setCols(c);
        entry.setRows(r);
        entry.setData(null);
    }

    @MethodInfo(noGUI = true)
    @Override
    public void setFormelEntry(FormelEntry entry) {
        this.entry = entry;
    }

    @MethodInfo(noGUI = true)
    @Override
    public FormelEntry getFormelEntry() {
        return entry;
    }

    /**
     * Sets the main canvas object.
     * @param mainCanvas the main canvas object
     */
    @MethodInfo(noGUI=true, callOptions="assign-to-canvas")
    public void setMainCanvas(VisualCanvas canvas) {
        this.mainCanvas = canvas;

        //entfernt nach umbau in der VRL
//        mainCanvas.getCanvasWindow(this).setTitle(entry.getShortName());
    }
};



/*

    "import gleichung.*;\n" +
    "@ObjectInfo(name=\"Set size for " + entry.getShortName() + "\")\n" +
    "public class SetSize implements IgnoreNot" +
    "" +
    "SerializableWarnings, " +
    "SetSizeInterface, IgnoreObjectTree{\n" +
    "   private static final long serialVersionUID = 1;\n" +
    "   private FormelEntry entry;\n" +
    "   public void setSize(\n" +
    "@ParamInfo(options=\"hideConnector=true\", name=\"number of rows:\")Integer r,\n" +
    "@ParamInfo(options=\"hideConnector=true\", name=\"number of cols:\") Integer c){\n" +
    "       entry.setCols(c);entry.setRows(r);\n" +
    "   }\n" +
    "   @MethodInfo(noGUI=true)\n" +
    "   public void setFormelEntry(FormelEntry entry) {\n" +
    "       this.entry = entry;\n" +
    "   }\n" +
    "   @MethodInfo(noGUI=true)\n" +
    "   public FormelEntry getFormelEntry() {\n" +
    "       return entry;\n" +
    "   }\n" +
    "}\n";

     */
