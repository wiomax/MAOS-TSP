/**
 * Description: provide the executing order information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.orderSel;

import maosKernel.represent.knowledge.*;
import maosKernel.represent.landscape.*;

public abstract class AbsOrderSelectModel extends AbsKnowledgeElement {
  public AbsOrderSelectModel() {}
  
  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    setNodeNumber(landscape.getNodeNumber());
  }
  
  abstract protected void setNodeNumber(int nodeNumber);

  abstract public int[] getExecutingOrder();
}

