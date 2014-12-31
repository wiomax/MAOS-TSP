/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.roundLevel;


public class ReScanLSLevelRoundPolicy extends AbsLSLevelRoundPolicy {
  
  public ReScanLSLevelRoundPolicy() {}
  
  protected boolean isRoundFinished(double sumDeltaCost) {
    return sumDeltaCost<0;
  }

}
