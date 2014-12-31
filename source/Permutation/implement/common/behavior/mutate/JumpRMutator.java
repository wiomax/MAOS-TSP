/**
 * Description: The description of Jump Distance strategy (reorder at random in given
 * distance).
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class JumpRMutator extends AbsPerturbationSearch {
  private int[] jumpArray = new int[2];

  public JumpRMutator() {
  }

  public JumpRMutator(int jumpDistance) {
    setJumpDistance(jumpDistance);
  }

  public void setJumpDistance(int jumpDistance) {
    jumpArray = new int[jumpDistance];
  }

  public boolean generate(SearchState baseState){
    int[] p = baseState.getIArray();
    if (jumpArray.length<1) return false;
    int[] selIndices = RandomGenerator.randomDistinctSelection(p.length, jumpArray.length);
    for (int i=0; i<selIndices.length; i++) {
      ArrayOperator.swapData(p, selIndices[i], selIndices[(i+1)%jumpArray.length]);
    }
    return true;
  }
}
