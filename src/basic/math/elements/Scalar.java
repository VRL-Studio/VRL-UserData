/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements;

import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.ScalarInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.mappings.Mapping;
import basic.math.elements.mappings.ScalarDefaultMapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import java.io.Serializable;

/**
 * This is a general class for all kind of data containing Scalars.
 *
 * @author Night
 */
@ObjectInfo(name = "Scalar")
public class Scalar extends Element implements ScalarInterface, Serializable {

    private static final long serialVersionUID = 1;
    /**
     * this is the lable that appears at Connection befor the name of the
     * visual element.
     */
    public static String DEFAULT_NAME = "Scalar";

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public Scalar() {
        super();
    }

    /**
     * generate a Scalar by calling super(1), which generate a vector of length 1.
     */
    public Scalar(Class<?> dataType) {
        super(new int[]{1}, dataType);

    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry() {
        return super.getEntry(0);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        setEntry(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        setEntry(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Object value) {
        setEntry(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Object value) {
        super.setEntry(0, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScalarInterface getScalar() {
        return this;
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
        return new ScalarDefaultMapping(getDimensions());
    }

    /**
     * This is simplified data element that stores integer compatible values.
     */
    @ObjectInfo(name = "Scalar.Integers")
    public static class Integers extends Element.Integers implements ScalarInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Scalar.Integers";

        public Integers() {
            super(new int[]{1});
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Integer getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
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
            return super.getEntry(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Integer getEntry() {
            return (Integer) super.getEntry(0);
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Object value) {
            super.setEntry(0, value);
        }

        /**
     * {@inheritDoc}
     */
        @Override
        public ScalarInterface getScalar() {
            return this;
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
            return new ScalarDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores float compatible values.
     */
    @ObjectInfo(name = "Scalar.Floats")
    public static class Floats extends Element.Floats implements ScalarInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Scalar.Floats";

        public Floats() {
            super(new int[]{1});
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Float getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
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
            return super.getEntry(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Float getEntry() {
            return (Float) super.getEntry(0);
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Object value) {
            super.setEntry(0, value);
        }

        /**
     * {@inheritDoc}
     */
        @Override
        public ScalarInterface getScalar() {
            return this;
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
            return new ScalarDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores double compatible values.
     */
    @ObjectInfo(name = "Scalar.Doubles")
    public static class Doubles extends Element.Doubles implements ScalarInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Scalar.Doubles";

        public Doubles() {
            super(new int[]{1});
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true, noGUI = true)
        @Override
        public Double getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
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
            return super.getEntry(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Double getEntry() {
            return (Double) super.getEntry(0);
        }

        /**
     * {@inheritDoc}
     */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Object value) {
            super.setEntry(0, value);
        }

        /**
     * {@inheritDoc}
     */
        @Override
        public ScalarInterface getScalar() {
            return this;
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
            return new ScalarDefaultMapping(getDimensions());
        }
    }
}
