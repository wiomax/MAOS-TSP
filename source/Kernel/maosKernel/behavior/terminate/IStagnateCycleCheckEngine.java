/**
 * Description: provide the cycle number for the stagnation of the best result
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.terminate;

public interface IStagnateCycleCheckEngine {
  public int getStagnatedCycleNumber();
}
