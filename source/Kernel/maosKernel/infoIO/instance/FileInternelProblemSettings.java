/**
 * Description: provide the internal problem settings loading an instance
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 08, 2008
 *
 * @version M01.00.01
 * @since M01.00.01
 */

package maosKernel.infoIO.instance;

import Global.methods.*;
import Global.basic.nodes.utilities.*;

public class FileInternelProblemSettings extends InternelProblemSettings {
  protected String instanceName = "";

  public FileInternelProblemSettings() {
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("Problem", instanceName));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    instanceName = TypeConverter.toString(getValue("Problem"));
  }
}

