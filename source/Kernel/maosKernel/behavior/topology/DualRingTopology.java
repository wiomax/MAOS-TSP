/**
 * Description: The RandRing connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 19, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;

public class DualRingTopology extends AbsCycleTopology {
  private int[] tempIDsArray;
  private IArray[] tempArrays;

  public DualRingTopology() {
  }

  protected void internalBasicInit(int nodeNumber) {
    tempIDsArray = new int[nodeNumber];
    tempArrays = new IArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      tempArrays[i] = new IArray(2);
    }
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("intervalCycle", 1));
  }

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return tempArrays[nodeID];
  }

  public void innerInitTopology() {
    RandomGenerator.randomDistinctSelection(tempIDsArray);
    int nodeNumber = getNodeNumber();
    for (int i=0; i<nodeNumber; i++) {
      tempArrays[i].clear();
      tempArrays[i].addElement(tempIDsArray[(i+1)%nodeNumber]);
      tempArrays[i].addElement(tempIDsArray[(i-1+nodeNumber)%nodeNumber]);
    }
  }
}
