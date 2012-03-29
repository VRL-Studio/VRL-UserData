/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.utils;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import net.sourceforge.jeuclid.swing.JMathComponent;



/**
 *
 * @author christianpoliwoda
 */
public class Main {

    protected static JMathComponent jMathComponent;
    private static final String emptyString = "<math><mtext>empty</mtext></math>";

    public static void main(String[] args) {

        JFrame f = new JFrame("JEuclid Test");
        f.setSize(400, 300);

        jMathComponent = new JMathComponent();
        // this method call important for resizing do not change without reason!
        // otherwise jmathcomponet will at most work once!
        jMathComponent.setMinimumSize(new Dimension(0, 0));

//        String result = "<math>"+"<ci>||</ci>" + "<mtext>a</mtext>" + "<ci>||</ci>" +"</math>";

        String result = emptyString;

        jMathComponent.setBackground(new Color(0, 0, 0, 0));
        jMathComponent.setFontSize(20);

        jMathComponent.setContent(result);


        f.add(jMathComponent);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        

        System.out.println("ContextClassLoader: "+ Thread.currentThread().getContextClassLoader());
        System.out.println("ClassLoader: "+ jMathComponent.getClass().getClassLoader() );
        System.out.println("SystemClassLoader: "+ jMathComponent.getClass().getClassLoader().getSystemClassLoader() );

        //  - - -   VORLAGE AUS MATHMLTYPE   - - - //

//        jMathComponent = new JMathComponent();
//
//        // this method call important for resizing do not change without reason!
//        // otherwise jmathcomponet will at most work once!
//        jMathComponent.setMinimumSize(new Dimension(0, 0));
//
//        jMathComponent.setContent(emptyString);
////        jMathComponent.setBackground(new Color(120, 0, 0, 120));
//        jMathComponent.setBackground(new Color(0, 0, 0, 0));
//        jMathComponent.setFontSize(20);
//
//        setInputComponent(jMathComponent);
//        add(jMathComponent);
    }
}


///**
// *
// * @author christianpoliwoda
// */
//public class Main {
//    public static void main(String[] args) {
//        JFrame f = new JFrame("JEuclid Test");
//        
//        f.setSize(400, 300);
//        
//        JMathComponent jMathComponent = new JMathComponent();
//        
//        String result = "<math>"+"<ci>||</ci>" + "<mtext>a</mtext>" +
//                            "<ci>||</ci>" +"</math>";
//        
//        jMathComponent.setContent(result);
//        jMathComponent.setFontSize(20);
//        
//        f.add(jMathComponent);
//        
////        f.pack();
//        
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        f.setVisible(true);
//    }
//    
//    public static String generateMathMLName(VisualVectorInterface a, VisualMatrixInterface m, String mathML, String shortName) {
//        String result = null;
//
//        if (mathML.equals("")) {
//            if (shortName.equals("")) {
//
//                if (m != null) {
//
//                    result = "<ci>||</ci>" + a.getXmlCoding() +
//                            "<ci>||</ci>" + "<apply> <selector/> <ci></ci> <cn> " +
//                            m.getXmlCoding() + "</cn></apply>";
//                } else {
//                    result = "<ci>||</ci>" + a.getXmlCoding() + "<ci>||</ci>";
//                }
//            } else {
//                result = MathMLShortCuts.textBegin + shortName +MathMLShortCuts.textEnd;;
//            }
//        } else {
//            result = mathML;
//        }
//        return result;
//    }
//}
