/**
 * Description: Updating A as B only as state B has a noworse quality.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.update;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.quality.*;

public class BetterStateUpdater extends AbsStateUpdater {
  private IQualityEvaluateEngine evaluateEngine;

  public BetterStateUpdater() {
  }

  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }

  protected boolean isAccepted(EncodedState basicState, EncodedState inputState) {
    return EncodedStateHandler.evaluate(evaluateEngine, inputState, basicState);
  }

}
