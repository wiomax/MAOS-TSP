/**
 * Description: The Generalized EAX with a sorting-based AB-cycle selection.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 * 
 * [1] Xiao-Feng Xie, Jiming Liu. Multiagent optimization system for solving the traveling 
 *  salesman problem (TSP). IEEE Transactions on Systems, Man, and Cybernetics - Part B, 
 *  2009, 39(2): 489-502 
 */

package implement.TSP.behavior.combine.eax;

import java.util.*;

import Global.basic.*;
import Global.basic.data.collection.*;

public final class SortEAXRecombinator extends AbsEAXRecombinator {
  /**************************************************/
  private IndexedDValue[] indexedV;

  public SortEAXRecombinator() {}

  protected void init(int nodeNumber) {
    super.init(nodeNumber);
    indexedV = new IndexedDValue[nABCycle];
    for(int i=0; i<indexedV.length; i++) {
      indexedV[i] = new IndexedDValue();
    }
  }

  protected void designABSet(IDynamicICollectionEngine activeCycleIDs, int Nmtm, ABCycleLib abCycleLib, int[][] costMatrix) {
    int idSetSize = abCycleLib.getSize();
    if (Nmtm>=idSetSize) {
      for (int i=0; i<idSetSize; i++) {
        activeCycleIDs.addElement(i);
      }
      return;
    }

    //sort
    for (int i=0; i<idSetSize; i++) {
      indexedV[i].index = i;
      indexedV[i].value = getCycleCostDifference(i, costMatrix);
    }
    Arrays.sort(indexedV, 0, idSetSize);
    for (int i=0; i<Nmtm; i++) {
      activeCycleIDs.addElement(indexedV[i].index);
    }
  }
}
