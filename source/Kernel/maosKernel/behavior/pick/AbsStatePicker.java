/**
 * Description: For picking one state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.pick;

import maosKernel.behavior.*;
import maosKernel.memory.*;

public abstract class AbsStatePicker extends AbsBehavior {
  public AbsStatePicker() {
  }

  public void setBaseState(EncodedState baseState) {}
  
  abstract public int pick(IGetEachEncodedStateEngine referEngine);

  public EncodedState pickBehavior(IGetEachEncodedStateEngine referEngine) {
    int pickID = pick(referEngine);
    if (pickID==-1) return null;
    return referEngine.getSelectedPoint(pickID);
  }
  
  public void reverseType() {
  }
}
