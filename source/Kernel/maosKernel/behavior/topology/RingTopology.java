/**
 * Description: The RandRing connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 08, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import Global.methods.*;

public class RingTopology extends AbsCycleTopology {
  private int[] connectedIDsArray;
  private int[] tempIDsArray;
  private IArray tempArray = new IArray(1);

  public RingTopology() {
  }

  protected void internalBasicInit(int nodeNumber) {
    tempIDsArray = new int[nodeNumber];
    connectedIDsArray = new int[nodeNumber];
  }

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    tempArray.clear();
    tempArray.addElement(connectedIDsArray[nodeID]);
    return tempArray;
  }

  public void innerInitTopology() {
    RandomGenerator.randomDistinctSelection(tempIDsArray);
    int nodeNumber = getNodeNumber();
    for (int i=0; i<nodeNumber; i++) {
      connectedIDsArray[tempIDsArray[i]] = tempIDsArray[(i+1)%nodeNumber];
    }
  }
}
