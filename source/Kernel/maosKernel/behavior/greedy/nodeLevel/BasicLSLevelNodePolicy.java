/**
 * Description: basic node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

public class BasicLSLevelNodePolicy extends AbsSingleLSLevelNodePolicy {
  
  public BasicLSLevelNodePolicy() {}
  
  protected boolean isValidReferV(int nodeID, int referV, double deltaQuality) {
    return true;
  }

  protected void preMove(int nodeID, int baseV, double deltaQuality) {
  }
}
