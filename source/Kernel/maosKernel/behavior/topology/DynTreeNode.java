/**
 * Description: provide an node in a dynamic tree
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 17, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;
import Global.basic.data.collection.*;

public class DynTreeNode {
//  public int selfID = -1;
  public int parentID = -1;
  public IDynamicICollectionEngine childIDs;
  
  public void clear() {
    this.parentID = -1;
    this.childIDs.clear();
  }
}

