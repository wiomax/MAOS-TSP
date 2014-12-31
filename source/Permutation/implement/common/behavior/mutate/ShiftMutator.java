/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 20, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class ShiftMutator extends AbsPerturbationSearch {
  private double minRou = 0;
  private double maxRou =0.5;

  public ShiftMutator() {
  }

  public boolean generate(SearchState baseState){
    int realMu = RandomGenerator.intRangeRandom((int)(minRou*baseState.getNodeNumber()), Math.min(baseState.getNodeNumber(), (int)(maxRou*baseState.getNodeNumber())));
    ArrayOperator.shiftSelf(baseState.getIArray(), realMu, true);
    return true;
  }
}
