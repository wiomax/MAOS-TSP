/**
 * Description: The description of combined scratch search
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    May 20, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.scratch;

import maosKernel.memory.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.incumbent.*;

public class LinkedScratchSearch extends AbsScratchSearch {
  private AbsScratchSearch scratchSearch;
  private AbsIncumbentSearch incumbentSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("scratchSearch", scratchSearch));
    initUtility(new BasicUtility("incumbentSearch", incumbentSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    scratchSearch = (AbsScratchSearch)(getValue("scratchSearch"));
    incumbentSearch = (AbsIncumbentSearch)getValue("incumbentSearch");
  }

  public boolean generate(EncodedState trialState) {
    if (!scratchSearch.generate(trialState)) return false;
    return incumbentSearch.generate(trialState);
  }
}
