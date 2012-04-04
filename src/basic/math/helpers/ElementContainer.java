/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import basic.math.elements.interfaces.VisualElementInterface;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.WindowRequest;
import eu.mihosoft.vrl.visual.CanvasWindow;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * The class ElementContainer serves for saving and loading a VisualElement
 * into / from a .xml file.
 * It can be additionally be used to have another source for a VisualElemnt,
 * e.g. the user has many logical groups of elements on the canvas and want
 * the same element on multiple groups.
 *
 * @author night
 */
@ObjectInfo(name="ElementContainer")

@ComponentInfo(name = "ElementContainer", category = "BasicMath")
public class ElementContainer implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient VisualCanvas mainCanvas;

    
    
    @MethodInfo(noGUI = true, callOptions = "assign-canvas")
    public void setMainCanvas(VisualCanvas mainCanvas) {
        this.mainCanvas = mainCanvas;
    }
    
    private VisualElementInterface visualElement;

    /**
     * Stores the visual element in an inner variable.
     * @param windowRequest 
     * @param visualElement the element to set
     */
    public void setVisualElement(WindowRequest windowRequest,
            VisualElementInterface visualElement) {
        this.visualElement = visualElement;

        CanvasWindow w = windowRequest.getWindow();
//                mainCanvas.getCanvasWindow(this, visualID);
        w.setTitle("ElementContainer: " + visualElement.getShortName());
    }

    /**
     * @return the stored visualElement
     */
    public VisualElementInterface getVisualElement() {

        return visualElement;
    }

    /**
     * Loads a VisualElement from a file,
     * which is genereted with java XMLEncoder.
     *
     * @param f the file to load from
     * @throws FileNotFoundException
     */
    public void loadVisualElement(
            @ParamInfo(name = "ElementFile", style = "load-dialog") File f)
            throws FileNotFoundException {

        XMLDecoder d = new XMLDecoder(new FileInputStream(f));
        visualElement = (VisualElementInterface) d.readObject();
        d.close();
    }

    /**
     * Stores a VisualElement in a file.
     * The genereted file will be created with java XMLEncoder.
     *
     * @param f the file to save in
     * @throws FileNotFoundException
     */
    public void saveVisualElement(
            @ParamInfo(name = "ElementFile", style = "save-dialog")  File f)
            throws FileNotFoundException {
        
        XMLEncoder e = new XMLEncoder(new FileOutputStream(f));
        e.writeObject(visualElement);
        e.close();
    }
}
