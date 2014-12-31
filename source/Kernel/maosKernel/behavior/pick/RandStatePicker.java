/**
 * Description: For picking one state at random
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.pick;

import maosKernel.memory.*;

public class RandStatePicker extends AbsStatePicker {

  public int pick(IGetEachEncodedStateEngine referEngine) {
    return StateSetHandler.getRandomIndex(referEngine);
  }
}
