/**
 * Description: Common positions avoided One point crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 20, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import maosKernel.represent.landscape.space.SearchState;
import Global.methods.*;

public class CAOnePointOrderXS extends AbsOnePointOrderXS {
  protected int startID, endID;

  public CAOnePointOrderXS() {}

  protected void initSelection(SearchState basePoint, SearchState referPoint){
    int nodeNumber = basePoint.getNodeNumber();

    startID = 0;
    for (int i=0; i<nodeNumber; i++) {
      if (basePoint.getValueAt(i)==referPoint.getValueAt(i)) {
        startID ++;
      } else {
        break;
      }
    }
    endID = nodeNumber; 
    for (int i=nodeNumber-1; i>=startID; i--) {
      if (basePoint.getValueAt(i)==referPoint.getValueAt(i)) {
        endID --;
      } else {
        break;
      }
    }
  }

   protected boolean initSelectedIDs(int nodeNumber) {
    if(endID-startID<2) return false;
    xPoint = RandomGenerator.intRangeRandom(startID+1, endID-1);
    isForward = RandomGenerator.booleanRandom();
    return true;
  }
}
