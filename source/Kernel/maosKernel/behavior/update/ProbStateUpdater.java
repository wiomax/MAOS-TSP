/**
 * Description: Updating A as B only as state B have noworse goodness value.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.update;

import Global.methods.*;
import Global.basic.nodes.utilities.*;

import maosKernel.memory.*;

public class ProbStateUpdater extends BetterStateUpdater {
  private double pDirect = 1;

  public ProbStateUpdater() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("pDirect", pDirect));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    pDirect = TypeConverter.toDouble(getValue("pDirect"));
  }

  protected boolean isAccepted(EncodedState basicState, EncodedState inputState) {
    if (super.isAccepted(basicState, inputState)) return true;
    return Math.random()<pDirect;
  }
}
