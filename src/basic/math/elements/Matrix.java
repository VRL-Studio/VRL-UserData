/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements;

import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.mappings.Mapping;
import basic.math.elements.mappings.MatrixDefaultMapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import java.io.Serializable;

/**
 * This is a general class for all kind of data containing Matrixes.
 *
 * @author Night
 */
@ObjectInfo(name = "Matrix")
public class Matrix extends Element implements MatrixInterface, Serializable {

    private static final long serialVersionUID = 1;
    /**
     * this is the lable that appears at Connection befor the name of the
     * visual element.
     */
    public static String DEFAULT_NAME = "Matrix";

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public Matrix() {
        super();
    }


    /**
     * @param row number of rows
     * @param col number of columns
     * @param dataType the class of the data that is stored
     */
    public Matrix(Integer row, Integer col, Class<?> dataType) {
        super(new int[]{row, col}, dataType);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return super.getEntry(new int[]{row, col});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        super.setEntry(new int[]{row, col}, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public String getDefaultName() {
        return DEFAULT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return getEntry(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        setEntry(row, col, value);
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
        return new MatrixDefaultMapping(getDimensions());
    }

     /**
     * This is simplified data element that stores double compatible values.
     */
    @ObjectInfo(name = "Matrix.Doubles")
    public static class Doubles extends Element.Doubles implements MatrixInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Matrix.Doubles";

        public Doubles(Integer row, Integer col) {
            super(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Double getEntry(Integer row, Integer col) {
            return super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
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
            return new MatrixDefaultMapping(getDimensions());
        }
    }

     /**
     * This is simplified data element that stores float compatible values.
     */
    @ObjectInfo(name = "Matrix.Floats")
    public static class Floats extends Element.Floats implements MatrixInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Matrix.Floats";

        public Floats(Integer row, Integer col) {
            super(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Float getEntry(Integer row, Integer col) {
            return super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
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
            return new MatrixDefaultMapping(getDimensions());
        }
    }

     /**
     * This is simplified data element that stores integer compatible values.
     */
    @ObjectInfo(name = "Matrix.Integers")
    public static class Integers extends Element.Integers implements MatrixInterface {

        private static final long serialVersionUID = 1;
        /**
         * this is the lable that appears at Connection befor the name of the
         * visual element.
         */
        public static String DEFAULT_NAME = "Matrix.Integers";

        public Integers(Integer row, Integer col) {
            super(new int[]{row, col});
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
        @MethodInfo(hide = true)
        @Override
        public Integer getEntry(Integer row, Integer col) {
            return super.getEntry(new int[]{row, col});
        }

        /**
         * {@inheritDoc}
         */
        @MethodInfo(hide = true)
        @Override
        public void setEntry(Integer row, Integer col, Object value) {
            super.setEntry(new int[]{row, col}, value);
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
            return new MatrixDefaultMapping(getDimensions());
        }
    }
}
