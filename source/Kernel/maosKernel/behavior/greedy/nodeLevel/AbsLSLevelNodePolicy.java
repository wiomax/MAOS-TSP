/**
 * Description: The node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import maosKernel.represent.landscape.quality.*;
import maosKernel.behavior.*;

public abstract class AbsLSLevelNodePolicy extends AbsBehavior implements ILSLevelNodeEngine, IGetGlobalCostEngine {
  
  public AbsLSLevelNodePolicy() {}
}
