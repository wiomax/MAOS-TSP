/**
 * Description: The description of fusion search (Recombination+Local Search)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 * 
 * @ Reference:
 * [1] Xiao-Feng Xie, Jiming Liu. Graph coloring by multiagent fusion search. 
 *     Journal of Combinatorial Optimization, In Press.
 */

package maosKernel.behavior.combine;

import maosKernel.memory.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.greedy.*;

public class FusionSearch extends AbsRecombinationSearch {
  private AbsRecombinationSearch recombinationSearch;
  private AbsExplicitLocalSearch localSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("recombinationSearch", recombinationSearch));
    initUtility(new BasicUtility("localSearch", localSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    recombinationSearch = (AbsRecombinationSearch)(getValue("recombinationSearch"));
    localSearch = (AbsExplicitLocalSearch)getValue("localSearch");
  }

  public boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    if (!recombinationSearch.generate(trialState, baseState, referState)) return false;
    localSearch.generate(trialState);
    return true;
  }
}
