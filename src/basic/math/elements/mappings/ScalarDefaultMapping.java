/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.mappings;

/**
 * This is the default mapping for Scalars.
 * This means the one and only entry of a Scalar can be accessed and returned.
 *
 * @author night
 */
public class ScalarDefaultMapping extends DefaultMapping implements Mapping {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     */
    public ScalarDefaultMapping() {
        super();
    }

    public ScalarDefaultMapping(int[] dimensions) {
        super(dimensions);
    }

    /**
     * {@inheritDoc}
     *
     * @param position is not really used here. It will be always 0 retuned.
     *
     * Additional it checks the index with the help of checkArrayIndex().
     *
     */
    @Override
    public Integer getIndex(int[] position, int[] dimensions) {
        //because of array beginns with 0
        //and a scalar just have only one entry
        checkArrayIndex(0, dimensions);
        return 0;
    }
}
