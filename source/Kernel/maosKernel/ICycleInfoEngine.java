/**
 * Description: For managing the results in each cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel;

import maosKernel.memory.*;

public interface ICycleInfoEngine {

  public void initTrial();
  public boolean cycleCheck(int cycleNumber, EncodedState bestState) throws Exception;
  
  public void setMonitorInfo(InteractionCenter socialSet);
}
