/**
 * Description: The interface for get distance between two states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008 
 *
 * @version M01.00.00
 */


package implement.common.knowledge.distance;


import Global.basic.data.collection.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import implement.common.knowledge.*;

public class UniDirectionalAdjacencyDistanceCalculator implements ICalcDistanceEngine {
  
 public double getDistance(SearchState stateA, SearchState stateB) {
    IDynamicICollectionEngine sameIDArray = BasicCollection.initDualIAlienArray(stateA.getNodeNumber());
    SequencesOperation.getSharedBlocks(sameIDArray, stateA, stateB);
    return sameIDArray.getSize();
  }
}
