/**
 * Description: Set Mask Flags (as the channel ouput)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.channel;

import Global.basic.data.collection.*;

public interface ISetMaskFlagsEngine {
  public void setMaskFlags(IAlienICollectionEngine checkFlags);
}
