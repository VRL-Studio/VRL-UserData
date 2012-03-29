/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**<p>
 * ToolButtonGroup (TBG) allows buttons inside the same groupe to interact with each
 * other e.g. to decide which one of the group is selected.
 * </p><p>
 * If a new button is added to the group he is added as an ActionListner to buttons
 * which already are in the group and the other way around.
 * <p></p>
 * TBG is also a ActionListener on all members of the group.
 * If a member fires an ActionEvent because of he is selected,
 * TBG sets the mode of the group to mode of the selected ToolButton member.
 * The mode represents selected member and e.g. the functionallity that is now used in PickPlotter.
 * <p>
 * @author night
 */
public class ToolButtonGroup extends ToolButtonList implements ActionListener {

    private HashMap<ToolButton, String> btnModes = new HashMap<ToolButton, String>();
    private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
    private String mode = "none";
    private ToolButton selectedButton;

    /**
     * Adds the button to the group and adds him as an ActionListner to the buttons
     * which already are in the group and the other way around.
     *
     * @param e the button that is added
     * @param mode the mode which should be connected with button e
     * @return true if addind was successful else false
     */
    public boolean add(ToolButton e, String mode) {

        for (ToolButton b : this) {
            e.addActionListener(b);
            b.addActionListener(e);
        }

        btnModes.put(e, mode);

        e.addActionListener(this);

        return super.add(e);

    }

    /**
     * Not recomanded to use.
     * Use instead add(ToolButton e, String mode).
     *
     * @param e ToolButton which is added.
     * @return true if sucessful added.
     */
    @Override
    public boolean add(ToolButton e) {
        return add(e, "none");
    }

    /**
     * Removes the button from the group and removes him as an ActionListner from
     * the buttons which already are in the group and the other way around.
     *
     * @param e the button that is removed
     * @return true if removing was successful else false
     */
    public boolean remove(ToolButton e) {

        for (ToolButton b : this) {
            b.removeActionListener(e);
            e.removeActionListener(b);
        }

        e.removeActionListener(this);

        btnModes.remove(e);

        return super.remove(e);
    }

    /**
     * If any of the observed ToolButtons changes its state and is selected
     * the group mode will be set to the mode that is associated with the ToolButton.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof ToolButton) {
            ToolButton toolButton = (ToolButton) e.getSource();

            if (e.getActionCommand().equals("changed") && toolButton.isSelected()) {
                setSelectedButton(toolButton);
                setMode(btnModes.get(toolButton));
//                System.out.println("mode:" + getMode());
            }
        }
    }

    /**
     * @return the current used mode in the button group
     */
    public String getMode() {
        return mode;
    }

    /**
     * Defines the mode of this group. If the mode value changes a "mode-changed"
     * action event will be fired. Additionally the ToolButton that is associated
     * with the specified mode will be selected, i.e., its <code>setSelected()</code>
     * method will be called.
     *
     * @param mode the mode to set
     */
    public void setMode(String mode) {

        if (!mode.equals(this.mode)) {
            this.mode = mode;

            for (Entry<ToolButton,String> e : btnModes.entrySet()) {

               if (e.getValue().equals(mode)) {
                   e.getKey().setSelected(true);
               }
            }
            
            fireAction(new ActionEvent(this, 0, "mode-changed"));
        }
    }

    /**
     * Notify all added ActionListeners that an action has been performed.
     *
     * @param event
     */
    private void fireAction(ActionEvent event) {
        for (ActionListener listener : getActionListeners()) {
            listener.actionPerformed(event);
        }
    }

    /**
     * Addes the ActionListener to vector with opserving ActionListeners.
     *
     * @param l the ActionListener that should be added
     * @return true if adding was successful otherwise false
     */
    public void addActionListener(ActionListener l) {
        getActionListeners().add(l);
    }

    /**
     * Removes the ActionListener from vector with opserving ActionListeners.
     *
     * @param l the ActionListener that should be removed
     * @return true if removing was successful otherwise false
     */
    public boolean removeActionListener(ActionListener l) {
        return getActionListeners().remove(l);
    }

    /**
     * @return the actionListeners
     */
    public ArrayList<ActionListener> getActionListeners() {
        return actionListeners;
    }

    /**
     * @return the selectedButton
     */
    public ToolButton getSelectedButton() {
        return selectedButton;
    }

    /**
     * @param selectedButton the selectedButton to set
     */
    public void setSelectedButton(ToolButton selectedButton) {
        this.selectedButton = selectedButton;
    }
}
