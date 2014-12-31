/**
 * Description: full-scan node-level policy
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import java.util.Arrays;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;

public class DNLMFullScanLSLevelNodePolicy extends BasicFullScanLSLevelNodePolicy {
  private boolean[][] dnlmMatrx;
  
  public DNLMFullScanLSLevelNodePolicy() {}

  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    dnlmMatrx = new boolean[landscape.getNodeNumber()][landscape.getNodeNumber()];
  }

  public void setState(SearchState baseState) {
    super.setState(baseState);
    for (int i=0; i<dnlmMatrx.length; i++) {
      Arrays.fill(dnlmMatrx[i], false);
    }
  }

  protected void premove(int nodeID, int referV) {
    Arrays.fill(dnlmMatrx[nodeID], false);
    Arrays.fill(dnlmMatrx[referV], false);
  }
  
  protected boolean isValidReferV(int nodeID, int referV) {
    return !dnlmMatrx[nodeID][referV];
  }

  protected void invalidReferV(int nodeID, int referV) {
    dnlmMatrx[nodeID][referV] = true;
  }
}
