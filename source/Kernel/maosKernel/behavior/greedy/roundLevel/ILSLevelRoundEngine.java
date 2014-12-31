/**
 * Description: For moving one node, and return the delta quality value
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 16, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.roundLevel;

import maosKernel.behavior.greedy.nodeLevel.*;

public interface ILSLevelRoundEngine {
  public double moveRound(AbsLSLevelNodePolicy nodeEngine);
}
