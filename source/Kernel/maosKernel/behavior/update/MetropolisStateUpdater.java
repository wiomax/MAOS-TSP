/**
 * Description: Updating A as B according to Metropolis algorithm.
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

public class MetropolisStateUpdater extends BetterStateUpdater {
  private double temperature = 1;

  public MetropolisStateUpdater() {
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("temperature", temperature));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    temperature = TypeConverter.toDouble(getValue("temperature"));
  }

  protected boolean isAccepted(EncodedState basicState, EncodedState inputState) {
    if (super.isAccepted(basicState, inputState)) return true;
    return (Math.exp((basicState.getEncodeInfo()-inputState.getEncodeInfo())/temperature)>Math.random());
  }
}
