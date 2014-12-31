/**
 * Description: The description of incumbent search with few moves and few consideration on quality information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.incumbent;

import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;

public abstract class AbsPerturbationSearch extends AbsIncumbentSearch {
  abstract protected boolean generate(SearchState baseState);
  
  public boolean generate(EncodedState baseState) {
    return generate(baseState.getSearchState());
  }
}
