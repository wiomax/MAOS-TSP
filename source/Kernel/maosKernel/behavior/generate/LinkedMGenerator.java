/**
 * Description: The description of generating operator for achieve an trial state in choose one from
 * two generators in given probability.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 09, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.generate;

import Global.basic.nodes.utilities.*;
import maosKernel.memory.*;
import maosKernel.behavior.incumbent.*;

public class LinkedMGenerator extends AbsMiniGenerator {
  private AbsMiniGenerator baseGenerator;
  private AbsIncumbentSearch incumbentSearch;

  public LinkedMGenerator() {}
  
  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("baseGenerator", baseGenerator));
    initUtility(new BasicUtility("incumbentSearch", incumbentSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    baseGenerator = (AbsMiniGenerator)(getValue("baseGenerator"));
    incumbentSearch = (AbsIncumbentSearch)getValue("incumbentSearch");
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    boolean isGenerated = baseGenerator.generateBehavior(trialState, baseState, referEngine);
    boolean isIncumbent = incumbentSearch.generate(trialState);
    return isGenerated||isIncumbent;
  }

}
