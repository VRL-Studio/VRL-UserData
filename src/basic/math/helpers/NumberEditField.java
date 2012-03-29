/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to check the user done input for element entries.
 * By using this class only validated characters (numbers) can be written into
 * an element.
 * 
 * @author night
 */
public class NumberEditField extends JTextField {

    private Toolkit toolkit;
    private NumberFormat numberFormatter;

    public NumberEditField(double value, int columns) {
        super(columns); //set the size of the textfield

        toolkit = Toolkit.getDefaultToolkit();

         numberFormatter = NumberFormat.getNumberInstance(Locale.US);
//        numberFormatter = NumberFormat.getNumberInstance(Locale.GERMAN);

        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                selectAll();
                
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        setValue(value);
    }

    public double getValue() {
        double retVal = 0;
        try {
            retVal = numberFormatter.parse(getText()).doubleValue();
        } catch (ParseException e) {
            // This should never happen because insertString allows
            // only properly formatted data to get in the field.
            toolkit.beep();
        }
        return retVal;
    }

    /**
     * Sets the value in the text field and checks it by the numberFormater.
     *
     * @param value the value to by set
     */
    public void setValue(double value) {
        setText(numberFormatter.format(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Document createDefaultModel() {
        return new NumberDocument(); //get one instance which checks the keyinput
    }

    /**
     * Class which contains the logic to check for correct entries during typing.
     * The input is allowed to start with "+" or "-" followed by some or no digits,
     * use one "." if US number format is used or "," if german number format is used
     * and again followed by the numbers from 0 to 9.
     */
    protected class NumberDocument extends PlainDocument {

        private Pattern pattern = Pattern.compile("[\\-]?\\d*\\.?\\d*"); // US number format
//        private Pattern pattern = Pattern.compile("[\\-]?\\d*\\,?\\d*"); // German number fomat

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertString(int offset, String str, AttributeSet a)
                throws BadLocationException {

            String result = super.getText(0, getLength());
            result = new StringBuffer(result).insert(offset, str).toString();

            Matcher matcher = pattern.matcher(result);

            boolean resultAccepted = matcher.matches();

            if (resultAccepted) {
                super.insertString(offset, str, a);
            }
        }
    }
}
