/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.roundLevel;

import Global.basic.nodes.utilities.*;
import maosKernel.behavior.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.behavior.greedy.orderSel.*;
import maosKernel.behavior.greedy.nodeLevel.*;

public abstract class AbsLSLevelRoundPolicy extends AbsBehavior implements ILSLevelRoundEngine {
  
  private AbsOrderSelectModel orderPolicy = new BasicOrderSelectModel();
  
  public AbsLSLevelRoundPolicy() {}
  
  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("orderPolicy", orderPolicy));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    orderPolicy = (AbsOrderSelectModel)(getValue("orderPolicy"));
  }

  abstract protected boolean isRoundFinished(double sumDeltaCost);

  public double moveRound(AbsLSLevelNodePolicy nodePolicy) {
    int[] execOrder = orderPolicy.getExecutingOrder();
    boolean isStateChanged = false;
    double oneDeltaCost, sumDeltaCost = 0;
    for(int i=0; i<execOrder.length; i++) {
      oneDeltaCost = nodePolicy.moveNode(execOrder[i]);
      if (oneDeltaCost != ICalcGlobalCostEngine.WOSRTVALUE) {
        isStateChanged = true;
        sumDeltaCost += oneDeltaCost;
        if (isRoundFinished(sumDeltaCost)) break;
      }
    }
    if (isStateChanged) {
      return sumDeltaCost;
    } else {
      return ICalcGlobalCostEngine.WOSRTVALUE;
    }
  }
}
