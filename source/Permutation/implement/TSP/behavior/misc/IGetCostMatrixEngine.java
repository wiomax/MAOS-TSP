/**
 * Description: Return a Cost Matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Mar 21, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.misc;

import Global.basic.data.matrix.*;

public interface IGetCostMatrixEngine {
  public ISquareIMatrixEngine getCostMatrix();
}
