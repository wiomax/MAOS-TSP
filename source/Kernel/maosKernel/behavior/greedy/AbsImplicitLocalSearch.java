/**
 * Description: The incumbent state is pre-loaded.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 17, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy;

import maosKernel.represent.landscape.space.*;

public abstract class AbsImplicitLocalSearch extends AbsExplicitLocalSearch {
  protected SearchState baseState;
  
  public AbsImplicitLocalSearch() {}

  final public boolean generate(SearchState baseState) {
    setState(baseState);
    return localSearch();
  }

  abstract public boolean localSearch();

  public void setState(SearchState baseState) {
    this.baseState = baseState;
  }

}
