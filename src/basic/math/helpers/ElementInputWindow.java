/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.types.VisualElementType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.TypeRepresentationContainer;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.CanvasActionListener;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.Connection;
import eu.mihosoft.vrl.visual.Connections;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VContainer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;

/**<p>
 * ElementInputWindow is the Window which contains a <code>ElementInputPane</code>,
 * JComboBox for the navigation through the 3rd dimension of an Element and 
 * a VButton for ending editing.
 *
 * In the displayed table it is possible to change the shown values of the
 * displayed data element.
 *
 * Changes on the values will be take over if the "ok" button was pressed
 * (window will be not closed) or the exit button will be pressed (window closes).
 * </p>
 * <p>
 * The shown table belongs to ElementInputPane.
 * </p>
 * @author Night
 */
public class ElementInputWindow extends CanvasWindow implements Serializable {

    private static final long serialVersionUID = 1;
    private ElementInputPane elementPane;
    private JComboBox deepSelection;
    private int counter = 0;

    /**
     * Combines an ElementInputPane (contains the table) and a button and a
     * comboBox (containg the information about the 3rd dim of an element) to an
     * InputWindow were the data of an Element can be changed.
     *
     * @param title the title of this ElementInputWindow
     * @param mainCanvas the canvas were this ElementInputWindow should be added to
     * @param visualElement the visual element which data should be shown in the
     *          table of this ElementInputWindow
     */
    public ElementInputWindow(String title, Canvas mainCanvas, final VisualElementInterface visualElement) {
        super(title, mainCanvas);

        Integer list[];

        if (visualElement.getElement().getDimensions().length > 2) {
            list = new Integer[visualElement.getElement().getDimensions()[2]];
        } else {
            list = new Integer[]{1};
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = i;

        }

        deepSelection = new JComboBox(list);
        add(deepSelection);

        //"remove" the selection drop down list for matrixes and vectors
        if (visualElement.getElement() instanceof MatrixInterface) {
            deepSelection.setVisible(false);
        }

        elementPane = new ElementInputPane(this, visualElement);
        elementPane.setFocusable(true);

        //add elementpane and show always the scrollbars
        JScrollPane elementPaneScrollPane = new JScrollPane(elementPane);//,
//                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //nessecarry for visuall representation
        elementPaneScrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        elementPaneScrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        elementPaneScrollPane.getViewport().setOpaque(true);
        elementPaneScrollPane.setBackground(new Color(0, 0, 0, 0));
        elementPaneScrollPane.setOpaque(true);



        Dimension dim = new Dimension(400, 300);
        elementPaneScrollPane.setMaximumSize(dim);

        elementPaneScrollPane.getHorizontalScrollBar().setVisible(true);
        elementPaneScrollPane.getVerticalScrollBar().setVisible(true);

        int w = Math.min((int) dim.getWidth(), elementPane.getPreferredSize().width +
                elementPaneScrollPane.getVerticalScrollBarPolicy() / 2);

        int h = Math.min((int) dim.getHeight(), elementPane.getPreferredSize().height +
                elementPaneScrollPane.getHorizontalScrollBarPolicy() / 2);

        Dimension preferredSize = new Dimension(w, h);

        elementPaneScrollPane.setPreferredSize(preferredSize);

        add(elementPaneScrollPane);

        // needed to be informed about the current selected deepView
        deepSelection.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //need to add current values without pushing the ok button
                if (getElementPane().getTable().isEditing()) {
                    getElementPane().getTable().getCellEditor().stopCellEditing();
                }

                //decide which deep should be shown and writte the data in the table
                getElementPane().setElementCurrentDeep(getDeepSelection().getSelectedIndex());
                getElementPane().setElementDataIntoTable(visualElement);

            }
        });

        elementPane.setAlignmentX(0.5f);

        //show the data of the currentDeepSelection (=0) in the table
        elementPane.setElementDataIntoTable(visualElement);


        final VButton button = new VButton("Ok");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                VButton o = (VButton) e.getSource();
                o.requestFocus();

                //stores the local changes values
                if (getElementPane().getTable().isEditing()) {
                    getElementPane().getTable().getCellEditor().stopCellEditing();

                    /*
                     * SOME PROBLEM WITH INPUTWINDOWS, e.g. they closing after changing values
                     */
                    //if the value of an visualElement were changed in a InputWindow
                    //VRL need to know that this is done and set it outdated
                    VisualElementType elemType = InputWindowManager.getInputWindowManager().
                            getTypRepresentation(elementPane.getVisualElement());

                    if (elemType != null) {
                        if(elemType.isInput()){
                        elemType.setReturnTypeOutdated();

//                        //after setting outdated set the new value
//                        elemType.setValue(elementPane.getVisualElement());
                        }
                        else{
//                            ArrayList<Connection> connectionList = 
//                            getMainCanvas().getConnections().
//                                    getAllWith(elemType.getConnector());
                            
                            Connections connectionListAll = (Connections)
                                    getMainCanvas().getAllConnections();
                            
                             ArrayList<Connection> connectionList = connectionListAll.getAllWith(elemType.getConnector());
                            
                            for (Connection connection : connectionList) {
                                TypeRepresentationBase typ = 
                                        //connection.getReceiver().getValueObject().getTypeRepresentation();
                                        ((TypeRepresentationContainer)connection.
                                        getReceiver().getValueObject()).getTypeRepresentation();
                                typ.setDataOutdated();
                            }
                        }

                    }
                }

//                //close this window
//                ElementInputWindow.this.close();
            }
        });

        button.setAlignmentX(0.5f);

        VContainer container = new VContainer(button);
        container.setBorder(new EmptyBorder(3, 0, 5, 0));

        add(container);

        // if a cell is in edit mode and the exit "button" is pressed the value
        // in the will be stoared into the data element
        addActionListener(new CanvasActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals(CanvasWindow.CLOSE_ACTION) && counter < 1) {

                    button.requestFocus();

                    counter++;
//                    System.out.println("COUNTER:" + counter);

                    TableCellEditor cellEditor = ElementInputWindow.this.
                            getElementPane().getTable().getCellEditor();

                    //to prevent nullPointerException check for != null
                    if (cellEditor != null) {
                        cellEditor.stopCellEditing();
                    }

                    /*
                     * SOME PROBLEM WITH INPUTWINDOWS, e.g. they closing after changing values
                     */
                    //if the value of an visualElement were changed in a InputWindow
                    //VRL need to know that this is done and set it outdated
                    VisualElementType elemType = InputWindowManager.getInputWindowManager().
                            getTypRepresentation(elementPane.getVisualElement());

                    if (elemType != null) {
                        if(elemType.isInput()){
                        elemType.setReturnTypeOutdated();

//                        //after setting outdated set the new value
//                        elemType.setValue(elementPane.getVisualElement());
                        }
//                        else{
//                            ArrayList<Connection> connectionList =
//                                    getMainCanvas().getConnections().
//                                    getAllWith(elemType.getConnector());
//
//                            for (Connection connection : connectionList) {
//                                TypeRepresentationBase typ = connection.getReceiver().
//                                        getValueObject().getTypeRepresentation();
//                                typ.setDataOutdated();
//                            }
//                        }
                    }

                }
            }
        });
    }

    /**
     * @return the elementPane
     */
    public ElementInputPane getElementPane() {
        return elementPane;
    }

    /**
     * @return the deepSelection
     */
    public JComboBox getDeepSelection() {
        return deepSelection;
    }
}
