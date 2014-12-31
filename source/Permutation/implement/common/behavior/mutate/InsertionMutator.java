/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import java.util.*;
import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class InsertionMutator extends AbsPerturbationSearch {
  private int[] xPoints = new int[2];

  public InsertionMutator() {
  }

  public boolean generate(SearchState baseState){
    RandomGenerator.randomDistinctSelection(baseState.getNodeNumber(), xPoints);
    Arrays.sort(xPoints);
    ArrayOperator.inverseSegment(baseState.getIArray(), xPoints[0], xPoints[1]);
    return true;
  }
}
