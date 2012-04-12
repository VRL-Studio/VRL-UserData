/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;

    public UserDataType() {
        
        setType(String.class);
        
        setName("");

        setStyleName("mathml");
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                String visualElem = (String) getValue();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    // left mouse click

                    if (visualElem != null) {

                        UserDataWindow w = new UserDataWindow("test", getMainCanvas());
                        
                        //add InputWindow to canvas
                        getMainCanvas().addWindow(w);
                    }
                } else if ( visualElem!=null && e.getButton() == MouseEvent.BUTTON3) {
                    //right mouse click
                    
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
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
    });

                }
    
}
