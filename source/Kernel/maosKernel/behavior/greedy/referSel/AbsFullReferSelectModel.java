/**
 * Description: provide the reference value for 1-move, it is finished
 *   is the first acceptable value is found
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

import maosKernel.represent.landscape.quality.*;

public abstract class AbsFullReferSelectModel extends AbsReferSelectModel {
  protected double ultimateQuality;
  
  public AbsFullReferSelectModel() {}
  
  public void initModel() {
    ultimateQuality = ICalcGlobalCostEngine.WOSRTVALUE;
  }

  abstract protected boolean internIsAcceptableQuality(double newReferQuality);

  public boolean isAcceptableQuality(double newReferQuality) {
    boolean isAcceptable = internIsAcceptableQuality(newReferQuality);
    if (isAcceptable) {
      ultimateQuality = newReferQuality;
    }
    return isAcceptable;
  }

  public boolean isFinished() {
    return false;
  }
}

