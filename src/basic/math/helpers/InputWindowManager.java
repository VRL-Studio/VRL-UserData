/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.types.VisualElementType;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Use this singleton class to get <code>ElementInputWindow</code>s for
 * <code>VisualElement</code>s and prevent
 * that way to have multiple instances of the same input window.
 *
 * @author Night
 */
public class InputWindowManager implements Serializable {

    private static final long serialVersionUID = 1;
    /**
     * the singleton instance of this class
     */
    private static InputWindowManager instance = null;
    /**
     * A list of all (used) InputWindows.
     */
    private HashMap<VisualElementInterface, ElementInputWindow> inputWindowMap = null;
    /**
     * A list of all TypeReferences which are hold for the key element.
     */
    private HashMap<VisualElementInterface, ArrayList<VisualElementType>> elementTypeMap = null;
    /**
     * A list of all VisualElements which uses the same data element.
     */
    private HashMap<ElementInterface, ArrayList<VisualElementInterface>> elementDataMap = null;

    /**
     * initializes the inner variables
     */
    private InputWindowManager() {

        inputWindowMap = new HashMap<VisualElementInterface, ElementInputWindow>();

        elementTypeMap = new HashMap<VisualElementInterface, ArrayList<VisualElementType>>();

        elementDataMap = new HashMap<ElementInterface, ArrayList<VisualElementInterface>>();
    }

    /**
     * Call this method if you need an instance of InputWindowManager.
     *
     * @return the singleton instance
     */
    public static InputWindowManager getInputWindowManager() {

        if (instance == null) {
            instance = new InputWindowManager();
        }

        return instance;
    }

    /**
     * Checks if there is an InputWindow allready avaiable for the clicked 
     * visualElement (if not, creates on) and puts it near the clicked position.
     *
     * @param visualElement the visual element which InputWindow we want to get
     *          shown
     * @param mainCanvas the canvas where the window should be added
     *
     * @return the InputWindow which shows the values of the VisualElement that
     *          is clicked
     */
    public ElementInputWindow getInputWindow(VisualElementInterface visualElement,
            VisualCanvas mainCanvas, MouseEvent event) {

        System.out.println("InputWindowManager.getInputWindow()");
        
        ElementInputWindow inputWindow = inputWindowMap.get(visualElement);

        if (inputWindow == null) {
            inputWindow = new ElementInputWindow("Input " + visualElement.getShortName(), mainCanvas, visualElement);
            inputWindowMap.put(visualElement, inputWindow);
        }

        Point mainPoint = mainCanvas.getLocationOnScreen();
        Point eventPoint = event.getLocationOnScreen();

//        System.err.println("mainPoint: " + mainPoint);
//        System.err.println("eventPoint: " + eventPoint);

        //set the window near the point where the user clicked
        inputWindow.setLocation(eventPoint.x - mainPoint.x, eventPoint.y - mainPoint.y);


        /* store the information which visual element get access on which data
         * element. Need for changes/updates of inputWindows which show the same
         * entries of a data element.
         */
        ElementInterface dataElement = visualElement.getElement();
        addVisualElementToElementDataMap(dataElement, visualElement);

        return inputWindow;
    }

    /**
     * Removes and closes the InputWindow of an Element if no references of the
     * visual element is used anymore and removes the Element of the list of all
     * observed elements.
     *
     * @param element which InputWindow we want to be removed
     */
    public void removeInputWindow(VisualElementInterface element) {
        System.out.println("InputWindowManager.removeInputWindow()");

        if (element != null) {

            if (elementTypeMap.get(element) != null) {

                if (elementTypeMap.get(element).isEmpty()) {

                    ElementInputWindow inputWindow = inputWindowMap.get(element);

                    if (inputWindow != null) {

                        inputWindow.close();
                        inputWindowMap.remove(element);
                    }

                }//isEmpty()
            }

        }// element != null
    }

    /**
     * Adds a reference to an ArrayList of visual elements which uses the same
     * data element.
     *
     * @param dataElement  the key for the list
     * @param visualElement the value for the list
     */
    public void addVisualElementToElementDataMap(ElementInterface dataElement, VisualElementInterface visualElement) {

        System.out.println("InputWindowManager.addVisualElementToElementDataMap()");
        
        if (dataElement != null) {

            ArrayList list = elementDataMap.get(dataElement);

            if (list == null) {
                elementDataMap.put(dataElement, new ArrayList<VisualElementInterface>());
                list = elementDataMap.get(dataElement);
            }

            if (!list.contains(visualElement)) {
                list.add(visualElement);
            }

        }

    }

    /**
     * Removes a reference from an ArrayList of visual elements which uses the
     * same data element.
     *
     * @param dataElement  the key for the list
     * @param visualElement the value for the list
     */
    public void removeVisualElementFromElementDataMap(ElementInterface dataElement, VisualElementInterface visualElement) {

        if (dataElement != null) {
            ArrayList list = elementDataMap.get(dataElement);

            if (list != null) {

                list.remove(visualElement);
            }
        }
    }

    /**
     * Adds a reference to an ArrayList of references,
     * to know if a shown InputWindow is needed.
     *
     * @param element the key for the list
     * @param type the value for the list
     */
    public void addElementTypeReference(VisualElementInterface element, VisualElementType type) {

        if (element != null) {

            ArrayList list = elementTypeMap.get(element);

            if (list == null) {
                elementTypeMap.put(element, new ArrayList<VisualElementType>());
                list = elementTypeMap.get(element);
            }

            if (!list.contains(type)) {
                list.add(type);
            }
        }
    }

    /**
     * Removes a reference from an ArrayList of references,
     * to know if a shown InputWindow is needed.
     *
     * @param element the key for the list
     * @param type the value for the list
     */
    public void removeElementTypeReference(VisualElementInterface element, VisualElementType type) {

        if (element != null) {
            ArrayList list = elementTypeMap.get(element);

            if (list != null) {

                list.remove(type);
            }
        }
    }

    /**
     * Updates all other InputWindows of VisualElements which have the same data element
     * like the param element.
     * 
     * @param element the data storing element
     * @param visualElement the visual representation element
     */
    void updateOtherInputWindowsWithSameDataElement(ElementInterface element, VisualElementInterface visualElement) {

     System.out.println("InputWindowManager.updateOtherInputWindowsWithSameDataElement()");
        
        for (VisualElementInterface visElem : elementDataMap.get(element)) {

            if (!visElem.equals(visualElement)) {

                ElementInputWindow inputWindow = inputWindowMap.get(visElem);
                inputWindow.getElementPane().setElementDataIntoTable(visElem);
            }
        }
    }

    /**
     * 
     * @param visualElement the element which type representation is needed
     * @return the first element typ which was connected with this visual element, if existing else null
     */
    VisualElementType getTypRepresentation(VisualElementInterface visualElement) {

        ArrayList<VisualElementType> list = elementTypeMap.get(visualElement);

        if(list != null){

            try{
                return list.get(0);
            }catch(Exception e){
                //System.err.println(e.toString());
            }
            
        }else{
            System.err.println(InputWindowManager.class.getSimpleName()+
                    "elementTypeMap.get(visualElement) == null");
        }
        
        return null;
    }
}
