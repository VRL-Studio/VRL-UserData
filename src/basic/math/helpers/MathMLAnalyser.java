/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.util.ArrayList;

/**
 *
 * @author Night
 */
public class MathMLAnalyser {

    static private ArrayList<String> supportedOperationSymbols;

    static {
        supportedOperationSymbols = new ArrayList<String>();

        supportedOperationSymbols.add("*");
        supportedOperationSymbols.add("+");
        supportedOperationSymbols.add("-");
    }

    static public String setBrackets(String mathML) {

        String result = null;
        boolean symbolFound = false;

        for (String symbol : supportedOperationSymbols) {

            if (mathML.contains(symbol)) {
                symbolFound = true;
                result = "<ci>(</ci>" + mathML + "<ci>)</ci>";
                break; // if one symbol found brackets will be set around complett expresion
            }
        }
        if (!symbolFound) {
            result = mathML;
        }

        return result;
    }
}
