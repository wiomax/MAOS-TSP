/**
 * Description: The connection topology: each node has fixed number of connected nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 11, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import Global.methods.*;

public abstract class AbsEdgeTopology extends AbsCycleTopology {
  private IArray[] connectedIDsArray;
  protected int locNumber = -1;

  //temp
  boolean isCentralized = false;
  
  public AbsEdgeTopology() {}

  protected void internalBasicInit(int nodeNumber) {
    connectedIDsArray = new IArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      connectedIDsArray[i] = new IArray(nodeNumber);
    }
    locNumber = initLocNumber(nodeNumber);
    if (locNumber==nodeNumber) isCentralized= true; 
  }

  public boolean isCentralized() {
    return isCentralized;
  }

  abstract protected int initLocNumber(int nodeNumber);

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return connectedIDsArray[nodeID];
  }

  protected void innerInitTopology() {
    int nodeNumber = getNodeNumber();
    for (int i=0; i<nodeNumber; i++) {
      RandomGenerator.randomDistinctSelection(connectedIDsArray[i].getValueArray(), nodeNumber, locNumber);
      connectedIDsArray[i].setSize(locNumber);
    }
  }
}
