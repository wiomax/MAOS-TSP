/**
 * Description: For any information element
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 28, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.represent.information;

import Global.basic.nodes.*;

public abstract class AbsInformationElement extends UtilSetNode implements ITrialInitEngine {
  public AbsInformationElement(){}
  
  public void initTrial() {
    int utilSize = this.getUtilitiesSize();
    for (int i=0; i<utilSize; i++) {
      Object util = this.getValueAt(i);
      if (util instanceof AbsInformationElement) {
        ((AbsInformationElement)util).initTrial();
      }
    }
  }

}
