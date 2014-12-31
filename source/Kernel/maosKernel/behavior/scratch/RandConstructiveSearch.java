/**
 * Description: Construction an state at random in given search space
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005    
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.scratch;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;

public class RandConstructiveSearch extends AbsConstructiveSearch {
  private AbsSearchSpace searchSpace;

  protected void setRootInfo(AbsLandscape landscape) {
    this.searchSpace = landscape.getSearchSpace();
  }

  public boolean generate(EncodedState trialState) {
    trialState.setSearchState(searchSpace.getRandomState());
    return true;
  }
}
