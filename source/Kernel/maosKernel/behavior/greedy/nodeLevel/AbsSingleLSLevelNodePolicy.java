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

package maosKernel.behavior.greedy.nodeLevel;

import Global.methods.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.behavior.greedy.onemove.*;
import maosKernel.behavior.greedy.referSel.*;

public abstract class AbsSingleLSLevelNodePolicy extends AbsVLSLevelNodePolicy {
  
  public AbsSingleLSLevelNodePolicy() {}

  protected double moveNode(int nodeID, AbsBasic1MoveModel basic1Mover, AbsReferSelectModel referSelector) {
    int newReferV = IBasic1MoveModelEngine.INVALID_V;
    double newReferQ = ICalcGlobalCostEngine.WOSRTVALUE;
    int elemSize = basic1Mover.getElementNumberAt(nodeID);
    int startV = RandomGenerator.intRangeRandom(elemSize);
    int referV;
    int baseV = basic1Mover.getBaseValue(nodeID);
    double referQ;
    referSelector.initModel();
    for (int i=0; i<elemSize; i++) {
      referV = (i+startV)%elemSize;
      if (referV!=baseV) {
        referQ = basic1Mover.getRelativeQuality(nodeID, referV);
        if (isValidReferV(nodeID, referV, referQ) && referSelector.isAcceptableQuality(referQ)) {
          newReferV = referV;
          newReferQ = referQ;
          if (referSelector.isFinished()) break;
        }
      }
    }
    if (newReferV!=IBasic1MoveModelEngine.INVALID_V) {
      preMove(nodeID, baseV, newReferQ);
      basic1Mover.actualMove(nodeID, newReferV);
    }
    return newReferQ;
  }
  
  abstract protected boolean isValidReferV(int nodeID, int referV, double deltaQuality);

  abstract protected void preMove(int nodeID, int baseV, double deltaQuality);
  
}
