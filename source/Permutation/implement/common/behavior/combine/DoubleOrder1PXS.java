/**
 * Description: The description of double order crossover.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 27, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import Global.basic.data.collection.*;
import Global.methods.RandomGenerator;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.combine.*;

public class DoubleOrder1PXS extends AbsNoQRecombinationSearch {
  protected int startID, xPoint, endID;

  //the locations and corresponding values for referPoint that are not same with basePoint
  private DualIAlienArray lVArray;
  private DualIAlienArray rVArray;

  public DoubleOrder1PXS() {}

  protected void setRootInfo(AbsLandscape landscape) {
    int nodeNumber = landscape.getSearchSpace().getNodeNumber();
    lVArray = new DualIAlienArray(nodeNumber);
    rVArray = new DualIAlienArray(nodeNumber);
  }

  protected void caSelection(SearchState basePoint, SearchState referPoint){
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

  protected boolean initSelectedIDs(SearchState referPoint) {
    if(endID-startID<2) return false;
    xPoint = RandomGenerator.intRangeRandom(startID+1, endID-1);
    lVArray.clear();
    rVArray.clear();
    for (int i=startID; i<xPoint; i++) {
      lVArray.addElement(referPoint.getValueAt(i));
    }
    for (int i=xPoint; i<endID; i++) {
      rVArray.addElement(referPoint.getValueAt(i));
    }
    return true;
  }
  //Single Cycle X (SCX, or CX-1)
  protected boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
    caSelection(basePoint, referPoint);
    if (!initSelectedIDs(referPoint)) return false;
    trialPoint.importSearchState(basePoint);
    orderBehavior(trialPoint, basePoint, lVArray, startID);
    orderBehavior(trialPoint, basePoint, rVArray, xPoint);
    return true;
  }
  
  private void orderBehavior(SearchState trialPoint, SearchState basePoint, IAlienICollectionEngine referElements, int startID) {
    int remainCount = 0;
    int nodeNumber = trialPoint.getNodeNumber();
    int remainNumber = referElements.getSize();
    int candidateV;
    for (int i = 0; i < nodeNumber; i++) {
    if (remainCount == remainNumber) break;
    candidateV = basePoint.getValueAt(i);
    if (referElements.getElementID(candidateV) != -1) {
      trialPoint.setValueAt(candidateV, startID+remainCount);
      remainCount++;
    }
  }
  }

}
