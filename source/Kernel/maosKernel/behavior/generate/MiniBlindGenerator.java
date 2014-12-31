/**
 * Description: The description of blind generating operator for achieve an trial state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.generate;

import Global.basic.nodes.utilities.*;
import maosKernel.memory.*;
import maosKernel.behavior.scratch.*;

public class MiniBlindGenerator extends AbsMiniGenerator {
  private AbsScratchSearch scratchSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("scratchSearch", scratchSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    scratchSearch = (AbsScratchSearch)getValue("scratchSearch");
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    return scratchSearch.generate(trialState);
  }
}
