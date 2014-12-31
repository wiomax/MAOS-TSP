/**
 * Description: checking the output condition
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 21, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO;

public class OutputIntervalChecker {
  private int T_out = 1;

  public void setOutputInterval(int T_OUT) {
    this.T_out = T_OUT;
  }

  public boolean isCycleNormalOutput(int currentNCycle) {
    return (T_out>0 && currentNCycle % T_out == 0);
  }
}

