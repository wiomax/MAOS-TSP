/**
 * Description: The execution of an incumbent search in multiple rounds
 *   it terminates if no further improvement on the incumbent state occurs
 *   for Li rounds.
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

public class ImpRepeatLocalSearch extends AbsRepeatLocalSearch  {
  private int LI = 1;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("LI", LI));
  }
  
  public void shortcutInit() throws Exception {
    super.shortcutInit();
    LI = TypeConverter.toInteger(getValue("LI"));
  }

  public boolean repeatedLS(AbsImplicitLocalSearch kernelLS) {
    boolean isValid = false;
    int counts = 0;
    while(true) {
      if (kernelLS.localSearch()) {
        isValid = true;
      } else {
        break;
      }
      if (kernelLS.getStateQualityImprovingFlag()) {
        counts = 0;
      } else {
        counts++;
        if (counts>=LI) {
          break;
        }
      }
    }
    return isValid;
  }
}
