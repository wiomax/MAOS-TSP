/**
 * Description: The description of recombination search (XS), which
 * is similar to a crossover behavior
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */
package maosKernel.behavior.combine;

import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;

public abstract class AbsNoQRecombinationSearch extends AbsRecombinationSearch {

  abstract protected boolean generate(SearchState trialState, SearchState baseState, SearchState referState);

  public boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    return generate(trialState.getSearchState(), baseState.getSearchState(), referState.getSearchState());
  }

}
