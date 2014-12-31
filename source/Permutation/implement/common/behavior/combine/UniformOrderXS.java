/**
 * Description: Two point crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 05, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import Global.methods.*;

import Global.basic.nodes.utilities.*;

public class UniformOrderXS extends AbsPointOrderXS {
  private double baseRatio = 0.5;

  public UniformOrderXS() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("baseRatio", baseRatio));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    baseRatio = TypeConverter.toDouble(getValue("baseRatio"));
  }

  protected boolean blindSelection(boolean[] selFlags) {
    int nodeNumber = selFlags.length;
    for (int i=0; i<nodeNumber; i++) {
      if (Math.random()<baseRatio) selFlags[i] = true;
    }
    return true;
  }
}
