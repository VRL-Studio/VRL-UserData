/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.elements.Element;
import basic.math.helpers.ElementInputWindow;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.helpers.InputWindowManager;
import basic.math.helpers.MathMLShortCuts;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * This typerepresentation controls the behaviour and the layout of
 * <code>VisualElement</code>s.
 *
 * It adds the ability to click on the mathML name and sets the name after the
 * name lable.
 *
 * On mouseClicked at the mathML name an <code>ElementInputWindow</code> appears
 * containing the data of the VisualElement.
 *
 * @author night
 */
public class VisualElementType extends MathMLType implements Serializable {

    private static final long serialVersionUID = 1;
    protected String emptyString = MathMLShortCuts.mathBegin + MathMLShortCuts.mathEnd;

    public VisualElementType() {
        super();

        setType(VisualElementInterface.class);
        setValueName(getDefaultTypeName());

//        setSupportedStyle("default");
        setStyleName("default");

        nameLabel.setText(getDefaultTypeName() + ":");

        //needed to be removed and then added after nameLabel
        //to set the right order of displaying
        remove(jMathComponent);
        add(nameLabel);
        add(jMathComponent);

        final JPopupMenu popup = new JPopupMenu();
        JMenuItem item = new JMenuItem("add Element to canvas");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((VisualCanvas)getMainCanvas()).addObject(getValue());
            }
        });

        popup.add(item);

        jMathComponent.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                VisualElementInterface visualElem = (VisualElementInterface) getValue();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    // left mouse click

                    if (visualElem != null) {

                        // elementInputWindow which contains the Table for adding values manually
                        ElementInputWindow w =
                                InputWindowManager.getInputWindowManager().
                                getInputWindow(visualElem, (VisualCanvas) getMainCanvas(), e);

                        //add InputWindow to canvas
                        getMainCanvas().addWindow(w);
                        
                    }
                } else if ( visualElem!=null && e.getButton() == MouseEvent.BUTTON3) {
                    //right mouse click

                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        //this allows to close an InputWindow if the depending typeRepresentation/element is closed
        emptyView();
        super.dispose();
    }

    /**
     * {@inheritDoc}
     *
     * Removes additional the visualElement and a instance of this typerepresentation
     * from map where the combinations of of type and element are stored.
     * It will be also the ElementInputWindow from a corresponding list removed.
     */
    @Override
    public void emptyView() {

        if (getViewValueWithoutValidation() != null) {

            VisualElementInterface visualElement = (VisualElementInterface) getViewValueWithoutValidation();

            if (visualElement != null) {
                InputWindowManager.getInputWindowManager().removeElementTypeReference(visualElement, this);
                InputWindowManager.getInputWindowManager().removeInputWindow(visualElement);
            }
        }
        super.emptyView();
    }

    /**
     * {@inheritDoc}
     *
     * Adds additional the visualElement and a instance of this typerepresentation
     * to map where the combinations of of type and element are stored.
     */
    @Override
    public void setViewValue(Object o) {

        if (o instanceof VisualElementInterface) {
            VisualElementInterface visualElem = (VisualElementInterface) o;

            InputWindowManager.getInputWindowManager().addElementTypeReference(visualElem, this);

            super.setViewValue(visualElem.getXmlCoding());
        }
    }

    /**
     * This method returns the default type name which is shown after a Connector.
     * 
     * @return the best spezialized default name possible
     */
    public String getDefaultTypeName() {
        return Element.getDEFAULT_NAME();
    }

    /**
     * This method gets for the transmitted object the spezialized default name,
     * which is available.
     *
     * @param o the object which is forwarded
     * @return the default name of the object
     */
    public String getTypeName(Object o) {
        String result = getDefaultTypeName();

        if (o != null) {
//            result = ((Tensor) o).getDefaultName();
            result = ((VisualElementInterface) o).getDefaultName();
        }

        return result;
    }

    /**
     * Overloaded because of not allowing to save the return values of methods.
     * Not allowing to save the values prevents loading bugs from a session.
     *
     * @return
     */
    @Override
    public Object getViewValue() {

        Object result = null;

        VisualCanvas canvas = (VisualCanvas) getMainCanvas();

//        if(!((VisualCanvas)getMainCanvas()).isSavingSession()){
        if (canvas != null) {
            if (!canvas.isSavingSession()) {

                result = super.getViewValue();
            }
        }


        return result;
    }

    
//    public boolean compatible(TypeRepresentationBase tRep) {
    public boolean isCompatible(TypeRepresentationBase tRep) {
        return this.getType().isAssignableFrom(tRep.getType());
    }
}
