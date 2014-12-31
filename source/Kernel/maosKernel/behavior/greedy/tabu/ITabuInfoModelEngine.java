/**
 * Description: provide the information for tabu information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.tabu;

import maosKernel.behavior.greedy.onemove.*;

public interface ITabuInfoModelEngine {

  public void init1MoveModel(AbsBasic1MoveModel basic1MoveModel);
  public void initTabuInfo();
  public boolean isNotForbidden(int nodeID, int referV, double deltaQuality);
  public void setForbiddenInfo(int nodeID, int baseV, double deltaQuality);
}
