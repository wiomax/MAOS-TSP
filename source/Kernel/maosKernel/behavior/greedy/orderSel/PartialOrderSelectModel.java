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

import Global.basic.nodes.utilities.*;
import Global.methods.RandomGenerator;
import Global.methods.TypeConverter;

public class PartialOrderSelectModel extends AbsOrderSelectModel {
  private int[] execOrder = new int[1];
  private int nodeNumber = -1;
  
  public PartialOrderSelectModel() {}
  
  protected void setNodeNumber(int nodeNumber) {
    this.nodeNumber = nodeNumber;
  }

  public int[] getExecutingOrder() {
    RandomGenerator.randomDistinctSelection(nodeNumber, execOrder);
    return execOrder;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("size", execOrder.length));
  }
  
  public void shortcutInit() throws Exception {
    super.shortcutInit();
    execOrder = new int[TypeConverter.toInteger(getValue("size"))];
  }
}

