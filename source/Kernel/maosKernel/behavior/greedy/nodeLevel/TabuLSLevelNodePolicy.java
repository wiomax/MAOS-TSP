/**
 * Description: TABU node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import Global.basic.nodes.utilities.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.tabu.*;

public class TabuLSLevelNodePolicy extends AbsSingleLSLevelNodePolicy {
  private AbsTabuInfoModel tabuInfo;
  
  public TabuLSLevelNodePolicy() {}
  
  public void setState(SearchState baseState) {
    super.setState(baseState);
    tabuInfo.initTabuInfo();
  }

  protected boolean isValidReferV(int nodeID, int referV, double deltaQuality) {
    return tabuInfo.isNotForbidden(nodeID, referV, deltaQuality);
  }

  protected void preMove(int nodeID, int baseV, double deltaQuality) {
    tabuInfo.setForbiddenInfo(nodeID, baseV, deltaQuality);
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("tabuInfo", tabuInfo));
  }
  
  public void shortcutInit() throws Exception {
    super.shortcutInit();
    tabuInfo = (AbsTabuInfoModel)(getValue("tabuInfo"));
    tabuInfo.init1MoveModel(super.get1Mover());
  }
}
