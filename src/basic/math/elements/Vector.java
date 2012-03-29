/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements;

import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.mappings.Mapping;
import basic.math.elements.mappings.VectorDefaultMapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import java.io.Serializable;

/**
 * This is a general class for all kind of data containing Vectors.
 *
 * @author Night
 */
@ObjectInfo(name = "Vector")
public class Vector extends Element implements VectorInterface, Serializable {

    private static final long serialVersionUID = 1;
    /**
     * this is the lable that appears at Connection befor the name of the
     * visual element.
     */
    public static String DEFAULT_NAME = "Vector";

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public Vector() {
        super();
    }



    /**
     * @param row number of rows
     * @param dataType the class of the data that is stored
     */
    public Vector(Integer row, Class<?> dataType) {
        super(new int[]{row}, dataType);

    }

    /**
     * @param row the index of the value we want
     * @return the value at index row
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row) {
        return super.getEntry(row);
    }

    /**
     * Sets the value at position(row)
     * @param row the row index
     * @param value the value to set
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Object value) {
        super.setEntry(row, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return getEntry(row);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        setEntry(row, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return getEntry(row);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        setEntry(row, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VectorInterface getVector() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatrixInterface getMatrix() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TensorInterface getTensor() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mapping createDefaultMapping() {
        return new VectorDefaultMapping(getDimensions());
    }

    /**
     * This is simplified data element that stores double compatible values.
     */
    @ObjectInfo(name = "Vector.Doubles")
    public static class Doubles extends Element.Doubles implements VectorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Vector.Doubles";

        public Doubles(Integer row) {
            super(new int[]{row});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Double getEntry(Integer row, Integer col, Integer deep) {
            return (Double) super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Double getEntry(Integer row, Integer col) {
            return (Double) super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Double getEntry(Integer row) {
            return (Double) super.getEntry(row);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Object value) {
            super.setEntry(row, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public VectorInterface getVector() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MatrixInterface getMatrix() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TensorInterface getTensor() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Mapping createDefaultMapping() {
            return new VectorDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores float compatible values.
     */
    @ObjectInfo(name = "Vector.Floats")
    public static class Floats extends Element.Floats implements VectorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Vector.Floats";

        public Floats(Integer row) {
            super(new int[]{row});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Float getEntry(Integer row, Integer col, Integer deep) {
            return (Float) super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Float getEntry(Integer row, Integer col) {
            return (Float) super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Float getEntry(Integer row) {
            return (Float) super.getEntry(row);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Object value) {
            super.setEntry(row, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public VectorInterface getVector() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MatrixInterface getMatrix() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TensorInterface getTensor() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Mapping createDefaultMapping() {
            return new VectorDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores integer compatible values.
     */
    @ObjectInfo(name = "Vector.Integers")
    public static class Integers extends Element.Integers implements VectorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Vector.Integers";

        public Integers(Integer row) {
            super(new int[]{row});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Integer getEntry(Integer row, Integer col, Integer deep) {
            return (Integer) super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Integer getEntry(Integer row, Integer col) {
            return (Integer) super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Integer getEntry(Integer row) {
            return (Integer) super.getEntry(row);
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public void setEntry(Integer row, Object value) {
            super.setEntry(row, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public VectorInterface getVector() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MatrixInterface getMatrix() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TensorInterface getTensor() {
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Mapping createDefaultMapping() {
            return new VectorDefaultMapping(getDimensions());
        }
    }
}

