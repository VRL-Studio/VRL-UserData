/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.helpers;


/**
 * Interface to allow to change the size of e.g matrices which are representanted
 * by <code>FormelEntry</code>´s
 *
 * @author night
 */
public interface SetSizeInterface {
    public void setFormelEntry(FormelEntry entry);
    public FormelEntry getFormelEntry();
}
