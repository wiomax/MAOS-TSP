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
 *
 */

package implement.common.behavior.combine;

import java.util.*;

import Global.basic.nodes.utilities.*;
import Global.methods.TypeConverter;

public abstract class AbsTwoPointOrderXS extends AbsPointOrderXS {
  private int[] xPoints = new int[2];
  public double pInside = 0.5;

  public AbsTwoPointOrderXS() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("pInside", pInside));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    pInside = TypeConverter.toDouble(getValue("pInside"));
  }
  
  protected boolean blindSelection(boolean[] selFlags) {
    if (!this.initSelectedIDs(selFlags.length, xPoints)) return false;
    blindSelection(selFlags, xPoints);
    return true;
  }
  protected abstract boolean initSelectedIDs(int nodeNumber, int[] xPoints);

  private void blindSelection(boolean[] selFlags, int[] xPoints) {
    Arrays.sort(xPoints);
    if (Math.random()>pInside) { //outside
      Arrays.fill(selFlags, 0, xPoints[0], true);
      Arrays.fill(selFlags, xPoints[1], selFlags.length, true);
    } else { //inside
      Arrays.fill(selFlags, xPoints[0], xPoints[1], true);
    }
  }
}
