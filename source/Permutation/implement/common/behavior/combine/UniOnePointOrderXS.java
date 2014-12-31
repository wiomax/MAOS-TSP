/**
 * Description: One point crossover
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

public class UniOnePointOrderXS extends AbsOnePointOrderXS {

  public UniOnePointOrderXS() {}

  protected boolean initSelectedIDs(int nodeNumber) {
    int len = RandomGenerator.getAllUniformLength(nodeNumber);
    isForward = RandomGenerator.booleanRandom();
    if (isForward) {
      xPoint = len;
    } else {
      xPoint = nodeNumber - len;
    }
    return true;
  }
}
