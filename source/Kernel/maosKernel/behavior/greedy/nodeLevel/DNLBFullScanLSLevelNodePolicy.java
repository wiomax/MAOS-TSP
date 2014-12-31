/**
 * Description: full-scan node-level policy with don't look bits
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 05, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import java.util.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.onemove.*;
import maosKernel.behavior.greedy.referSel.*;

public class DNLBFullScanLSLevelNodePolicy extends BasicFullScanLSLevelNodePolicy {
  private boolean[] dnlbArray;
  
  public DNLBFullScanLSLevelNodePolicy() {}

  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    dnlbArray = new boolean[landscape.getNodeNumber()];
  }

  public void setState(SearchState baseState) {
    super.setState(baseState);
    Arrays.fill(dnlbArray, false);
  }
    
  protected double moveNode(int nodeID, AbsBasic1MoveModel basic1Mover, AbsReferSelectModel referSelector) {
    if (dnlbArray[nodeID]) return ICalcGlobalCostEngine.WOSRTVALUE;
    double sumQuality = super.moveNode(nodeID, basic1Mover, referSelector);
    if (sumQuality==ICalcGlobalCostEngine.WOSRTVALUE) {
      dnlbArray[nodeID] = true;
    }
    return sumQuality;
  }

  protected void premove(int nodeID, int referV) {
    dnlbArray[nodeID] = false;
    dnlbArray[referV] = false;
  }
}
