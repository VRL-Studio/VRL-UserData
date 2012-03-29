/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;

import basic.math.ug.equation.helpers.*;
import java.util.ArrayList;

/**
 * Formel is an ArrayList of FormelEntry
 * and represents the equation which is used
 * to solve a given problem.
 *
 * @author Night
 */
public class Formel extends ArrayList<FormelEntry>{


    @Override
    public String toString(){

        String result = "";
        for (FormelEntry e : this){
            result+=e.getFormel();
        }

        return result;
    }
}
