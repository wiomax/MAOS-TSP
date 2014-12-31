/**
 * Description: Two point PTL crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 05, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 * 
 * Reference:
 * [1] Q.-Q. Pan, M. F. Tasgetiren, and Y.-C. Liang, "A discrete 
 * differential evolution algorithm for the permutation flowshop 
 * scheduling problem," in Genetic and Evolutionary Computation 
 * Conference, London, UK, pp. 126-133, 2007.
 *
 */

package implement.common.behavior.combine;

import java.util.*;

import Global.methods.*;
import maosKernel.behavior.combine.*;
import maosKernel.represent.landscape.AbsLandscape;
import maosKernel.represent.landscape.space.SearchState;

public class TwoPointPTLXS extends AbsNoQRecombinationSearch {
  private int[] xPoints = new int[2];
  protected boolean[] remainVFlags;

  public TwoPointPTLXS() {}

  protected void setRootInfo(AbsLandscape landscape) {
    int nodeNumber = landscape.getSearchSpace().getNodeNumber();
    remainVFlags = new boolean[nodeNumber];
  }
  
  protected boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
    int nodeNumber = trialPoint.getNodeNumber();
    RandomGenerator.randomDistinctSelection(nodeNumber, xPoints);
    Arrays.sort(xPoints);
    int length = xPoints[1] - xPoints[0];
    int rLen = nodeNumber-length;
    boolean isLeftSide = RandomGenerator.booleanRandom();
    int startID = 0, uStartID = length;
    if (!isLeftSide) {
      startID = rLen;
      uStartID = 0;
    }
    System.arraycopy(basePoint.getIArray(), xPoints[0], trialPoint.getIArray(), startID, length);
    Arrays.fill(remainVFlags, true);
    for (int i=xPoints[0]; i<xPoints[1]; i++){
      remainVFlags[basePoint.getValueAt(i)] = false;
    }
    
    int referV;
    for (int i=0; i<nodeNumber; i++) {
      referV = referPoint.getValueAt(i);
      if (remainVFlags[referV]) {
        trialPoint.setValueAt(referV, uStartID);
        uStartID ++;
      }
    }
    return true;
  }
}
