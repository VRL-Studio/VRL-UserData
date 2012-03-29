/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

/**
 * Contains some static MathML tags as a help to reduce typing errors.
 *
 * @author night
 */
public class MathMLShortCuts {

    /**
     * Info tag which xml version is just for the mathML code
     */
    public static String XMLInfoTag = "<?xml version=\"1.0\"?> " +
            "<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 2.0//EN\" " +
            " \"http://www.w3.org/TR/MathML2/dtd/mathml2.dtd\">";
    /**
     * With this tag begans each mathML code
     */
    public static String mathBegin = "<math>";
    /**
     * With this tag ends each mathML code
     */
    public static String mathEnd = "</math>";
    /**
     * Start tag for a number.
     * Before a number can be added to a mathML code, this tag is needed.
     * //<mn>2</mn> -> 2
     */
    public static String numberBegin = "<mn>";
    /**
     * End tag for a number.
     * After a number was added to a mathML code, this tag is needed.
     * //<mn>2</mn> -> 2
     */
    public static String numberEnd = "</mn>";
    /**
     * Start tag for a letter.
     * Before a letter can be added to a mathML code, this tag is needed.
     * //<mi>x</mi> -> x
     */
    public static String letterBegin = "<mi>";
    /**
     * End tag for a letter.
     * After a letter was added to a mathML code, this tag is needed.
     * //<mi>x</mi> -> x
     */
    public static String letterEnd = "</mi>";
    /**
     * Start tag for a text.
     * Before a text can be added to a mathML code, this tag is needed.
     * //<mtext> cdata </mtext> -> cdata
     */
    public static String textBegin = "<mtext>";
    /**
     * End tag for a text.
     * After a text was added to a mathML code, this tag is needed.
     * //<mtext> cdata </mtext> -> cdata
     */
    public static String textEnd = "</mtext>";
    /**
     * Start tag for a symbol.
     * Before a symbol can be added to a mathML code, this tag is needed.
     * //1) <mo> + </mo> -> +
     * //2) <mo> ( </mo> -> (
     */
    public static String operationBegin = "<mo>";
    /**
     * End tag for a symbol.
     * After a symbol was added to a mathML code, this tag is needed.
     * //1) <mo> + </mo> -> +
     * //2) <mo> ( </mo> -> (
     */
    public static String operationEnd = "</mo>";
    /**
     * Start tag for a fraction.
     * Before a fraction can be added to a mathML code, this tag is needed.
     * //<mfrac>  <mi>x</mi> <mi>y</mi> </mfrac> -> x/y
     */
    public static String fractureBegin = "<mfrac>";
    /**
     * End tag for a fraction.
     * After a fraction was added to a mathML code, this tag is needed.
     * //<mfrac>  <mi>x</mi> <mi>y</mi> </mfrac> -> x/y
     */
    public static String fractureEnd = "</mfrac>";
    /**
     * this tag replace the operation-, letter- -Begin and -End tags.
     * for more information look at
     * http://www.w3.org/TR/MathML2/appendixc.html#cedef.ci
     */
    public static String ciEnd = "</ci>";
    /**
     * this tag replace the operation-, letter- -Begin and -End tags.
     * for more information look at
     * http://www.w3.org/TR/MathML2/appendixc.html#cedef.ci
     */
    public static String ciStart = "<ci>";
    /**
     * Start tag for an equation.
     * Before an equation can be added to a mathML code, this tag is needed.
     * // relations <eq/> (apply-Version should be used)
     * // 1) a=b : <apply>  <eq/>  <ci> a </ci><ci> b </ci>  </apply>
     * // 2) x=1 : <reln>   <eq/>  <ci> x </ci><cn> 1 </cn>  </reln>
     */
    public static String equationBegin = "<apply><eq/>";
    /**
     * End tag for an equation.
     * After an equation was added to a mathML code, this tag is needed.
     * // relations <eq/> (apply-Version should be used)
     * // 1) a=b : <apply>  <eq/>  <ci> a </ci><ci> b </ci>  </apply>
     * // 2) x=1 : <reln>   <eq/>  <ci> x </ci><cn> 1 </cn>  </reln>
     */
    public static String equationEnd = "</apply>";
    /**
     * Start tag for a logarithmen.
     * Before a logarithmen can be added to a mathML code, this tag is needed.
     * // log_base(x) e.g. base=10 ->log_10(x)
     * // <apply> <log/> <logbase>base</logbase>  argument </apply>
     */
    public static String logBegin = "<apply> <log/>";
    /**
     * Start tag for the base of a logarithmen.
     * Before a base of a logarithmen can be added to a mathML code, this tag is needed.
     * // log_base(x) e.g. base=10 ->log_10(x)
     * // <apply> <log/> <logbase>base</logbase>  argument </apply>
     */
    public static String logBaseBegin = "<logbase>";
    /**
     * End tag for the base of a logarithmen.
     * After a base of a logarithmen was added to a mathML code, this tag is needed.
     * // log_base(x) e.g. base=10 ->log_10(x)
     * // <apply> <log/> <logbase>base</logbase>  argument </apply>
     */
    public static String logBaseEnd = "</logbase>";
    /**
     * End tag for a logarithmen.
     * After a logarithmen was added to a mathML code, this tag is needed.
     * // log_base(x) e.g. base=10 ->log_10(x)
     * // <apply> <log/> <logbase>base</logbase>  argument </apply>
     */
    public static String logEnd = "</apply>";
    /**
     * Start tag for a sinus.
     * Before a sinus can be added to a mathML code, this tag is needed.
     * //<apply> <sin/> ... </apply> -> sin( )
     */
    public static String sinusBegin = "<apply> <sin/>";
    /**
     * End tag for a sinus.
     * After a sinus was added to a mathML code, this tag is needed.
     * //<apply> <sin/> ... </apply> -> sin( )
     */
    public static String sinusEnd = "</apply>";
    /**
     * Start tag for a cosinus.
     * Before a cosinus can be added to a mathML code, this tag is needed.
     * //<apply> <cos/> ... </apply> -> cos( )
     */
    public static String cosinusBegin = "<apply> <cos/>";
    /**
     * End tag for a cosinus.
     * After a cosinus was added to a mathML code, this tag is needed.
     * //<apply> <cos/> ... </apply> -> cos( )
     */
    public static String cosinusEnd = "</apply>";
    /**
     * Start tag for a tangens.
     * Before a tangens can be added to a mathML code, this tag is needed.
     * //<apply> <tan/> ... </apply> -> tan( )
     */
    public static String tangensBegin = "<apply> <tan/>";
    /**
     * End tag for a tangens.
     * After a tangens was added to a mathML code, this tag is needed.
     * //<apply> <tan/> ... </apply> -> tan( )
     */
    public static String tangensEnd = "</apply>";
    //
    // some greek characters / letters
    //
    /**
     * Tag for a the greek small character alpha.
     */
    public static String alpha = "<mi>&alpha;</mi>";
    /**
     * Tag for a the greek small character beta.
     */
    public static String beta = "<mi>&beta;</mi>";
    /**
     * Tag for a the greek small character gamma.
     */
    public static String gamma = "<mi>&gamma;</mi>";
    /**
     * Tag for a the greek small character delta.
     */
    public static String deltaSmall = "<mi>&delta;</mi>";
    /**
     * Tag for a the greek big character delta.
     */
    public static String deltaBig = "<mi>&Delta;</mi>";
    /**
     * Tag for a the greek character nabla.
     */
    public static String nabla = "<mi>&nabla;</mi>";
}
