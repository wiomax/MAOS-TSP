/**
* Description: The connection topology: each node has fixed number of connected nodes
 *   -> the number is determined by locNumber parameter
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 11, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import Global.basic.nodes.utilities.*;
import Global.methods.*;

public class FixEdgeTopology extends AbsEdgeTopology {

  public FixEdgeTopology() {}

  protected int initLocNumber(int nodeNumber) {
    return TypeConverter.getNaturalValue(locNumber,nodeNumber);
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("locNumber", locNumber));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    locNumber = TypeConverter.toInteger(getValue("locNumber"));
  }
}
