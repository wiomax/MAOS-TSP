/**
 * Description: generate the AB-cycles
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 18, 2005    Adapted from Nagata's EAX-Rand code in C++
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 03, 2006    Remove redundant int[] creations
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

public class ABCycleLib {
  private int maxSize = 100;
  /******************cycle data**********************/
  private int[] cycleDataArray;
  private IArray cycleCountArray;
  /*temp*********************************************/
  private int n_edge;
  private int[] route;
  private int[] pRoute;
  private ABGraph abGraph;
  /**************************************************/

  public ABCycleLib(int nodeNumber) {
    abGraph = new ABGraph(nodeNumber);
    route = new int [nodeNumber*2 + 1];
    pRoute = new int [nodeNumber];
    cycleDataArray = new int [nodeNumber*2 + 1];
    cycleCountArray = new IArray(nodeNumber+1);
  }

  public void clear() {
    cycleCountArray.clear();
    cycleCountArray.addElement(0);
  }

  public int getCycleStartIndex(int cycleID) {
    return cycleCountArray.getElementAt(cycleID);
  }

  public int[] getCycleData() {
    return cycleDataArray;
  }

  public int getCycleSizeAt(int cycleID) {
    return cycleCountArray.getElementAt(cycleID+1)-cycleCountArray.getElementAt(cycleID);
  }

  public int getSize() {
    return cycleCountArray.getSize()-1;
  }

  public void setMaxSize(int size) {
    this.maxSize = size;
  }

  public void setABCycles(DChainState parentAPoint, DChainState parentBPoint, boolean[] commonFlagArray) {
    clear();
    abGraph.initChances(parentAPoint, parentBPoint, commonFlagArray);
    extractABGraph(abGraph);
  }

  private void extractABGraph(ABGraph abGraph) {

    int startID = 0, currID = 0, preID = 0;
    int pr_type = 0;

    boolean flag_st = true;
    boolean flag_circle = false;

    Arrays.fill(pRoute, -1);

    //2-chance nodes
    DualIAlienArray chanceTwoNodeArray = abGraph.getChanceTwoNodeArray();
    while(chanceTwoNodeArray.getSize()>0) {
      if(flag_st) {
        n_edge=0;
        startID=chanceTwoNodeArray.getRandomElement();
        pRoute[startID]=0;
        route[0]=startID;
        currID=startID;
        pr_type=2;
      } else {
        currID=route[n_edge];
      }

      flag_circle = false;
      while(!flag_circle) {
        n_edge++;

        preID=currID;

        currID = abGraph.getCurrentID(preID, n_edge%2, pr_type);

        route[n_edge]=currID;

        if(abGraph.getABChanceNumberAt(currID)==2) {
          if(currID==startID) {
            if(pRoute[startID]==0) {
              if((n_edge-pRoute[startID])%2==0) {
                abGraph.swapData(currID, preID, n_edge%2);
                if (formABCircle(1) && getSize()>=maxSize) return;

                flag_st=false;
                flag_circle = true;
                pr_type=1;
              }
              else {
                abGraph.swapData(currID, n_edge%2);
                pr_type=2;
              }
              pRoute[startID]=n_edge;
            } else {
              if (formABCircle(2) && getSize()>=maxSize) return;
              flag_st=true;
              flag_circle=true;
            }
          } else if(pRoute[currID]==-1) {
            pRoute[currID]=n_edge;
            abGraph.swapData(currID, preID, n_edge%2);
            pr_type=2;
          } else if(pRoute[currID]>0) {
            abGraph.swapData(currID, n_edge%2);
            if((n_edge-pRoute[currID])%2==0) {
              if (formABCircle(1) && getSize()>=maxSize) return;
              flag_st=false;
              flag_circle=true;
              pr_type=1;
            } else {
              abGraph.swapData(currID, (n_edge+1)%2);
              pr_type=3;
            }
          }
        } else if(abGraph.getABChanceNumberAt(currID)==1)  {
          if(currID==startID) {
            if (formABCircle(1)&& getSize()>=maxSize) return;
            flag_st=true;
            flag_circle=true;
          } else {
            pr_type = 1;
          }
        }
      }
    }

    //1-chance nodes
    DualIAlienArray chanceOneNodeArray = abGraph.getChanceOneNodeArray();
    while(chanceOneNodeArray.getSize()>0) {
      n_edge=0;
      startID=chanceOneNodeArray.getRandomElement();
      route[0]=startID;
      currID=startID;

      flag_circle=false;
      while(!flag_circle) {
        preID=currID;
        n_edge++;
        currID = abGraph.getCurrentID(preID, n_edge%2, 1);
        route[n_edge]=currID;
        if(currID==startID) {
          if (formABCircle(1)&& getSize()>=maxSize) return;
          flag_circle=true;
        }
      }
    }
  }

  private boolean formABCircle(int st_appear) {
    int startID,currID;
    int cem = 0; /* n_circle_edge */

    boolean isAEdge = (n_edge%2==0);  /* A edge */
    int bias = 0;
    if (isAEdge) {
      bias = 1;
    }

    int currIndex = cycleCountArray.getElementAt(cycleCountArray.getSize()-1);

    startID=route[n_edge];
    cycleDataArray[currIndex+bias]=startID;

    int st_count = 0;
    while(true) {
      cem++;
      n_edge--;
      currID=route[n_edge];
      abGraph.removeNode(currID);
      if(currID==startID) st_count++;
      if(st_count==st_appear) break;
      cycleDataArray[cem+currIndex+bias]=currID;
    }

    if (isAEdge) {
      cycleDataArray[currIndex] = cycleDataArray[cem+currIndex];
    }

    if(cem<4 || cem>=abGraph.getNodeNumber()) {
      return false;
    }
    cycleCountArray.addElement(currIndex+cem);
    return true;
  }
}

