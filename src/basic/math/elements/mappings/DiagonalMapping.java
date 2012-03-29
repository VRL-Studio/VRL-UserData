/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This Mapping is disgned to be used for m x n matrizes where m and n have
 * different sizes.
 * The DiagonalMapping operats on a matrix element but allows only access on
 * the diagonal entries of the matrix.
 *
 * @author night
 */
public class DiagonalMapping extends DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public DiagonalMapping() {
        super();
    }



    public DiagonalMapping(int[] dimensions) {
        super(dimensions);
        visualLength = Math.min(dimensions[0], dimensions[1]);
    }

    /**
     * @param position the wanted entry position
     * @param dimensions the size of the element
     * @return the calculated index
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {

        Integer result = null;

        if (position[0] <= visualLength) {

            result = getElementDimensions()[1] * position[0] + position[0];
//            System.out.println(DiagonalMapping.class + ".getIndex()= " + result + "\n " +
//                    "matrixDimensions[1]= " + elementDimensions[1] + " " +
//                    "position[0]= " + position[0] + " " +
//                    "arrayLength= " + arrayLength);

        } else {
            throw new ArrayIndexOutOfBoundsException(DiagonalMapping.class +
                    ".getIndex(): position[0]= " + position[0] + " is bigger than visualLength=" + visualLength);
        }

        checkArrayIndex(result,getElementDimensions());

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * Default implementation is NOT used.
     *  
     * @return the calculated array index
     */
    @Override
    public Integer visualIndexToArrayIndex(Integer visualIndex) {

         if ( visualIndex <= visualLength) {
             return getElementDimensions()[1] * visualIndex + visualIndex;
         }
        throw new ArrayIndexOutOfBoundsException(DiagonalMapping.class +
                    ".visualIndexToArrayIndex(): visualIndex= " + visualIndex +
                    " is bigger than visualLength= " + visualLength);
    }
}
