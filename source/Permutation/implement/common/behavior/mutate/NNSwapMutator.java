/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class NNSwapMutator extends AbsPerturbationSearch {
  public NNSwapMutator() {
  }

  public boolean generate(SearchState baseState){
    ArrayOperator.nnSwapData(baseState.getIArray(), RandomGenerator.intRangeRandom(baseState.getNodeNumber()-1));
    return true;
  }
}
