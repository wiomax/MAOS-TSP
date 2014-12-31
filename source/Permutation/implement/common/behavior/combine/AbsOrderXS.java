/**
 * Description: the abstract order-based crossover for two sequences (order & position)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 04, 2007    To an abstract "order" version
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.common.behavior.combine;

import java.util.*;
import Global.basic.data.collection.*;

import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.combine.*;
import maosKernel.represent.landscape.*;
import implement.common.knowledge.*;

public abstract class AbsOrderXS extends AbsNoQRecombinationSearch {

  //modes for tacking unassigned nodes
  protected final int GBASE_NONE = 0;
  protected final int GBASE_COMMONNV = 1;
  protected final int GBASE_COMMONBV = 2;
  protected final int GBASE_SHAREDBV = 3;
  
  protected final int REFER_NONE = 0;
  protected final int REFER_POSITION = 1;

  protected final int REMAIN_NONE = 0;
  protected final int REMAIN_RND = 1;
  protected final int REMAIN_PM = 2;
  protected final int REMAIN_ORDER = 3;

  protected int gBaseMode = GBASE_NONE;
  protected int referMode = REFER_POSITION;
  protected int remainMode = REMAIN_RND;

  //selected IDs for the base state
  private boolean[] firstSelFlags;

  //temp values for remaining IDs and Values
  protected DualIAlienArray remainIDArray;
  protected DualIAlienArray remainVArray;
  protected DualIAlienArray selBaseIDArray;

  public AbsOrderXS() {}

  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    int nodeNumber = landscape.getSearchSpace().getNodeNumber();
    firstSelFlags = new boolean[nodeNumber];
    remainIDArray = new DualIAlienArray(nodeNumber);
    remainVArray = new DualIAlienArray(nodeNumber);
    selBaseIDArray = new DualIAlienArray(nodeNumber);
  }

  protected boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
//    trialPoint.importSearchState(basePoint);
    Arrays.fill(firstSelFlags, false);
    if (!baseSelection(firstSelFlags, basePoint, referPoint)) {
      return false;
    }

    int nodeNumber = trialPoint.getNodeNumber();

    //fills selected IDs from base state and builds remain data
    int candidateV;
    remainIDArray.clear();
    remainVArray.clear();
    for (int i=0; i<nodeNumber; i++) {
      candidateV = basePoint.getValueAt(i);
      if (!firstSelFlags[i]) {
        remainVArray.addElement(candidateV);
        remainIDArray.addElement(i);
      } else {
        trialPoint.setValueAt(candidateV, i);
      }
    }

    //from refer point
    int candidateID;
    int remainNumber;
    switch(referMode) {
      case REFER_POSITION: //absolution position
        remainNumber = remainIDArray.getSize();
        for (int i = remainNumber - 1; i > -1; i--) {
          candidateID = remainIDArray.getElementAt(i);
          candidateV = referPoint.getValueAt(candidateID);
          if (remainVArray.getElementID(candidateV) != -1) {
            trialPoint.setValueAt(candidateV, candidateID);
            remainIDArray.removeElement(candidateID);
            remainVArray.removeElement(candidateV);
          }
        }
        break;
      case REFER_NONE:
        break;
      default: //REFER_NONE
        break;
    }

    remainNumber = remainIDArray.getSize();
    if (remainNumber == 0) return true;

    //for remaining IDs
    switch(remainMode) {
      case REMAIN_RND: //random remains
        for (int i = 0; i < remainNumber; i++) {
          candidateV = remainVArray.getRandomElement();
          trialPoint.setValueAt(candidateV, remainIDArray.getElementAt(i));
          remainVArray.removeElement(candidateV);
        }
        break;
      case REMAIN_PM: //partial mapping remains
        int remainID = -1;
        for (int i = 0; i < remainNumber; i++) {
          remainID = remainIDArray.getElementAt(i);
          candidateV = getMappedValue(remainID, basePoint, referPoint);
          trialPoint.setValueAt(candidateV, remainID);
        }
        break;
      case REMAIN_ORDER: //ordered remains
        int remainCount = 0;
        for (int i = 0; i < nodeNumber; i++) {
          if (remainCount == remainNumber) break;
          candidateV = referPoint.getValueAt(i);
          if (remainVArray.getElementID(candidateV) != -1) {
            trialPoint.setValueAt(candidateV, remainIDArray.getElementAt(remainCount));
            remainCount++;
          }
        }
        break;
      default:
        return false;
    }
    return true;
  }

  private int getMappedValue(int remainID, SearchState basePoint, SearchState referPoint) {
    int baseV = basePoint.getValueAt(remainID);
    if (remainVArray.getElementID(baseV)!=-1) {
      return baseV;
    } else {
      int referID = referPoint.getValueIndex(baseV);
      return getMappedValue(referID, basePoint, referPoint);
    }
  }
  protected void initSelection(SearchState basePoint, SearchState referPoint){}

  private boolean baseSelection(boolean[] selFlags, SearchState basePoint, SearchState referPoint) {
    initSelection(basePoint, referPoint);
    boolean isBSuccess = blindSelection(selFlags);
    boolean isGSuccess = guideSelection(selFlags, basePoint, referPoint);
    return (isBSuccess||isGSuccess);
  }

  protected void guideSelection(DualIAlienArray selIDArray, SearchState basePoint, SearchState referPoint){
    switch (gBaseMode) {
      case GBASE_COMMONNV: 
        SequencesOperation.getCommonPositions(selIDArray, basePoint, referPoint);
        break;
      case GBASE_COMMONBV: 
        SequencesOperation.getCommonBlocks(selIDArray, basePoint, referPoint);
        break;
      case GBASE_SHAREDBV: 
        SequencesOperation.getSharedBlocks(selIDArray, basePoint, referPoint);
        break;
      case GBASE_NONE:
      default:
        break;
    }
  }
  
  protected boolean guideSelection(boolean[] selFlags, SearchState basePoint, SearchState referPoint){
    selBaseIDArray.clear();
    guideSelection(selBaseIDArray, basePoint, referPoint);
    if (selBaseIDArray.getSize()>0) {
      //unions the selected base IDs
      for (int i=0; i<selBaseIDArray.getSize(); i++) {
        selFlags[selBaseIDArray.getElementAt(i)] = true;
      }
      return true;
    } else {
      return false;
    }
  }

  protected boolean blindSelection(boolean[] selFlags) {
    return false;
  }
}
