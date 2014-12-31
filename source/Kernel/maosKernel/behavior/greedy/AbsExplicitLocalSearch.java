/**
 * Description: The description of local search by moving based on an 
 *   incumbent State, which tries to improve its quality.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy;

import maosKernel.behavior.AbsBehavior;
import maosKernel.behavior.incumbent.*;
import maosKernel.memory.EncodedState;
import maosKernel.represent.landscape.quality.IGetDeltaCostEngine;
import maosKernel.represent.landscape.quality.IGetGlobalCostEngine;
import maosKernel.represent.landscape.space.SearchState;

public abstract class AbsExplicitLocalSearch extends AbsIncumbentSearch {
  public AbsExplicitLocalSearch() {}

  abstract protected boolean generate(SearchState baseState);
  
  public boolean generate(EncodedState baseState) {
    if (!generate(baseState.getSearchState())) return false;

    getTrialStateQuality(this, baseState);
    return true;
  }

  private void getTrialStateQuality(AbsBehavior searchBehavior, EncodedState baseState) {
    if (baseState.isEncoded() && searchBehavior instanceof IGetDeltaCostEngine) {
      baseState.setEncodeInfo(baseState.getEncodeInfo());
      baseState.setDeltaEncodeInfo(((IGetDeltaCostEngine)searchBehavior).encodeLocal());
    } else if ((searchBehavior instanceof IGetGlobalCostEngine)) {
      baseState.setEncodeInfo(((IGetGlobalCostEngine)searchBehavior).encodeGlobal());
    } else {
      baseState.removeEncodeInfo();
    }
  }

  public boolean getStateQualityImprovingFlag() {
    return false;
  }
}
