/**
 * Description: The execution of an incumbent search in multiple rounds
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 *
 * @version 1.0
 */

package maosKernel.behavior.greedy.meta;

import Global.basic.nodes.utilities.*;
import Global.methods.*;

import maosKernel.behavior.greedy.*;

public class FixRepeatLocalSearch extends AbsRepeatLocalSearch {
  private int Lc = 1;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("Lc", Lc));
  }
  
  public void shortcutInit() throws Exception {
    super.shortcutInit();
    Lc = TypeConverter.toInteger(getValue("Lc"));
  }
  
  public boolean repeatedLS(AbsImplicitLocalSearch kernelLS) {
    boolean isValid = false;

    for (int i=0; i<Lc; i++) {
      if (kernelLS.localSearch()) {
        isValid = true;
      }
    }
    return isValid;
  }
}
