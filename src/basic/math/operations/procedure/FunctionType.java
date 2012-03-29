/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.procedure;

import java.awt.Color;
import java.awt.GridLayout;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentation;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;

/**
 * TypeRepresentation for <code>basic.math.operations.procedure.Function</code>.
 * @author night
 */
public class FunctionType extends TypeRepresentationBase
        implements TypeRepresentation {

    private static final long serialVersionUID = 8581316770324180806L;
//    private JTextField input = new JTextField("");
//    private Function value;

    /**
     * Constructor.
     */
    public FunctionType() {
        super(new GridLayout());
        //BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);

        //this.setLayout(layout);

        this.setType(Function.class);

        this.addSupportedRepresentationType(RepresentationType.INPUT);
        this.addSupportedRepresentationType(RepresentationType.OUTPUT);

        nameLabel.setText("Function:");
        nameLabel.setAlignmentY(0.5f);

        this.add(nameLabel);

//        int height = (int) this.input.getMinimumSize().getHeight();
//        this.input.setPreferredSize(new Dimension(40, height));
//
//        this.add(input);
    }

    @Override
    public TypeRepresentationBase copy() {
        return new FunctionType();
    }

    @Override
    protected void valueInvalidated() {
        if (isInput()) {
             nameLabel.setForeground(Color.RED);
        }
    }

    @Override
    protected void valueValidated() {
        if (isInput()) {
            nameLabel.setForeground(Color.BLACK);
        }
    }
}

