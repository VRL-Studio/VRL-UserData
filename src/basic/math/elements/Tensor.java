/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements;

import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.mappings.Mapping;
import basic.math.elements.mappings.TensorDefaultMapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import java.io.Serializable;

/**
 * This is a general class for all kind of data containing Tensors.
 *
 * @author Night
 */
@ObjectInfo(name = "Tensor")
public class Tensor extends Element implements TensorInterface, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * this is the lable that appears at Connection befor the name of the
     * visual element.
     */
    public static String DEFAULT_NAME = "Tensor";

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public Tensor() {
        super();
    }

    /**
     * @param row number of rows
     * @param col number of columns
     * @param deep number of the third dimension
     * @param dataType the class of the data that is stored
     */
    public Tensor(Integer row, Integer col, Integer deep, Class<?> dataType) {
        super(new int[]{row, col, deep}, dataType);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return super.getEntry(new int[]{row, col, deep});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        super.setEntry(new int[]{row, col, deep}, value);
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
        return new TensorDefaultMapping(getDimensions());
    }

    /**
     * This is simplified data element that stores double compatible values.
     */
    @ObjectInfo(name = "Tensor.Doubles")
    public static class Doubles extends Element.Doubles implements TensorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Tensor.Doubles";

        public Doubles(Integer row, Integer col, Integer deep) {
            super(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Double getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
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
            return new TensorDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores float compatible values.
     */
    @ObjectInfo(name = "Tensor.Floats")
    public static class Floats extends Element.Floats implements TensorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Tensor.Floats";

        public Floats(Integer row, Integer col, Integer deep) {
            super(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Float getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
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
            return new TensorDefaultMapping(getDimensions());
        }
    }

    /**
     * This is simplified data element that stores integer compatible values.
     */
    @ObjectInfo(name = "Tensor.Integers")
    public static class Integers extends Element.Integers implements TensorInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Tensor.Integers";

        public Integers(Integer row, Integer col, Integer deep) {
            super(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Integer getEntry(Integer row, Integer col, Integer deep) {
            return super.getEntry(new int[]{row, col, deep});
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setEntry(Integer row, Integer col, Integer deep, Object value) {
            super.setEntry(new int[]{row, col, deep}, value);
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
            return new TensorDefaultMapping(getDimensions());
        }
    }
}
