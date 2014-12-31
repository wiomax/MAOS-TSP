/**
 * Description: Return the best solution ever obtained
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 10, 2006
 *
 * @version 1.0
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.runtime;

import maosKernel.memory.*;

public abstract interface IGetBestSolutionEngine {
  public EncodedState getBestState();
}
