/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This is the default mapping for Tensors.
 * This means every entry of a Tensor can be accessed and returned.
 *
 * @author night
 */
public class TensorDefaultMapping extends DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public TensorDefaultMapping() {
        super();
    }


    public TensorDefaultMapping(int[] dimensions) {
        super(dimensions);
    }

    /**
     * {@inheritDoc}
     *
     * Additional it checks the index with the help of checkArrayIndex().
     *
     *
     * Example Tensor 3x2x4 (rowNumber x colNumber x deepNumber)
     * left : row
     *  up  : column
     * above: deep
     *
     * TENSOR POSITION
     *   0                                       1
     *   ________0_______________1________   *   ________0_______________1________
     * 0 | ( 0 , 0 , 0 ) | ( 0 , 1 , 0 ) |   * 0 | ( 0 , 0 , 1 ) | ( 0 , 1 , 1 ) |
     *   ---------------------------------   *   ---------------------------------
     * 1 | ( 1 , 0 , 0 ) | ( 1 , 1 , 0 ) |   * 1 | ( 1 , 0 , 1 ) | ( 1 , 1 , 1 ) |
     *   ---------------------------------   *   ---------------------------------
     * 2 | ( 2 , 0 , 0 ) | ( 2 , 1 , 0 ) |   * 2 | ( 2 , 0 , 1 ) | ( 2 , 1 , 1 ) |
     *   ---------------------------------   *   ---------------------------------
     *
     *   2                                       3
     *   ________0_______________1________   *   ________0_______________1________
     * 0 | ( 0 , 0 , 2 ) | ( 0 , 1 , 2 ) |   * 0 | ( 0 , 0 , 3 ) | ( 0 , 1 , 3 ) |
     *   ---------------------------------   *   ---------------------------------
     * 1 | ( 1 , 0 , 2 ) | ( 1 , 1 , 2 ) |   * 1 | ( 1 , 0 , 3 ) | ( 1 , 1 , 3 ) |
     *   ---------------------------------   *   ---------------------------------
     * 2 | ( 2 , 0 , 2 ) | ( 2 , 1 , 2 ) |   * 2 | ( 2 , 0 , 3 ) | ( 2 , 1 , 3 ) |
     *   ---------------------------------   *   ---------------------------------
     *
     * INDEX
     *   0                1
     *   __0____1___  *   __0____1___
     * 0 | 0  | 1  |  * 0 | 6  | 7  |
     *   ----------   *   -----------
     * 1 | 2  | 3  |  * 1 | 8  | 9  |
     *   ----------   *   -----------
     * 2 | 4  | 5  |  * 2 | 10 | 11 |
     *   ----------   *   -----------
     *
     *   2                3
     *   __0____1___  *   __0____1___
     * 0 | 12 | 13 |  * 0 | 18 | 19 |
     *   ----------   *   -----------
     * 1 | 14 | 15 |  * 1 | 20 | 21 |
     *   ----------   *   -----------
     * 2 | 16 | 17 |  * 2 | 22 | 23 |
     *   ----------   *   -----------
     *
     * Data is stored in an  one dimensional array. Start counting at deep 0 in
     * the left upper corner with 0 (Zero) then go right and add 1 (one) at end
     * of row go on with next row and so on.
     *
     * position( row , col , deep ) -> index
     * ( 0 , 0 , 0 ) ->   0
     * ( 1 , 0 , 2 ) ->   14
     * ( 2 , 1 , 3 ) ->   23
     *
     * index = rowNumber * colNumber * pos(deep) +
     *         colNumber * pos(row) +
     *         pos(col) 
     *   14  =  3 * 2 * ( 2 ) + 2 *( 1 ) + ( 0 )
     *   23  =  3 * 2 * ( 3 ) + 2 *( 2 ) + ( 1 )
     *
     * ( x, y, 3) = 3*2 * (3) =18
     * ( x, 1, z) = (1)       =1
     * ( 2, y, z) = 2 * (2)   =4
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {
        //because of array beginns with 0 and human starts with 1
        //and for a tensor the first dimension is the row position,
        //the second dimension the column position and the third the deep

        int arrayIndex =
                dimensions[0] * dimensions[1] * position[2]  +
                dimensions[1] * position[0]  +
                position[1] ;
        
        checkArrayIndex(arrayIndex, dimensions);

        return arrayIndex;
    }
}

