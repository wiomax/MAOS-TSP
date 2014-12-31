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
 *
 */

package implement.common.behavior.combine;

import java.util.*;

public abstract class AbsOnePointOrderXS extends AbsPointOrderXS {
  protected boolean isForward;
  protected int xPoint;

  public AbsOnePointOrderXS() {}

  //obtains xPoint and isForward flag
  protected abstract boolean initSelectedIDs(int nodeNumber);

  private void baseSelection(boolean[] selFlags, int xPoint, boolean isForward) {
    if (isForward) {
      Arrays.fill(selFlags, 0, xPoint, true);
    } else {
      Arrays.fill(selFlags, xPoint, selFlags.length, true);
    }
  }

  protected boolean blindSelection(boolean[] selFlags) {
    if (!initSelectedIDs(selFlags.length)) return false;
    baseSelection(selFlags, xPoint, isForward);
    return true;
  }
}
