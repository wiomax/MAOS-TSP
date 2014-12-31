/**
 * Description: For storing best solution.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 5, 2004
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.memory.*;
import maosKernel.infoIO.runtime.*;

public final class SolutionDepositor implements IGetBestSolutionEngine {
  private IQualityEvaluateEngine evalEngine;
  private EncodedState bestState = null;

  public SolutionDepositor(AbsLandscape virtualLandscape) {
    bestState = new EncodedState(virtualLandscape);
    this.evalEngine =virtualLandscape;
    clear();
  }

  public boolean depositPoint(EncodedState newPoint) {
    if (!EncodedStateHandler.evaluate(evalEngine, bestState, newPoint)) {
      bestState.importEncodeState(newPoint);
      return true;
    }
    return false;
  }

  public void clear() {
    bestState.removeEncodeInfo();
  }

  public EncodedState getBestState() {
    return bestState;
  }
}
