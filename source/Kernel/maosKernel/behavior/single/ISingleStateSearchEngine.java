/**
 * Description: The interface of search for achieving an new state 
 * based on the trialState (may in SPACE_FULL)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 19, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.single;

import maosKernel.memory.*;

public interface ISingleStateSearchEngine {
  public boolean generate(EncodedState trialState);
}
