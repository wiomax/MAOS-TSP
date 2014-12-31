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
import Global.methods.*;
import Global.util.*;

public class QrandOrderSelectModel extends AbsOrderSelectModel {
  private QuasiRandomIndicesGenerator qriManager;
  private int quasiNumber = 100;

  public QrandOrderSelectModel() {}
  
  protected void setNodeNumber(int nodeNumber) {
    qriManager = new QuasiRandomIndicesGenerator(nodeNumber, nodeNumber, quasiNumber);
  }

  public int[] getExecutingOrder() {
    return qriManager.randomDistinctSelection();
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("quasiNumber", quasiNumber));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    quasiNumber = TypeConverter.toInteger(getValue("quasiNumber"));
  }
}

