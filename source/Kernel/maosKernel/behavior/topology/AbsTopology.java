/**
 * Description: The connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 28, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import maosKernel.behavior.*;

public abstract class AbsTopology extends AbsBehavior {
  private int nodeNumber = -1;

  public AbsTopology() {
  }

  final public void basicInit(int nodeNumber) {
    this.nodeNumber = nodeNumber;
    this.internalBasicInit(nodeNumber);
  }
  
  public boolean isCentralized() {
    return false;
  }

  abstract protected void internalBasicInit(int nodeNumber);

  final public int getNodeNumber() {
    return nodeNumber;
  }
  
  abstract protected void innerInitTopology();

  abstract public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID);

  public void adjustStatus() {}

  public void initTrial() {
    super.initTrial();
    innerInitTopology();
  }
}
