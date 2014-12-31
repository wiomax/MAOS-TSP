/**
 * Description: provide the information for the search space with sequence state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.represent;

import java.util.*;
import Global.methods.*;
import maosKernel.represent.landscape.space.*;

public class SeqSearchSpace extends AbsSearchSpace {
  private int nodeNumber = -1;

  public SeqSearchSpace(int nodeNumber) {
    this.nodeNumber = nodeNumber;
    this.occupyFlags = new boolean[nodeNumber];
  }

  public int getNodeNumber() {
    return nodeNumber;
  }

  public SearchState getRandomState() {
    return new SearchState(RandomGenerator.randomDistinctSelection(getNodeNumber()));
  }

  private boolean[] occupyFlags;
  public boolean isValid(SearchState point) {
    Arrays.fill(occupyFlags, false);
    int[] tourArray = point.getIArray();
    if (tourArray == null) return false;
    if (tourArray.length!= this.getNodeNumber()) return false;
    for (int i=0; i<tourArray.length; i++) {
      if (occupyFlags[tourArray[i]]) return false;
      occupyFlags[tourArray[i]] = true;
    }
    return true;
  }
}

