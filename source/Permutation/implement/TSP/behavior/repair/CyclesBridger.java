/**
 * Description: bridge the intermediate tsp path in many cycles into one cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 28, 2005    Adapted from Nagata's EAX-Rand code in C++
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] Yuichi Nagata. The EAX Algorithm Considering Diversity Loss. PPSN, 2004
 */

package implement.TSP.behavior.repair;

import implement.TSP.represent.*;
import implement.TSP.behavior.neighbor.*;

public class CyclesBridger {
  private IsoCyclesManager isoCyclesManager;
  private int[][] costMatrix;

  public CyclesBridger(IGetProblemDataEngine problemData) {
    isoCyclesManager = new IsoCyclesManager(problemData.getNodeNumber());
    costMatrix = problemData.getCostMatrix();
  }

  private int get2EdgeChangeDiffValue(int node1A, int node1B, int node2A, int node2B) {
    return  costMatrix[node1A][node2A]
          + costMatrix[node1B][node2B]
          - costMatrix[node1A][node1B]
          - costMatrix[node2A][node2B];
  }


  public int bridgeCycles(DChainState chainState, INeighborEngine neighborEngine, boolean[] commonFlagArray, int joinType) {
    int j2, nnNumber, near_num;
    int startID,preID,currID,nextID;
    int[] nnArray;
    int a,b,c,d, node1A=0,node1B=0, node2A, node2B;
    int center_un = 0, mediem_un, select_un = 0;
    int curr_diff, min_diff, total_diff=0;

    isoCyclesManager.initIsoCycles(chainState);

    if (isoCyclesManager.getRemainedCycleNumber()<2) return total_diff;

    while(true) {
      center_un = isoCyclesManager.getMinCycleID();
      startID = isoCyclesManager.get_unit_start_ID_at(center_un);

      currID=-1;
      nextID=startID;
      node2A=-1; node2B=-1;

      min_diff = Integer.MAX_VALUE;
      while(true) {
        preID=currID;
        currID=nextID;
        nextID = chainState.getNextID(preID, currID);

        a=currID; b=nextID;
        if (joinType==0 || (!commonFlagArray[a] || !commonFlagArray[b]))  {
        nnArray = neighborEngine.getNNArrayAt(a);
        nnNumber = neighborEngine.getElementNumberAt(a);

        for(near_num=0;near_num<nnNumber;near_num++) {
          c=nnArray[near_num];
          mediem_un = isoCyclesManager.get_city_belong_unit_at(c);
          if(mediem_un!=center_un) {
            for(j2=0;j2<2;j2++) {
              if (j2==0) {
                d = chainState.getPreCityIDAt(c);
              } else {
                d = chainState.getPostCityIDAt(c);
              }
              if (joinType<2 || (!commonFlagArray[c] || !commonFlagArray[d]))  {
              curr_diff=costMatrix[a][c]+costMatrix[b][d]-costMatrix[a][b]-costMatrix[c][d];
              if(curr_diff<min_diff) {
                node1A=a; node1B=b; node2A=c; node2B=d;
                min_diff=curr_diff;
                select_un=mediem_un;
              }
              }
            }
          }
        }
        }
        if(nextID==startID) break;
      }
      if(node2A==-1 || node2B==-1) {
        //choose first feasible one
        for(c=0;c<chainState.getNodeNumber();c++) {
          select_un=isoCyclesManager.get_city_belong_unit_at(c);
          if(select_un!=center_un) {
            node1A=a; node1B=b;
            node2A=c; node2B=chainState.getPreCityIDAt(c);
            min_diff = get2EdgeChangeDiffValue(node1A, node1B, node2A, node2B);
            break;
          }
        }
      }

      // From Edge(node1A, node1B)+Edge(node2A, node2B) -> Edge(node1A, node2A)+Edge(node1B, node2B)
      // Usually for a) joining two cycles or b) breaking one cycle
      // @params: node1A, node1B, node2A, node2B are IDs of cities
      chainState.changeChainCityIDAt(node1A, node1B, node2A);
      chainState.changeChainCityIDAt(node1B, node1A, node2B);
      chainState.changeChainCityIDAt(node2A, node2B, node1A);
      chainState.changeChainCityIDAt(node2B, node2A, node1B);

      total_diff += min_diff;
      if (isoCyclesManager.getRemainedCycleNumber()>2) {
        isoCyclesManager.mergeTwoCycles(center_un, select_un);
      } else {
        break;
      }
    }
    return total_diff;
  }

}

