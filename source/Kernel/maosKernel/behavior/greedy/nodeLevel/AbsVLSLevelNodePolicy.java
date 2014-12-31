/**
 * Description: The node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import Global.basic.nodes.utilities.*;
import maosKernel.represent.landscape.quality.ICalcGlobalCostEngine;
import maosKernel.represent.landscape.quality.IGetGlobalCostEngine;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.onemove.*;
import maosKernel.behavior.greedy.referSel.*;

public abstract class AbsVLSLevelNodePolicy extends AbsLSLevelNodePolicy {
  private AbsBasic1MoveModel basic1Mover = null;
  private AbsReferSelectModel referSelector;
  
  public AbsVLSLevelNodePolicy() {}

  public void setState(SearchState baseState) {
    get1Mover().setState(baseState);
  }
  
  protected AbsBasic1MoveModel get1Mover() {
    return this.basic1Mover;
  }
  
  public double encodeGlobal() {
    if (basic1Mover instanceof IGetGlobalCostEngine) {
      return ((IGetGlobalCostEngine)basic1Mover).encodeGlobal();
    } else {
      return ICalcGlobalCostEngine.WOSRTVALUE;
    }
  }
  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("referSelector", referSelector));
    initUtility(new BasicUtility("basic1Mover", basic1Mover));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    referSelector = (AbsReferSelectModel)(getValue("referSelector"));
    basic1Mover = (AbsBasic1MoveModel)(getValue("basic1Mover"));
  }

  public double moveNode(int nodeID) {
    return moveNode(nodeID, basic1Mover, referSelector);
  }
  
  abstract protected double moveNode(int nodeID, AbsBasic1MoveModel basic1Mover, AbsReferSelectModel referSelector);
}
