/**
 * Description: defines some tools.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 07, 2005    For TimeCostCounter: running and cpu time
 * Xiaofeng Xie    Jul 14, 2006    get java version
 *
 * @version 1.0
 */

package Global.util;

import Global.util.timecost.MultiTimeCostCounter;

public class GlobalTools {
  public static MultiTimeCostCounter timeCostCounter = new MultiTimeCostCounter(false);
  public static MultiTimeCostCounter CPUTimeCostCounter = new MultiTimeCostCounter(true);

  public static int getCurrentJavaVerRelationTo(String version) {
    String ver = System.getProperty("java.version");
    return ver.compareTo(version);
  }

}

