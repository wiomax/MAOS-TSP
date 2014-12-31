/**
 * Description: provide the external problem settings (do not need predefined parameters)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 02, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 * Xiaofeng Xie    Sep 08, 2008 
 *
 * @version M01.00.01
 * @since M01.00.00
 */

package maosKernel.infoIO.setting;

import Global.basic.nodes.*;
import Global.basic.nodes.loader.*;
import maosKernel.infoIO.parser.*;

public class CMDLineProblemSettings extends UtilSetNode {
  private AbsUtilSetNodeLoader utilSetNodeLoader = new CoverUtilSetNodeLoader();
  private AbsUtilSetNodeLoader utilSetNodeSaver = new BracketUtilSetNodeLoader();

  public CMDLineProblemSettings() {
  }

  public String getSummary() {
    return utilSetNodeSaver.saveUtilSetNode(this);
  }

  public void loadContent(String content) {
    this.utilSetNodeLoader.loadUtilSetNode(this, content);
  }

}

