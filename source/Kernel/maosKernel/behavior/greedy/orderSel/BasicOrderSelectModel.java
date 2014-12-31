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

public class BasicOrderSelectModel extends AbsOrderSelectModel {
  private int[] execOrder;
  public BasicOrderSelectModel() {}
  
  protected void setNodeNumber(int nodeNumber) {
    execOrder = new int[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      execOrder[i] = i;
    }
  }

  public int[] getExecutingOrder() {
    return execOrder;
  }
}

