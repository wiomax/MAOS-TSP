/**
 * Description: The dynamic connection topology in cycles
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 28, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.define.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;

public abstract class AbsCycleTopology extends AbsTopology {
  private int intervalCycle = GlobalValue.MAXINTEGER;

  //temp value
  private int execCycle = 1;

  public AbsCycleTopology() {}

  public void initTrial() {
    execCycle = 1;
    super.initTrial();
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("intervalCycle", intervalCycle));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    intervalCycle = TypeConverter.toInteger(getValue("intervalCycle"));
    if (intervalCycle<0) intervalCycle= GlobalValue.MAXINTEGER;
  }

  public void adjustStatus() {
    if (execCycle > 0 && execCycle<intervalCycle) {
      execCycle ++;
    } else {
      execCycle = 1;
      innerInitTopology();
    }
  }
}
