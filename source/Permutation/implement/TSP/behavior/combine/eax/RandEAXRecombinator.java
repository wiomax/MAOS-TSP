/**
 * Description: The EAX with a randomized AB-cycle selection.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.combine.eax;

import Global.methods.*;
import Global.basic.data.collection.*;

public final class RandEAXRecombinator extends AbsEAXRecombinator {

  public RandEAXRecombinator() {}

  protected void designABSet(IDynamicICollectionEngine activeCycleIDs, int Nmtm, ABCycleLib abCycleLib, int[][] costMatrix) {
    randomDesignABSet(activeCycleIDs, abCycleLib.getSize(), Nmtm);
  }
  private void randomDesignABSet(IDynamicICollectionEngine activeCycleIDs, int cycleSize, int maxTimes) {
    for (int j = 0; j < maxTimes; j++) {
      activeCycleIDs.addElement(RandomGenerator.intRangeRandom(cycleSize));
    }
  }
}
