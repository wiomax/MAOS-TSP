/**
 * Description: Two point crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 05, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import Global.methods.*;

public class TwoPointOrderXS extends AbsTwoPointOrderXS {
  public TwoPointOrderXS() {}

  protected boolean initSelectedIDs(int nodeNumber, int[] xPoints) {
    RandomGenerator.randomDistinctSelection(nodeNumber, xPoints);
    return true;
  }
}
