/**
 * Description: The description of linked incumbent search 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.meta;

import maosKernel.behavior.incumbent.*;
import maosKernel.behavior.greedy.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.space.SearchState;
import Global.basic.nodes.utilities.*;

public class KickMoveLocalSearch extends AbsExplicitLocalSearch {
  private AbsPerturbationSearch purturbSearch;
  private AbsExplicitLocalSearch localSearch;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("purturbSearch", purturbSearch));
    initUtility(new BasicUtility("localSearch", localSearch));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    purturbSearch = (AbsPerturbationSearch)(getValue("purturbSearch"));
    localSearch = (AbsExplicitLocalSearch)getValue("localSearch");
  }

  public boolean generate(EncodedState baseState) {
    boolean isP = purturbSearch.generate(baseState);
    boolean isL = localSearch.generate(baseState);
    return isP || isL;
  }
  
  //no usage
  protected boolean generate(SearchState baseState) {
    return true;
  }
}
