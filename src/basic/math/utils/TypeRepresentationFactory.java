/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.utils;

/**
 *
 * @author christianpoliwoda
 */
public class TypeRepresentationFactory {
    
    /**   COPY FROM VRL class DefaultTypeRepresentationFactory
     * 
     * Checks whether a given class object is a primitive and returns its
     * wrapper class. If the class is no primitive the class will be returned
     * without changes.
     * @param clazz the class to convert
     * @return the wrapper class
     */
    public static Class<?> convertPrimitiveToWrapper(Class<?> clazz) {
        Class<?> result = clazz;

        if (clazz.isPrimitive()) {
            if (clazz.getName().equals("boolean")) {
                result = Boolean.class;
            } else if (clazz.getName().equals("short")) {
                result = Short.class;
            } else if (clazz.getName().equals("int")) {
                result = Integer.class;
            } else if (clazz.getName().equals("long")) {
                result = Long.class;
            } else if (clazz.getName().equals("float")) {
                result = Float.class;
            } else if (clazz.getName().equals("double")) {
                result = Double.class;
            } else if (clazz.getName().equals("byte")) {
                result = Byte.class;
            } else if (clazz.getName().equals("char")) {
                result = Character.class;
            }
        }

        return result;
    }
}
