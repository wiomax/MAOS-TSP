/**
 * Description: The generating operator (with quality based control for the base state) 
 *   for achieving an trial state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 02, 2008
 *
 * @version M01.00.02
 * @since M01.00.02
 */

package maosKernel.behavior.generate;

import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;

public class MiniSBILQXGenerator extends MiniSBILXGenerator {
  private IQualityEvaluateEngine evaluateEngine;

  public MiniSBILQXGenerator() {}
  
  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }
  
  protected boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    EncodedState innerBaseState = baseState, innerReferState = referState;
    if (!EncodedStateHandler.evaluate(evaluateEngine, baseState, referState)) {
      innerBaseState = referState;
      innerReferState = baseState;
    }
    return super.generate(trialState, innerBaseState, innerReferState);
  }
}
