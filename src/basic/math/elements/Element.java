/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements;

import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.mappings.DefaultMapping;
import basic.math.elements.mappings.Mapping;
import basic.math.utils.TypeRepresentationFactory;
import eu.mihosoft.vrl.annotation.ObjectInfo;
//import eu.mihosoft.vrl.reflection.TypeRepresentationFactory;
import java.lang.reflect.Array;

/**
 * This is a general class for all kind of data containing mathematic elements
 * like Scalars, Vectors, Matrixes and Tensors.
 *
 * @author Night
 */
@ObjectInfo(name = "Element")
public /*abstract*/ class Element implements ElementInterface {

    private static final long serialVersionUID = 1L;
    /**
     * this is the lable that appears at Connection befor the name of the
     * visual element.
     */
    protected static String DEFAULT_NAME = "Element";

    /**
     * the lable that appears at Connection befor the name of the
     * visual element.
     * @return the DEFAULT_NAME
     */
    public static String getDEFAULT_NAME() {
        return DEFAULT_NAME;
    }

    /**
     * @param aDEFAULT_NAME the DEFAULT_NAME to set
     */
    public static void setDEFAULT_NAME(String aDEFAULT_NAME) {
        DEFAULT_NAME = aDEFAULT_NAME;
    }
    /**
     * Stores the information about how many dimensions are used (dimensions.length)
     * and how many entries in dimension i there are (dimension[i]).
     */
    private int[] dimensions;
    /**
     * stores the length of the linear stored data array
     */
    private Integer arrayLength;
    /**
     * The mapping knows what data structure is stored in the data array and
     * how to get the entry of a multidimensional data element from the
     * linear data array.
     */
    protected Mapping mapping;
    /**
     * The object where all the data is stored.
     */
    private Object dataArray = null;
    /**
     * Stores the information of which kind the data is.
     * E.g. Double, Float, Integer or ..
     */
    private Class<?> dataType = null;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public Element() {
    }

    /**
     * @param dimensions the Array of Integers which contains the value /
     *          deppness information for each dimension.
     * @param clazz the class of the stored data
     */
    public Element(int[] dimensions, Class<?> clazz) {
        this.dimensions = dimensions;
        this.dataType = clazz;

        this.mapping = createDefaultMapping();
        this.arrayLength = mapping.calculateCompleteArrayLength();

        dataArray = Array.newInstance(dataType, arrayLength);
    }

    /**
     * Copies all entries from the parameter Element to this.
     *
     * @param element which dataArray should be copied.
     */
    public void copyAllEntries(Element element) {

        if (checkDimensions(element)) {

            for (int i = 0; i < arrayLength; i++) {
                Array.set(dataArray, i, element.getEntry(i));
            }

        } else {
            throw new IllegalArgumentException("Dimensions are not equal, so " +
                    this.getClass().getName() + ".copyAllEntries() done nothing !");
        }
    }

    /**
     * Checks if this Element and the other Element have the same dimensions.
     *
     * @param element the Element which should be compared with this.
     *
     * @return true if all sizes are equal, false otherwise.
     */
    public Boolean checkDimensions(Element element) {
        Boolean result = Boolean.TRUE;

        if (dimensions.length != element.getDimensions().length) {
            result = Boolean.FALSE;
        } else {
            for (int i = 0; i < dimensions.length; i++) {
                if (element.getDimensions()[i] != dimensions[i]) {
                    result = Boolean.FALSE;
                }
            }
        }

        return result;
    }

    /**
     * the lable that appears at Connection befor the name of the
     * visual element.
     * @return the default name for this class
     */
    @Override
    public String getDefaultName() {
        return DEFAULT_NAME;
    }

    /**
     * {@inheritDoc}
     *
     * @return a string containing all data entries in a line
     */
    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < dimensions.length; i++) {
            result += Array.get(dataArray, i).toString() + " ";
        }

        return result;
    }

    /**
     * Checks if position request is compatibal with used dimensions.
     * @param position that should be used
     * @return true if compatible
     */
    protected Boolean checkPosition(int[] position) {
        if (position.length > getDimensions().length) {

            for (int i = getDimensions().length; i < position.length; i++) {

                if (position[i] != 0) {
                    throw new IllegalArgumentException(
                            "if position lenght is bigger than dimension lenght" +
                            "all entries with higher index must be 0.");
                }
            }
        }
        return true;
    }

    /**
     * Checks if the object obj is compatible or an instance of the class which
     * is stored in the internal variable dataType and can therefor be stored.
     *
     * @param obj the object to be checked
     * @return true if is instance of dataType, else throws Exception
     */
    protected Boolean checkCompatibility(Object obj) {
//        System.out.println(Element.class + " dataType = " + dataType);
//        System.out.println(Element.class + " obj.getClass() = " + obj.getClass());
//            System.out.println("obj.isPrimitive " + obj.getClass().isPrimitive());
//            System.out.println("dataType.isPrimitive " + dataType.isPrimitive());

        Class<?> objClass = null;
        Class<?> dataTypeClass = null;

        if (dataType.isPrimitive()) {
            dataTypeClass = TypeRepresentationFactory.convertPrimitiveToWrapper(dataType);
        } else {
            dataTypeClass = dataType;
        }

        if (obj.getClass().isPrimitive()) {
            objClass = TypeRepresentationFactory.convertPrimitiveToWrapper(obj.getClass());
        } else {
            objClass = obj.getClass();
        }

        if (dataTypeClass.isAssignableFrom(objClass)) {
            return true;
        }

        Object convertedObj = convertValue(obj);

        if (convertedObj.getClass().equals(dataType)) {
            return true;
        } else {
            throw new IllegalArgumentException(Element.class + "\n" +
                    " Value must be compatibel with " + dataType +
                    "\n value is " + obj.getClass());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getEntry(int index) {
        if ((index >= 0) && (index < arrayLength)) {
            //            return Array.get(array, index);
            return Array.get(dataArray, index);
        } else {

            throw new IllegalArgumentException(Element.class + ".getEntry() \n" +
                    " Index is " + index + " but need to be between: 0 and " + arrayLength + ".");
        }
    }

    /**
     * {@inheritDoc}
     * If the requested position is aviable the corresponding entry is returned
     * else null.
     * @param position the coordinated of the wanted entry
     * @return the corresponding entry or null
     */
    @Override
    public Object getEntry(int[] position) {
        if (checkPosition(position)) {
            return Array.get(dataArray, getMapping().getIndex(position, dimensions));
        }
        return null;
    }

    /**
     * {@inheritDoc }
     *
     * Checks additionally the validition of position and the compability of
     * value with the in this element storable data.
     */
    @Override
    public void setEntry(int[] position, Object value) {
        if (checkPosition(position)) {

            if (checkCompatibility(value)) {

                Object convertedValue = convertValue(value);

                Array.set(dataArray, getMapping().getIndex(position, dimensions), convertedValue);
            }
        }
    }

    /**
     * {@inheritDoc}
     * Checks additionally the compability of the to be stored value.
     */
    @Override
    public void setEntry(int index, Object value) {
        if ((index >= 0) && (index < arrayLength) && (checkCompatibility(value))) {

            Object convertedValue = convertValue(value);

            Array.set(dataArray, index, convertedValue);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getDimensions() {
        return dimensions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillAllEntriesWith(Object value) {
        for (int i = 0; i < arrayLength; i++) {
            Array.set(dataArray, i, value);
        }
    }

    /**
     * Try to convert/typcast the value in the number typ which is stored in datatyp.
     * If no matching case is found return the original value.
     *
     * @param value the value which should be converted / typcast
     * @return the converted value if possible else original value
     */
    public Object convertValue(Object value) {

        if (value instanceof Number) {
            
            Double d = new Double(value.toString());

            if (dataType.equals(Integer.class)) {

                return new Integer(d.intValue());
            }

            if (dataType.equals(Float.class)) {
                return new Float(d.floatValue());
            }

            if (dataType.equals(Long.class)) {
                return new Long(d.longValue());
            }

            if (dataType.equals(Double.class)) {
                return d;
            }
        }// end if number

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getDataType() {
        return dataType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Mapping getMapping() {
        return mapping;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Mapping createDefaultMapping() {
        return new DefaultMapping(getDimensions());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Integer getArrayLength() {
        return arrayLength;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Object getDataArray() {
        return dataArray;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public ElementInterface getElement() {
        return this;
    }

    /**
     * @param dimensions the dimensions to set
     */
    @Override
    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * @param arrayLength the arrayLength to set
     */
    @Override
    public void setArrayLength(Integer arrayLength) {
        this.arrayLength = arrayLength;
    }

    /**
     * @param dataArray the dataArray to set
     */
    @Override
    public void setDataArray(Object dataArray) {
        this.dataArray = dataArray;
    }

    /**
     * This is simplified data element that stores double compatible values.
     */
    @ObjectInfo(name = "Element.Doubles")
    public static abstract class Doubles extends Element implements ElementInterface {

        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Element.Doubles";

        /**         
         * @param dims an array which contains the dimensions
         */
        public Doubles(int[] dims) {
            super(dims, Double.TYPE);
            super.fillAllEntriesWith(new Double(0));
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public Double getEntry(int[] pos) {
            return (Double) super.getEntry(pos);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public ElementInterface getElement() {
            return super.getElement();
        }
    }

    /**
     * This is simplified data element that stores float compatible values.
     */
    @ObjectInfo(name = "Element.Floats")
    public static abstract class Floats extends Element implements ElementInterface {

        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Element.Floats";

        /**
         * @param dims an array which contains the dimensions
         */
        public Floats(int[] dims) {
            super(dims, Float.TYPE);
            super.fillAllEntriesWith(new Float(0));
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public Float getEntry(int[] pos) {
            return (Float) super.getEntry(pos);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public ElementInterface getElement() {
            return super.getElement();
        }
    }

    /**
     * This is simplified data element that stores integer compatible values.
     */
    @ObjectInfo(name = "Element.Integers")
    public static abstract class Integers extends Element implements ElementInterface {

        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Element.Integers";

        /**
         * @param dims an array which contains the dimensions
         */
        public Integers(int[] dims) {
            super(dims, Integer.TYPE);
            super.fillAllEntriesWith(new Integer(0));
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public Integer getEntry(int[] pos) {
            return (Integer) super.getEntry(pos);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public ElementInterface getElement() {
            return super.getElement();
        }
    }
}
