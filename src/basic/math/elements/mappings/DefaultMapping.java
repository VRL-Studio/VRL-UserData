/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This class is a base class for Tensor-, Matrix-, Vector- and 
 * ScalarDefaultMapping with some implemented methods.
 *
 * It is not recomanded to use this class directly. Use instead the
 * specialized Mappings like TensorDefaulftMapping for Tensors.
 *
 * ATTENTION:   getIndex() is NOT implemented !.
 * @author night
 */
public class DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;
    /**
     * The number of used entries of the data array of the element
     * this mapping is used for.
     * This means if we want only to return the diagonl of 3x3 matrix the visual
     * lenght would be 3 and the array lenght of the element would be 9.
     */
    protected  int[] elementDimensions = null;
    protected Integer arrayLength = null;
    protected Integer visualLength = null;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public DefaultMapping() {
    }
    


    /**
     * @param dimensions integer array with the sizes of the element in corresponding dimension
     */
    public DefaultMapping(int[] dimensions) {
        elementDimensions = dimensions;
    }

    /**
     * {@inheritDoc}
     *
     * Additional it is checked if there could be a BufferOverflow of the index
     * for the data array of the corresponding element.
     * The index is an integer so the max number of entries is 2.147.483.647 .
     */
    @Override
    public Integer calculateCompleteArrayLength() {

        if (arrayLength == null) {
            Integer result = 1;

            for (int i = 0; i < elementDimensions.length; i++) {
                result *= elementDimensions[i];

                if (result < 0) {
                    throw new ArithmeticException("Integer BufferOverflow in " +
                            this.getClass().getName() +
                            " completeArraySize is bigger than 2.147.483.647");
                }
            }
            arrayLength = result;
        }

        return arrayLength;
    }

    /**
     * This is a helper methode that calls calculateCompleteArrayLength() to check
     * if the used array index is not smaller than 0 and bigger than the number
     * of entries in the data element.
     * Throws Exceptions if something doesn´t match.
     *
     * @param arrayIndex that should be checked for plausibility
     * @param dimensions of the underlying data structure
     */
    public void checkArrayIndex(int arrayIndex, int[] dimensions) {
        if (arrayIndex < 0) {

            System.out.println(DefaultMapping.class + " arrayIndex = " + arrayIndex);

            throw new ArithmeticException("Integer BufferOverflow in " +
                    this.getClass().getName() +
                    " completeArrayPosition is bigger than 2.147.483.647" +
                    " in last calculation.");
        }

        if (arrayIndex >= calculateCompleteArrayLength()) {

            System.out.println(DefaultMapping.class + " arrayIndex = " + arrayIndex);
            System.out.println(DefaultMapping.class + " calculateCompleteArrayLength(dimensions) = " + calculateCompleteArrayLength());

            throw new ArithmeticException("Arrayposition (index) could NOT be bigger than array size !");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {

        throw new UnsupportedOperationException("Not supported yet for " + DefaultMapping.class);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Integer getVisualLength() {
        if (visualLength != null) {
            return visualLength;
        }
        return arrayLength;
    }

    /**
     *
     * @param visualIndex is the index of an element entry, which you would assume
     *        if you see the visualization of the element.
     *        Counting begins with 0 and ends with visual element.length-1 .
     *
     *        DEFAULT IS TO RETURN THE SAME INDEX WHICH WAS INPUT PARAMETER !
     * 
     * @return the (real) index in the data array
     */
    @Override
    public Integer visualIndexToArrayIndex(Integer visualIndex) {
        return visualIndex;
    }

    /**
     * @return the elementDimensions
     */
    public int[] getElementDimensions() {
        return elementDimensions;
    }

    /**
     * @param elementDimensions the elementDimensions to set
     */
    public void setElementDimensions(int[] elementDimensions) {
        this.elementDimensions = elementDimensions;
    }

    /**
     * @return the arrayLength
     */
    public Integer getArrayLength() {
        return arrayLength;
    }

    /**
     * @param arrayLength the arrayLength to set
     */
    public void setArrayLength(Integer arrayLength) {
        this.arrayLength = arrayLength;
    }

    /**
     * @param visualLength the visualLength to set
     */
    public void setVisualLength(Integer visualLength) {
        this.visualLength = visualLength;
    }
}
