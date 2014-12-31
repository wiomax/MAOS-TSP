/**
 * Description: Updating A as B directly.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.update;

import maosKernel.memory.*;

public class DirectStateUpdater extends AbsStateUpdater {
  protected boolean isAccepted(EncodedState basicState, EncodedState inputState) {
    return true;
  }
}
