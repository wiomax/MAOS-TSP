/**
 * Description: The description of recombination search with a quality-based base state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 29, 2008 
 *
 * @version M01.00.02
 * @since M01.00.02
 * 
 * @Reference:
 * [1] Xiao-Feng Xie, Wen-Jun Zhang, Zhi-Lian Yang. Social cognitive optimization for nonlinear programming 
 * problems. International Conference on Machine Learning and Cybernetics (ICMLC). Beijing, China, 2002: 779-783
 */

package maosKernel.behavior.combine;

import maosKernel.memory.*;
import maosKernel.represent.landscape.AbsLandscape;
import maosKernel.represent.landscape.quality.IQualityEvaluateEngine;
import Global.basic.nodes.utilities.*;

public class QBaseRecombinationSearch extends AbsRecombinationSearch {
  private AbsRecombinationSearch recombinationSearch;
  private IQualityEvaluateEngine evaluateEngine;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("recombinationSearch", recombinationSearch));
  }

  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    recombinationSearch = (AbsRecombinationSearch)(getValue("recombinationSearch"));
  }

  public boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    EncodedState innerBaseState = baseState, innerReferState = referState;
    if (!EncodedStateHandler.evaluate(evaluateEngine, baseState, referState)) {
      innerBaseState = referState;
      innerReferState = baseState;
    }
    return recombinationSearch.generate(trialState, innerBaseState, innerReferState);
  }
}
