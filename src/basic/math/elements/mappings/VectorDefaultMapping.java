/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This is the default mapping for Vectors.
 * This means every entry of a Vector can be accessed and returned.
 *
 * @author night
 */
public class VectorDefaultMapping extends DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;

     /**
     * Needed for XML- -Encoder / -Decoder
     */
    public VectorDefaultMapping() {
        super();
    }


    public VectorDefaultMapping(int[] dimensions) {
        super(dimensions);
    }

    /**
     * {@inheritDoc}
     *
     * Additional it checks the index with the help of checkArrayIndex().
     *
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {
        //because of array beginns with 0 and human starts with 1
        //and a vector just have only one dimension

//        System.out.println("position[0] = " + position[0]);
        checkArrayIndex(position[0] , dimensions);
        return position[0] ;
    }
}

