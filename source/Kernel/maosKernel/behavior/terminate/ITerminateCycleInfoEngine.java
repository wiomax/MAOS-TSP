/**
 * Description: basic information for checking termination conditions
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 22, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.terminate;

public interface ITerminateCycleInfoEngine {
  int getMaxCycleNumber();
  int getMaxStagnateCycleNumber();
}
