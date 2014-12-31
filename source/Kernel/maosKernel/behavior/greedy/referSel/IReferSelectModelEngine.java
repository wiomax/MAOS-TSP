/**
 * Description: provide the information for reference selection
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.referSel;


public interface IReferSelectModelEngine {

  public void initModel();
  public boolean isAcceptableQuality(double newReferQuality);
  public boolean isFinished();
}
