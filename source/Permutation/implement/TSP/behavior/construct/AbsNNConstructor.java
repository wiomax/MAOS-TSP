/**
 * Description: the constructor including the neighborhood information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.construct;

import Global.basic.nodes.utilities.*;

import maosKernel.behavior.scratch.*;

import implement.TSP.behavior.neighbor.*;

public abstract class AbsNNConstructor extends AbsConstructiveSearch {
  protected AbsNeighborManager neighborEngine;

  public AbsNNConstructor() {
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("neighborEngine", neighborEngine));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    neighborEngine = (AbsNeighborManager)(getValue("neighborEngine"));
  }
}
