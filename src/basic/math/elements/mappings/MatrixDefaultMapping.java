/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This is the default mapping for Matrizes.
 * This means every entry of a Matrix can be accessed and returned.
 *
 * @author night
 */
public class MatrixDefaultMapping extends DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public MatrixDefaultMapping() {
        super();
    }



    public MatrixDefaultMapping(int[] dimensions) {
        super(dimensions);
    }

    /**
     * {@inheritDoc}
     *
     * Additional it checks the index with the help of checkArrayIndex().
     *
     *
     * Example Matrix 3x4 (rowNumber x colNumber)
     * left: row
     *  up : column
     *
     *   ______0___________1___________2___________3______
     * 0 | ( 0 , 0 ) | ( 0 , 1 ) | ( 0 , 2 ) | ( 0 , 3 ) |
     *   -------------------------------------------------
     * 1 | ( 1 , 0 ) | ( 1 , 1 ) | ( 1 , 2 ) | ( 1 , 3 ) |
     *   -------------------------------------------------
     * 2 | ( 2 , 0 ) | ( 2 , 1 ) | ( 2 , 2 ) | ( 2 , 3 ) |
     *   -------------------------------------------------
     *
     * In array all in a line. Start count in the left upper corner with 0
     * (Zero) then go right and add 1 (one) at end of row go on with next row
     * and so on.
     *
     * position( row , col ) -> index
     * ( 0 , 0 ) ->   0
     * ( 1 , 0 ) ->   4
     * ( 1 , 2 ) ->   6
     * ( 2 , 3 ) ->   11
     *
     * index = colNumber * pos(row) + pos(col)
     *   6   =     4     *      1   +      2
     *
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {
        //because of array beginns with 0 and human starts with 1
        //and for a matrix the first dimension is the row position
        //and the second dimension the column position

        Integer result = dimensions[1] * position[0] + position[1];

        checkArrayIndex(result, dimensions);

        return result;
    }
}
