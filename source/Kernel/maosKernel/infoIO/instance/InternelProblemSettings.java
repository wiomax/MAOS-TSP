/**
 * Description: provide the internal problem settings
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 08, 2008
 *
 * @version M01.00.01
 * @since M01.00.01
 */

package maosKernel.infoIO.instance;

import Global.basic.nodes.*;

public class InternelProblemSettings extends UtilSetNode {

  public InternelProblemSettings() {
  }

  public String getRepresentName() {
    String name = "";
    for (int i=0; i<super.getUtilitiesSize(); i++) {
      name +=super.getUtilityAt(i).getValue();
      if (i<super.getUtilitiesSize()-1) {
        name+="_";
      }
    }
    return name;
  }

}

