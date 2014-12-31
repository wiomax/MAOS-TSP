/**
* Description: 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 24, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.knowledge;

import Global.basic.data.collection.*;
import maosKernel.represent.landscape.space.*;

public class SequencesOperation {
  
  public static void getCommonPositions(boolean[] sameIDFlags, SearchState pointA, SearchState pointB) {
    int nodeNumber = sameIDFlags.length;
    for (int i=0; i<nodeNumber; i++) {
      sameIDFlags[i] = pointA.getValueAt(i)==pointB.getValueAt(i);
    }
  }
  public static void getCommonPositions(IDynamicICollectionEngine sameIDArray, SearchState pointA, SearchState pointB) {
    sameIDArray.clear();
    int nodeNumber = pointA.getNodeNumber();
    for (int i=0; i<nodeNumber; i++) {
      if (pointA.getValueAt(i)==pointB.getValueAt(i)) {
        sameIDArray.addElement(i);
      }
    }
  }
  public static void getSharedBlocks(IDynamicICollectionEngine sameIDArray, SearchState pointA, SearchState pointB) {
    getSharedBlocks(sameIDArray, pointA, pointB, 0, pointA.getNodeNumber());
  }
  
  public static void getSharedBlocks(IDynamicICollectionEngine sameIDArray, SearchState pointA, SearchState pointB, int startID, int endID) {
    int nodeNumber = pointA.getNodeNumber();
    sameIDArray.clear();
    int refID = nodeNumber;
    int beginID = -1, blockSize=0;
    for (int i=startID; i<endID; i++) {
      if (blockSize>0) {
        if (refID<nodeNumber-1 && pointA.getValueAt(i)== pointB.getValueAt(refID+1)) {
          blockSize++;
        } else {
          if (blockSize>1) {
            for (int j=0; j<blockSize; j++) {
              sameIDArray.addElement(beginID+j);
            }
          }
          blockSize = 0;
        }
      } else {
        refID = pointB.getValueIndex(pointA.getValueAt(i));
        beginID = i;
        blockSize = 1;
      }
    }
  }

  public static void getCommonBlocks(IDynamicICollectionEngine sameIDArray, SearchState pointA, SearchState pointB) {
    getCommonPositions(sameIDArray, pointA, pointB);
    int sameSize = sameIDArray.getSize();
    if (sameSize<2) {
      sameIDArray.clear();
      return;
    }
    boolean preVoteFlag = false, postVoteFlag = false;
    for (int i=sameIDArray.getSize()-1; i>=0; i--) {
      postVoteFlag = i>0 && (sameIDArray.getElementAt(i)-sameIDArray.getElementAt(i-1)==1);
      if (!preVoteFlag && !postVoteFlag) {
        sameIDArray.removeElementAt(i);
      }
      preVoteFlag = postVoteFlag;
    }
  }
}

