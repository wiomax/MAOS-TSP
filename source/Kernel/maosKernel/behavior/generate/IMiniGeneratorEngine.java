/**
 * Description: The interface of generating operator for achieve an trial state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 19, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.generate;

import maosKernel.memory.*;

public interface IMiniGeneratorEngine {
  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine);
}
