/**
 * Description: The description of local search (LS) in two levels, 
 *   including the local (node) level and the round level.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.instance;

import Global.basic.nodes.utilities.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.*;
import maosKernel.behavior.greedy.nodeLevel.*;
import maosKernel.behavior.greedy.roundLevel.*;

public class TwoLevelLocalSearch extends AbsImplicitLocalSearch implements IGetGlobalCostEngine {
  private AbsLSLevelNodePolicy nodePolicy;
  private AbsLSLevelRoundPolicy roundPolicy;
  
  private boolean improveFlag = false;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("nodePolicy", nodePolicy));
    initUtility(new BasicUtility("roundPolicy", roundPolicy));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    nodePolicy = (AbsLSLevelNodePolicy)(getValue("nodePolicy"));
    roundPolicy = (AbsLSLevelRoundPolicy)getValue("roundPolicy");
  }
  
  public void setState(SearchState baseState) {
    super.setState(baseState);
    nodePolicy.setState(baseState);
  }

  public boolean getStateQualityImprovingFlag() {
    return improveFlag;
  }

  public double encodeGlobal() {
    if (nodePolicy instanceof IGetGlobalCostEngine) {
      return ((IGetGlobalCostEngine)nodePolicy).encodeGlobal();
    } else {
      return ICalcGlobalCostEngine.WOSRTVALUE;
    }
  }
  
  public boolean localSearch() {
    improveFlag = false;
    double deltaQ = roundPolicy.moveRound(nodePolicy);
    if (deltaQ==ICalcGlobalCostEngine.WOSRTVALUE) {
      return false;
    } else {
      if (deltaQ<0) {
        improveFlag = true;
      }
    }
    return true;
  }
}
