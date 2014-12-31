/**
 * Description: The interface for get distance between two states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008 
 *
 * @version M01.00.00
 */


package implement.common.knowledge.distance;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;

public class PositionDistanceCalculator implements ICalcDistanceEngine {
  
  public PositionDistanceCalculator() {
  }
   
  // Position-based measure
  public double getDistance(SearchState stateA, SearchState stateB) {
    int nodeNumber = stateA.getNodeNumber();
    int distV = nodeNumber;
    for (int i=0; i<nodeNumber; i++) {
      if (stateA.getValueAt(i)==stateB.getValueAt(i)) {
        distV --;
      }
    }
    return distV;
  }
}
