/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.procedure;

import eu.mihosoft.vrl.types.ColorType;
import java.awt.Dimension;

/**
 * The the default TypeRepresentation for a Trajectory.
 *
 * @author night
 */
public class TrajectoryType extends ColorType {

    private static final long serialVersionUID = 8581316770324180806L;
    private Trajectory t;
    private String valueName;
    private Dimension dim = new Dimension(30, 30);

    public TrajectoryType() {
        setType(Trajectory.class);
        setValueName("Trajectory:");

        container.setSize(dim);
        container.setMinimumSize(dim);
        container.setMaximumSize(dim);
        container.setPreferredSize(dim);

        setPreferredSize(null);
    }

    @Override
    public void setValueName(String name) {
        valueName = name;
        super.setValueName(name);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof Trajectory) {
            t = (Trajectory) o;
            super.setViewValue(t.getColor());
            nameLabel.setText(t.getName());

            container.setSize(dim);
            container.setMinimumSize(dim);
            container.setMaximumSize(dim);
            container.setPreferredSize(dim);

            setPreferredSize(null);
        }
    }

    @Override
    public Object getViewValue() {
        return t;
    }

    @Override
    public void emptyView() {
        t = null;
        super.emptyView();
        nameLabel.setText(valueName);

        container.setSize(dim);
        container.setMinimumSize(dim);
        container.setMaximumSize(dim);
        container.setPreferredSize(dim);

        setPreferredSize(null);
    }
}
