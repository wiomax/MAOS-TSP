/**
 * Description: For any knowledge element
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 06, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.represent.knowledge;

import maosKernel.represent.landscape.*;
import maosKernel.represent.information.*;

public abstract class AbsKnowledgeElement extends AbsInformationElement implements ISetLandscapeInfoEngine {
  private boolean isRootLandscapeInfoOK = false;

  //init only once
  final public void setInfo(AbsLandscape landscape){
    if (isRootLandscapeInfoOK) return;
    //set leaf info
    for(int i=0; i<super.getUtilSize(); i++) {
      Object object = super.getUtilityAt(i).getValue();
      if (object!=null && object instanceof AbsKnowledgeElement) {
        ((ISetLandscapeInfoEngine)object).setInfo(landscape);
      }
    }
    setRootInfo(landscape);
    isRootLandscapeInfoOK = true;
  }

  protected void setRootInfo(AbsLandscape landscape){}
}
