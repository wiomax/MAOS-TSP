/**
 * Description: For two encoded states (acceptState, donateState), updating acceptState 
 *   based on acceptState & donateState
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.update;

import maosKernel.behavior.*;
import maosKernel.memory.*;

public abstract class AbsStateUpdater extends AbsBehavior { 

  public AbsStateUpdater() {
  }
  
  public boolean updateBehavior(EncodedState basicState, EncodedState inputState) {
    if (isAccepted(basicState, inputState)) {
      basicState.importEncodeState(inputState);
      return true;
    }
    return false;
  }
  
  abstract protected boolean isAccepted(EncodedState basicState, EncodedState inputState);
}
