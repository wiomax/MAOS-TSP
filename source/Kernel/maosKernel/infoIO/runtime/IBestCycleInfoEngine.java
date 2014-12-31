/**
 * Description: provide the interface for handling best information in a cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 19, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.runtime;

public interface IBestCycleInfoEngine extends IGetBestSolutionEngine {

  public double getBestTime();

  public int getBestNCycle();

}

