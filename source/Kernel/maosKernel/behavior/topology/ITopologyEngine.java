/**
 * Description: The connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 11, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;

public interface ITopologyEngine {
  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID);
}
