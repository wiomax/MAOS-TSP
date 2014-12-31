/**
 * Description: The description of cycle crossover.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 08, 2005
 * Xiaofeng Xie    Jul 16, 2006    MAOS-ALL Beta 1.1.006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import Global.basic.data.collection.*;
import Global.methods.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.combine.*;
import maosKernel.behavior.channel.*;
import Global.basic.nodes.utilities.*;

public class CycleXS extends AbsNoQRecombinationSearch implements IGetMaskFlagsEngine {
  private int maxLibSize = Integer.MAX_VALUE;
  private int tourSize = 1;
  //For local search
  protected DualIAlienArray sameElemIDArray = null;
  private CycleLib cycleLib;

  public CycleXS() {}

  protected void setRootInfo(AbsLandscape landscape) {
    int nodeNumber = landscape.getSearchSpace().getNodeNumber();
    sameElemIDArray = new DualIAlienArray(nodeNumber);
    cycleLib = new CycleLib(nodeNumber);
    cycleLib.setMaxSize(maxLibSize);
  }

  public IAlienICollectionEngine getMaskFlags() {
    return this.sameElemIDArray;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("tourSize", tourSize));
    initUtility(new IntegerUtility("maxLibSize", maxLibSize));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    tourSize = TypeConverter.toInteger(getValue("tourSize"));
    maxLibSize = TypeConverter.toInteger(getValue("MaxLibSize"));
  }

  private void crossByLocation(SearchState trialPoint, SearchState referPoint, int locID) {
    trialPoint.setValueAt(referPoint.getValueAt(locID), locID);
  }

  private void crossByIArray(SearchState trialPoint, SearchState referPoint, int[] indices) {
    for (int i=0; i<indices.length; i++) {
      crossByLocation(trialPoint, referPoint, indices[i]);
    }
  }

  private int getMINCycleID() {
    int selID = -1;
    int minSize = Integer.MAX_VALUE;
    int realTourSize = Math.min(tourSize, cycleLib.getCycleNumber());
    for (int i=0; i<realTourSize; i++) {
      if (cycleLib.getCycleAt(i).length<minSize) {
        selID = i;
        minSize = cycleLib.getCycleAt(i).length;
      }
    }
    return selID;
  }

  protected void preProcess(SearchState basePoint, SearchState referPoint) {
    int nodeNumber = basePoint.getNodeNumber();
    //recognizes the facilities with same location
    sameElemIDArray.clear();
    for (int i=0; i<nodeNumber; i++) {
      if (basePoint.getValueAt(i)==referPoint.getValueAt(i)) {
        sameElemIDArray.addElement(i);
      }
    }
    cycleLib.initCycles(basePoint, referPoint, sameElemIDArray);
  }

  //Single Cycle X (SCX, or CX-1)
  protected boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
    preProcess(basePoint, referPoint);
    if (cycleLib.getCycleNumber()<1) return false;
    if ((cycleLib.getCycleNumber()<2 && maxLibSize>1)) return false;
    trialPoint.importSearchState(basePoint);
    crossByIArray(trialPoint, referPoint, cycleLib.getCycleAt(getMINCycleID()));
    return true;
  }
}
