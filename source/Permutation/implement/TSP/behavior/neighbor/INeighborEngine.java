/**
 * Description: provide the information for the Neighbor nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.TSP.behavior.neighbor;

import Global.basic.data.matrix.*;
import Global.basic.data.collection.*;

public interface INeighborEngine extends IIrregularNumberEngine, INodeNumberEngine {
  public int[] getNNArrayAt(int index);
}

