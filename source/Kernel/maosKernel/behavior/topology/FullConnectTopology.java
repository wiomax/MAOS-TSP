/**
 * Description: The full-connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 4, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;

public class FullConnectTopology extends AbsTopology {
  private IArray connectedArray;

  public FullConnectTopology() {}

  final public boolean isCentralized() {
    return true;
  }

  protected void internalBasicInit(int nodeNumber) {
    connectedArray = new IArray(nodeNumber);
    for (int i=0; i<this.getNodeNumber(); i++) {
      connectedArray.addElement(i);
    }
  }
  
  public void innerInitTopology() {}
  

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return connectedArray;
  }
}
