/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import basic.math.helpers.Formel;
import basic.math.helpers.FormelEntry;
import eu.mihosoft.vrl.reflection.RepresentationType;
import java.io.Serializable;

/**
 * Class where the STATIC visual repesentation of the used equation is generated.
 * This is done in MathML standart with the JEuclid library.
 *
 * The whole equation is a <code>Formel</code> and consists of
 * many <code>FormelEntry</code>´s.
 *
 * @author Night
 */
public class StaticFormelInputType extends FormelInputType implements Serializable{
    private static final long serialVersionUID = 1L;

    private static String xmlTag = "<?xml version=\"1.0\"?> " +
            "<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 2.0//EN\" " +
            " \"http://www.w3.org/TR/MathML2/dtd/mathml2.dtd\">";

    /**
     * Generates the necessary Formel and FormelEntries with all settings.
     * Calls "super.setViewValue( formel )" with the generated formel.
     */
    public StaticFormelInputType() {
        setType(Formel.class);
        setValueName("Formel");
        
//        setSupportedStyle("static");
        setStyleName("static");
        
        addSupportedRepresentationType(RepresentationType.INPUT);

        Formel result = new Formel();

        FormelEntry a0 = new FormelEntry();
        a0.setFormel(xmlTag + "<math><mfrac> <mi>d</mi> <mi>dt</mi> </mfrac>" +
                " <mo>(</mo></math> ");
        result.add(a0);

        FormelEntry e1 = new FormelEntry();
        e1.setFormel(xmlTag + "<math><mi>&beta;</mi></math>");
        e1.setRows(1);
        e1.setCols(1);
//        e1.setShortName("beta"); //im ug-script steht sonst auch "beta00"
        e1.setShortName("b");
        e1.setEditable(true);
        result.add(e1);

        FormelEntry a1 = new FormelEntry();
        a1.setFormel(xmlTag + "<math><mo>*</mo> <mi>u</mi> <mo>)</mo></math> ");
//        a1.setShortName("*u");
        result.add(a1);

        FormelEntry a2 = new FormelEntry();
        a2.setFormel(xmlTag + "<math><mo>-</mo> <mi>div( </mi></math>");
//        a2.setShortName("-div(");
        result.add(a2);

        FormelEntry e2 = new FormelEntry();
        e2.setRows(2);
        e2.setCols(2);
        e2.setFormel(xmlTag + "<math><mi>A </mi></math>");
        e2.setShortName("a");
        e2.setEditable(true);
        result.add(e2);

        FormelEntry a3 = new FormelEntry();
        a3.setFormel(xmlTag + "<math><mo>*</mo><mi>&nabla;</mi><mi>u</mi> <mo>+</mo></math> ");
//        a3.setShortName("*(N)u+");
        result.add(a3);

        FormelEntry e3 = new FormelEntry();
        e3.setFormel(xmlTag + "<math><mi>c</mi></math>");
        e3.setShortName("c");
        e3.setRows(2);
        e3.setCols(1);
        e3.setEditable(true);
        result.add(e3);

        FormelEntry a4 = new FormelEntry();
        a4.setFormel(xmlTag + "<math><mo>*</mo><mi>u</mi><mo>) = 0</mo></math>");
//        a4.setShortName("u)");
        result.add(a4);

        super.setViewValue(result);
    }


    /**
     * Nothing done here.
     */
    @Override
    public void emptyView() {
        //
    }
}
