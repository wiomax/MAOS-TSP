/**
 * Description: for handling Historical State
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 22, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.historical;

import maosKernel.behavior.terminate.IOptimalStateCheckEngine;
import maosKernel.memory.*;

public interface IHistoricalHandleEngine extends IOptimalStateCheckEngine {
  public boolean updateHistoricalState(EncodedState state);
  public boolean outputHistoryState() throws Exception;
}
