/**
 * Description: full-scan node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 * 
 * @Reference
 * [1] Taillard ED. FANT: Fast ant system. Technical Report, IDSIA-46-98, Lugano: IDSIA, 1998.
 * [2] Thomas Stutzle, Marco Dorigo. ACO algorithms for the quadratic assignment problem.
 *     New Ideas in Optimization, 1999
 */

package maosKernel.behavior.greedy.nodeLevel;

import Global.methods.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.behavior.greedy.onemove.*;
import maosKernel.behavior.greedy.referSel.*;

public class BasicFullScanLSLevelNodePolicy extends AbsVLSLevelNodePolicy {
  
  public BasicFullScanLSLevelNodePolicy() {}

  protected double moveNode(int nodeID, AbsBasic1MoveModel basic1Mover, AbsReferSelectModel referSelector) {
    double sumQuality = 0;
    int newReferV = IBasic1MoveModelEngine.INVALID_V;
    double newReferQ = ICalcGlobalCostEngine.WOSRTVALUE;

    int elemSize = basic1Mover.getElementNumberAt(nodeID);
    int startV = RandomGenerator.intRangeRandom(elemSize);
    int referV;
    int baseV = basic1Mover.getBaseValue(nodeID);
    double referQ;
    referSelector.initModel();
    for (int i=0; i<elemSize; i++) {
      referV = (i+startV+elemSize)%elemSize;
      if (referV!=baseV && isValidReferV(nodeID, referV)) {
        referQ = basic1Mover.getRelativeQuality(nodeID, referV);
        if (referSelector.isAcceptableQuality(referQ)) {
          newReferV = referV;
          newReferQ = referQ;
          if (referSelector.isFinished()) {
            if (sumQuality==ICalcGlobalCostEngine.WOSRTVALUE) sumQuality = 0;
            premove(nodeID, newReferV);
            basic1Mover.actualMove(nodeID, newReferV);
            sumQuality += newReferQ;
            referSelector.initModel();
         } else {
           invalidReferV(nodeID, referV);
         }
        }
      }
    }
    return sumQuality;
  }
  
  protected void premove(int nodeID, int referV) {}
  
  protected boolean isValidReferV(int nodeID, int referV) {
    return true;
  }

  protected void invalidReferV(int nodeID, int referV) {}
}
