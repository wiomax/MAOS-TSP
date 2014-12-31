/**
 * Description: provide the information for node-type 1-move. The node
 *   and other node(s) may be changed.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.onemove;

public abstract class AbsNode1MoveModel extends AbsBasic1MoveModel {
  public AbsNode1MoveModel() {}
  
  public int getBaseValue(int nodeID) {
    return nodeID;
  }

}

