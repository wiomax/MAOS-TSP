/**
 * Description: provide an graph for merging two Hamilton paths
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 19, 2005    Adapted from Nagata's EAX-Rand code in C++
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] Yuichi Nagata. The EAX Algorithm Considering Diversity Loss. PPSN, 2004
 */

package implement.TSP.behavior.combine.eax;

import java.util.*;
import Global.basic.data.collection.*;
import implement.TSP.represent.*;

public class ABGraph {
  private int[] chanceArray; // For the major state
  private DualIAlienArray chanceOneNodeArray;
  private DualIAlienArray chanceTwoNodeArray;

  private DChainState[] parentStates = new DChainState[2];

  public ABGraph(int nodeNumber) {
    for (int i=0; i<parentStates.length; i++) {
      parentStates[i] = new DChainState(nodeNumber);
    }
    chanceArray = new int[nodeNumber];
    chanceOneNodeArray = new DualIAlienArray(nodeNumber);
    chanceTwoNodeArray = new DualIAlienArray(nodeNumber);
  }

  public void removeNode(int ci) {
    if(chanceArray[ci]==2) {
      chanceTwoNodeArray.removeElement(ci);
      chanceOneNodeArray.addElement(ci);
    } else if(chanceArray[ci]==1) {
      chanceOneNodeArray.removeElement(ci);
    }
    chanceArray[ci]--;
  }

  public DualIAlienArray getChanceOneNodeArray() {
    return chanceOneNodeArray;
  }

  public DualIAlienArray getChanceTwoNodeArray() {
    return chanceTwoNodeArray;
  }

  public int getNodeNumber() {
    return chanceArray.length;
  }

  public int getABChanceNumberAt(int selID) {
    return chanceArray[selID];
  }

  public void swapData(int selID, int preID, int parentID) {
    if (parentStates[parentID].getPreCityIDAt(selID) == preID) {
      swapData(selID, parentID);
    }
  }

  public void swapData(int selID, int parentID) {
    parentStates[parentID].swapChainAt(selID);
  }

  public int getCurrentID(int selID, int parentID, int pr_type) {
    int ci = -1;
    switch(pr_type) {
    case 1:
      ci = parentStates[parentID].getPreCityIDAt(selID);
      break;
    case 2:
      if (Math.random()<0.5) {
        swapData(selID, parentID);
      }
      ci = parentStates[parentID].getPostCityIDAt(selID);
      break;
    case 3:
      ci = parentStates[parentID].getPostCityIDAt(selID);
      break;
    }
    return ci;
  }

  //put the two parents, and determine which node is n-Chance node (n=1,2)
  public void initChances(DChainState parentAPoint, DChainState parentBPoint, boolean[] commonFlagArray) {
    parentStates[0].importPoint(parentAPoint);
    parentStates[1].importPoint(parentBPoint);

    int nodeNumber = getNodeNumber();

    Arrays.fill(chanceArray, 2); //indicate the chance to be enter a AB-cycle

    for( int j = 0; j < nodeNumber ; ++j ) {
      if (commonFlagArray[j]) {
        chanceArray[j]= 0;
        chanceArray[parentAPoint.getPreCityIDAt(j)] = Math.min(chanceArray[parentAPoint.getPreCityIDAt(j)], 1);
        chanceArray[parentAPoint.getPostCityIDAt(j)] = Math.min(chanceArray[parentAPoint.getPostCityIDAt(j)], 1);
      }
    }

    //If is 1-Chance node, then move the 1-Chance path to be the first node
    for( int j = 0; j < nodeNumber ; ++j ) {
      if (chanceArray[j]== 1) {
        if (parentStates[0].getPreCityIDAt(j)==parentBPoint.getPreCityIDAt(j)) {
          swapData(j, 0);
          swapData(j, 1);
        } else if (parentAPoint.getPreCityIDAt(j)==parentBPoint.getPostCityIDAt(j)) {
          swapData(j, 0);
        } else if (parentAPoint.getPostCityIDAt(j)==parentBPoint.getPreCityIDAt(j)) {
          swapData(j, 1);
        }
      }
    }

    chanceOneNodeArray.clear();
    chanceTwoNodeArray.clear();
    for( int j = 0; j < nodeNumber ; ++j ) {
      if (chanceArray[j]== 2) {
        chanceTwoNodeArray.addElement(j);
      } else if (chanceArray[j]== 1) {
        chanceOneNodeArray.addElement(j);
      }
    }
  }
}

