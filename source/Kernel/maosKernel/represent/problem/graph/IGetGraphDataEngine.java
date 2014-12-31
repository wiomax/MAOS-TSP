/**
 * Description: the basic information of an graph
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 17, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */


package maosKernel.represent.problem.graph;

import Global.basic.data.collection.*;

public interface IGetGraphDataEngine extends INodeNumberEngine {
  public boolean isAttacked(int nodeA, int nodeB);
  public int[] getAttackNodesAt(int index);
}
