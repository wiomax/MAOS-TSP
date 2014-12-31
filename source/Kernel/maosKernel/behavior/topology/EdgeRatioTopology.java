/**
 * Description: The connection topology: each node has fixed number of connected nodes
 *   -> the number is determined by locRatio*nodeNumber
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 19, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.nodes.utilities.*;
import Global.methods.*;

public class EdgeRatioTopology extends AbsEdgeTopology {
  private double locRatio = 1;

  public EdgeRatioTopology() {}

  protected int initLocNumber(int nodeNumber) {
    if (locRatio<0||locRatio>1) locRatio = 1;
    return (int)Math.rint(locRatio*nodeNumber);
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("locRatio", locRatio));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    locRatio = TypeConverter.toDouble(getValue("locRatio"));
  }
}
