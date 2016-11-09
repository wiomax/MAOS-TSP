/**
 * Description: The description of blind crossover (mutated by cycles on baseState)
 *  with local search immediately.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 08, 2005
 * Xiaofeng Xie    Jul 16, 2006
 * Xiaofeng Xie    Aug 01, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import java.util.*;
import Global.basic.data.collection.*;

import maosKernel.represent.landscape.space.*;

public class CycleLib {
  //the locations and corresponding values for referPoint that are not same with basePoint
  private DualIAlienArray locArray;
  private DualIAlienArray valArray;

  /****************************************************/
  private int maxSize = Integer.MAX_VALUE;
  private Vector<int[]> cycleLib = new Vector<int[]>();
  private IArray tempCycleArray = null;

  public CycleLib(int nodeNumber) {
    locArray = new DualIAlienArray(nodeNumber+1);
    valArray = new DualIAlienArray(nodeNumber+1);
    tempCycleArray= new IArray(nodeNumber);
  }

  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }

  public int getMaxSize() {
    return maxSize;
  }

  public int[] getCycleAt(int index) {
    return (int[])cycleLib.elementAt(index);
  }

  public int getCycleNumber() {
    return cycleLib.size();
  }

  public void initCycles(SearchState basePoint, SearchState referPoint, IAlienICollectionEngine sameElemIDArray) {
    preProcess(referPoint, sameElemIDArray);
    findCycles(basePoint);
  }

  private void findCycles(SearchState basePoint) {
    cycleLib.clear();
    while(cycleLib.size()<maxSize) {
      int[] newCycle = findNextCycle(basePoint);
      if (newCycle==null) break;
      if (newCycle.length>1) {
        cycleLib.add(newCycle);
      }
    }
  }

  private int[] findNextCycle(SearchState basePoint) {
    if (valArray.getSize()<1) return null;
    tempCycleArray.clear();
    int refVal = -1;
    int refLoc = -1;
    int selVal = valArray.getRandomElement();
    while (true) {
      refLoc = locArray.getElementAt(valArray.getElementID(selVal));
      tempCycleArray.addElement(refLoc);
      valArray.removeElement(selVal);
      locArray.removeElement(refLoc);
      refVal = basePoint.getValueAt(refLoc);
      if (valArray.getElementID(refVal) != -1) {
        selVal = refVal;
      } else {
        break;
      }
    }
    return tempCycleArray.getClonedValues();
  }

  public void generate(SearchState trialPoint, IAlienICollectionEngine sameElemIDArray) {
    preProcess(trialPoint, sameElemIDArray);
    for (int i=0; i<locArray.getSize(); i++) {
      int rndV = valArray.getRandomElement();
      trialPoint.setValueAt(rndV, locArray.getElementAt(i));
      valArray.removeElement(rndV);
    }
  }

  //only take the locations with different elements
  private void preProcess(SearchState selPoint, IAlienICollectionEngine sameElemIDArray) {
    int nodeNumber = selPoint.getNodeNumber();
    valArray.clear();
    locArray.clear();
    for (int i=0; i<nodeNumber; i++) {
      if (sameElemIDArray.getElementID(i)==-1) {
        valArray.addElement(selPoint.getValueAt(i));
        locArray.addElement(i);
      }
    }
  }
}
