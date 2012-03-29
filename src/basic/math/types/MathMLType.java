/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.helpers.MathMLShortCuts;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.system.VRL;
import eu.mihosoft.vrl.visual.VSwingUtil;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import net.sourceforge.jeuclid.swing.JMathComponent;

/**
 * This typrepresentation allows the rendering of the mathML names of the
 * different visual elements.
 *
 * @author night
 */
public class MathMLType extends TypeRepresentationBase {

    protected JMathComponent jMathComponent;
//    private static final String emptyString = "<math><mtext>empty</mtext></math>";
    private static final String emptyString = "<math></math>";

    /**
     * Constructor.
     */
    public MathMLType() {

//        System.out.println("");
//        System.out.println("@@ In MathML Constructure:");
//        System.out.println("@@@ ContextClassLoader: " + Thread.currentThread().getContextClassLoader());
//        System.out.println("@@@ ClassLoader: t " + this.getClass().getClassLoader());
//        System.out.println("@@@ SystemClassLoader: t " + this.getClass().getClassLoader().getSystemClassLoader());
//
//
////        Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
//        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
//
//        System.out.println("@@@ ClassLoader after set to SystemClassLoader: "
//                + Thread.currentThread().getContextClassLoader());
//        System.out.println("");


        setType(String.class);
//        setName("Formula");
        setName("");

//        setSupportedStyle("mathml");
        setStyleName("mathml");

        addSupportedRepresentationType(RepresentationType.OUTPUT);
        addSupportedRepresentationType(RepresentationType.INPUT);

        jMathComponent = new JMathComponent();

        // this method call important for resizing do not change without reason!
        // otherwise jmathcomponet will at most work once!
        jMathComponent.setMinimumSize(new Dimension(0, 0));

        jMathComponent.setContent(emptyString);
//        jMathComponent.setBackground(new Color(120, 0, 0, 120));
        jMathComponent.setBackground(new Color(0, 0, 0, 0));
        jMathComponent.setFontSize(20);

        setInputComponent(jMathComponent);
        add(jMathComponent);

//        System.out.println("@@@ ClassLoader: j " + jMathComponent.getClass().getClassLoader());
//        System.out.println("@@@ SystemClassLoader: j " + jMathComponent.getClass().getClassLoader().getSystemClassLoader());

    }

    /**
     * {@inheritDoc}
     *
     * Setting before and after a mathML containg String the needed start and
     * end tags.
     * Additionaly the layout gets an update call to make the jMathComponent work.
     */
    @Override
    public void setViewValue(final Object o) {
        VSwingUtil.invokeLater(new Runnable() {

            @Override
            public void run() {

//                System.out.println("");
//                System.out.println("@@@ In MathML setViewValue: begin");
//
//                System.out.println("@@@ ContextClassLoader: " + Thread.currentThread().getContextClassLoader());
//                System.out.println("@@@ ClassLoader: j " + jMathComponent.getClass().getClassLoader());
//                System.out.println("@@@ SystemClassLoader: j " + jMathComponent.getClass().getClassLoader().getSystemClassLoader());
//                System.out.println("@@@ ClassLoader: t " + this.getClass().getClassLoader());
//                System.out.println("@@@ SystemClassLoader: t " + this.getClass().getClassLoader().getSystemClassLoader());
//
//
//                Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
//
//                System.out.println("@@@ ClassLoader after set to SystemClassLoader: "
//                        + Thread.currentThread().getContextClassLoader());
//                System.out.println("");


                if (o instanceof String) {
                    String mathMLcontent = (String) o;

                    if (mathMLcontent.length() > 0) {

                        jMathComponent.setContent(
                                MathMLShortCuts.XMLInfoTag
                                + MathMLShortCuts.mathBegin
                                + //  MathMLShortCuts.textBegin+
                                mathMLcontent
                                + //  MathMLShortCuts.textEnd+
                                MathMLShortCuts.mathEnd);

//                        System.out.println("@@@" + this.getClass().getName()
//                                + " setViewValue() \n" + mathMLcontent);

                        // this layout update block is highly important and should
                        // not be changed without reason!
                        // otherwise jmathcomponet will at most work once!
                        jMathComponent.setMinimumSize(null);
                        jMathComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
//                        jMathComponent.setPreferredSize(null);
                        jMathComponent.setPreferredSize(new Dimension(
                                jMathComponent.getWidth(),
                                jMathComponent.getHeight()));

                        jMathComponent.revalidate();
                        MathMLType.this.updateLayout();
                        jMathComponent.revalidate();
                    }
                }
                
//                System.out.println("");
//                System.out.println("@@@ In MathML setViewValue: end");
//
//                System.out.println("@@@ ContextClassLoader: " + Thread.currentThread().getContextClassLoader());
//                System.out.println("@@@ ClassLoader: j " + jMathComponent.getClass().getClassLoader());
//                System.out.println("@@@ SystemClassLoader: j " + jMathComponent.getClass().getClassLoader().getSystemClassLoader());
//                System.out.println("@@@ ClassLoader: t " + this.getClass().getClassLoader());
//                System.out.println("@@@ SystemClassLoader: t " + this.getClass().getClassLoader().getSystemClassLoader());
//
//
//                
//                System.out.println("");
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void emptyView() {
        jMathComponent.setContent(emptyString);

        // this layout update block is highly important and should
        // not be changed without reason!
        // otherwise jmathcomponet will at most work once!
        jMathComponent.setMinimumSize(new Dimension(0, 0));
        jMathComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        jMathComponent.setPreferredSize(null);
        jMathComponent.revalidate();
        this.updateLayout();
    }
}
