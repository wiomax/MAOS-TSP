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
import maosKernel.behavior.pick.*;
import maosKernel.behavior.combine.*;

public class MiniSOnlyGenerator extends AbsMiniGenerator {
  private AbsStatePicker statePicker = new RandStatePicker();
  private AbsRecombinationSearch recombinationSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("statePicker", statePicker));
    initUtility(new BasicUtility("recombinationSearch", recombinationSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    statePicker = (AbsStatePicker)getValue("statePicker");
    recombinationSearch = (AbsRecombinationSearch)getValue("recombinationSearch");
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    statePicker.setBaseState(baseState);
    return recombinationSearch.generate(trialState, statePicker.pickBehavior(referEngine), statePicker.pickBehavior(referEngine));
  }
}
