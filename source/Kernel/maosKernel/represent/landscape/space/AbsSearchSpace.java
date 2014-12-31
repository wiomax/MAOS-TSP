/**
 * Description: provide the information for the search space
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.represent.landscape.space;

import Global.basic.data.collection.*;

public abstract class AbsSearchSpace extends AbsPureSpace implements INodeNumberEngine {
  public int spaceFlag = 0;
  
  public SearchState getNullState() {
    return new SearchState(this.getNodeNumber());
  }

  abstract public SearchState getRandomState();
}

