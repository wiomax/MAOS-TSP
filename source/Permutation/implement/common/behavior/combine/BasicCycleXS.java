/**
 * Description: The description of cycle crossover.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 08, 2005
 * Xiaofeng Xie    Jul 16, 2006    MAOS-ALL Beta 1.1.006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.common.behavior.combine;

import Global.basic.data.collection.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.combine.*;

public class BasicCycleXS extends AbsNoQRecombinationSearch {

  //the locations and corresponding values for referPoint that are not same with basePoint
  private DualIAlienArray locArray;
  private DualIAlienArray valArray;
  private IArray tempCycleArray = null;

  public BasicCycleXS() {}

  protected void setRootInfo(AbsLandscape landscape) {
    int nodeNumber = landscape.getSearchSpace().getNodeNumber();
    locArray = new DualIAlienArray(nodeNumber+1);
    valArray = new DualIAlienArray(nodeNumber+1);
    tempCycleArray= new IArray(nodeNumber);
  }

  private void crossByIArray(SearchState trialPoint, SearchState referPoint, IBasicICollectionEngine indices) {
    int length = indices.getSize();
    int valueAtI;
    for (int i=0; i<length; i++) {
      valueAtI = indices.getElementAt(i);
      trialPoint.setValueAt(referPoint.getValueAt(valueAtI), valueAtI);
    }
  }
 
  protected void preProcess(SearchState basePoint, SearchState referPoint) {
    int nodeNumber = basePoint.getNodeNumber();
    valArray.clear();
    locArray.clear();
    for (int i=0; i<nodeNumber; i++) {
      if (basePoint.getValueAt(i)!=referPoint.getValueAt(i)) {
        valArray.addElement(referPoint.getValueAt(i));
        locArray.addElement(i);
      }
    }
  }

  private void findOneCycle(IArray cycleArray, SearchState basePoint) {
    if (valArray.getSize()==0) return;
    cycleArray.clear();
    int refVal = -1;
    int refLoc = -1;
    int selVal = valArray.getRandomElement();
    while (true) {
      refLoc = locArray.getElementAt(valArray.getElementID(selVal));
      cycleArray.addElement(refLoc);
      valArray.removeElement(selVal);
      locArray.removeElement(refLoc);
      refVal = basePoint.getValueAt(refLoc);
      if (valArray.getElementID(refVal) != -1) {
        selVal = refVal;
      } else {
        break;
      }
    }
  }

  //Single Cycle X (SCX, or CX-1)
  protected boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
    preProcess(basePoint, referPoint);
    findOneCycle(tempCycleArray, basePoint);
    if (tempCycleArray.getSize()==0) return false;
    trialPoint.importSearchState(basePoint);
    crossByIArray(trialPoint, referPoint, tempCycleArray);
    return true;
  }
}
