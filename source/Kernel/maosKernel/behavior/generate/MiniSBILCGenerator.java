/**
 * Description: The description of generating operator for achieve an trial state
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
import maosKernel.behavior.complex.*;

public class MiniSBILCGenerator extends AbsMiniGenerator {
  private AbsComplexSearch complexSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("complexSearch", complexSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    complexSearch = (AbsComplexSearch)getValue("complexSearch");
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    return complexSearch.generate(trialState, baseState, referEngine);
  }
}
