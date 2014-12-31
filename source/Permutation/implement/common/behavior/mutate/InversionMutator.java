/**
 * Description: Select two points along the permutation, cut it at
 *  these points and re-insert the reversed string.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 18, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class InversionMutator extends AbsPerturbationSearch {
  public InversionMutator() {
  }

  public boolean generate(SearchState baseState){
    ArrayOperator.insertTo(baseState.getIArray(), RandomGenerator.intRangeRandom(baseState.getNodeNumber()), RandomGenerator.intRangeRandom(baseState.getNodeNumber()));
    return true;
  }
}
