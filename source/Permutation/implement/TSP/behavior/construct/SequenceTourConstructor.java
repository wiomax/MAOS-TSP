/**
 * Description: To construct a tour in sequence
  *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005    For ant algorithm
 * Xiaofeng Xie    May 24, 2005    For general construction
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;

import java.util.*;
import Global.methods.*;
import maosKernel.represent.landscape.space.*;

public class SequenceTourConstructor {
  private INextNodeSelectEngine nextNodeSelector;
  //temp values
  private boolean[] occupyFlags;

  public SequenceTourConstructor(int nodeNumber) {
    occupyFlags = new boolean[nodeNumber];
  }

  public SequenceTourConstructor(int nodeNumber, INextNodeSelectEngine nextNodeSelector) {
    this(nodeNumber);
    setINextNodeSelectEngine(nextNodeSelector);
  }

  public void setINextNodeSelectEngine(INextNodeSelectEngine nextNodeSelector) {
    this.nextNodeSelector = nextNodeSelector;
  }

  protected int selectNextNodeAt(int selCityID, boolean[] occupyFlags) {
    if (nextNodeSelector==null) return -1;
    return nextNodeSelector.selectNextNodeAt(selCityID, occupyFlags);
  }

  public boolean generate(SearchState trialPoint) {
    /* Mark all nodes as unvisited */
    Arrays.fill(occupyFlags, false);
    /* Start from a random initial node */
    int selCityID = RandomGenerator.intRangeRandom(occupyFlags.length);
    /* Select next node one by one*/
    for(int i=0; i<occupyFlags.length; i++) {
      occupyFlags[selCityID] = true;
      trialPoint.setValueAt(selCityID, i);
      if (i!=occupyFlags.length-1) {
        selCityID = selectNextNodeAt(selCityID, occupyFlags);
        if (selCityID==-1) {
          selCityID = RandomGenerator.intRangeRandom(occupyFlags, false);
        }
      }
    }
    return true;
  }
}
