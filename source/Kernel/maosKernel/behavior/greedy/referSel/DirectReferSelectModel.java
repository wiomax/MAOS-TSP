/**
 * Description: return the first reference value for 1-move
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Jun 06, 2008 
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.referSel;

public class DirectReferSelectModel extends AbsFirstReferSelectModel {
  
  public DirectReferSelectModel() {}

  protected boolean internIsAcceptableQuality(double newReferQuality) {
    return true;
  }
}

