/**
 * Description: The description of 2-opt strategies.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005    For TSP problem
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 * @ Reference:
 * [1] Program's name: acotsp
 *  Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
 *  symmetric TSP, Copyright (C) 2004  Thomas Stuetzle
 *  Email: stuetzle no@spam informatik.tu-darmstadt.de
 * [2] S. Lin and B. W. Kernighan, "An effective heuristic algorithm for the
 *  traveling-salesman problem," Operations Research, vol. 21, pp. 498-516, 1973.
 * [3] Reinelt, G. The Traveling Salesman: Computational Solutions for TSP
 *  Applications. Berlin: Springer, 1994.
 */

package implement.TSP.behavior.greedy;

import Global.basic.data.collection.*;
import Global.basic.nodes.utilities.*;
import Global.methods.*;
import maosKernel.behavior.greedy.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;

import implement.TSP.behavior.neighbor.*;
import implement.TSP.knowledge.*;
import implement.TSP.represent.*;

public class Basic2OPT extends AbsExplicitLocalSearch {
  //global information
  private AbsNeighborManager neighborEngine;
  private int[][] costMatrix;

  public Basic2OPT() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("neighborEngine", neighborEngine));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    neighborEngine = (AbsNeighborManager)(getValue("neighborEngine"));
  }

  protected void setRootInfo(AbsLandscape virtualLandscape) {
    costMatrix = ((InternalRepresentation)virtualLandscape).getIGetProblemDataEngine().getCostMatrix();
  }
  
  protected boolean generate(SearchState baseState) {
    betterOpt(baseState.getIArray(), costMatrix, neighborEngine);
    return true;
  }

  public int betterOpt(int[] tour, int[][] distEngine, INeighborEngine neighborEngine) {
    int execCount = 0;

    int n = tour.length;
    DualIAlienArray dpArray = new DualIAlienArray(n);

    int[] tour_IDs = new int[2]; //selected IDs in tour
    int[] city_IDs = new int[2]; //selected nodes
    int[] p_tour_IDs = new int[2]; /* ID precessors*/
    int[] p_city_IDs = new int[2]; /* node precessors*/
    int[] s_tour_IDs = new int[2]; /* ID successors*/
    int[] s_city_IDs = new int[2]; /* node successors*/

    boolean improvement_flag = true;

    int totalDeltaValue = 0;
    int diffValue = 0;

    while (improvement_flag) {
      dpArray.clear();
      dpArray.addElements(tour);
      improvement_flag = false;

      int randStartID = RandomGenerator.intRangeRandom(n);
      for (int l = 0; l < n; l++) {

        tour_IDs[0] = BasicArray.getPeriodID(n, randStartID + l);/* first ID in tour */
        city_IDs[0] = tour[tour_IDs[0]]; /* first city */
        s_tour_IDs[0] = BasicArray.getSuccessorID(n, tour_IDs[0]);
        s_city_IDs[0] = tour[s_tour_IDs[0]];
        p_tour_IDs[0] = BasicArray.getPrecessorID(n, tour_IDs[0]);
        p_city_IDs[0] = tour[p_tour_IDs[0]];

        int h = 0; /* Search for one of the h-nearest neighbours */
        int nn_ls0 = neighborEngine.getElementNumberAt(city_IDs[0]);
        while (h < nn_ls0) {
          if (improvement_flag) {
            break;
          }
          city_IDs[1] = neighborEngine.getNNArrayAt(city_IDs[0])[h]; /* second city*/
          tour_IDs[1] = dpArray.getElementID(city_IDs[1]); /* second ID in tour*/

          //Forward 2-Opt
          s_tour_IDs[1] = BasicArray.getSuccessorID(n, tour_IDs[1]);
          s_city_IDs[1] = tour[s_tour_IDs[1]];

          if (s_tour_IDs[0] != tour_IDs[1] && tour_IDs[0]!= s_tour_IDs[1]) {
//            diffValue = distEngine.getLocalCost(city_IDs[0], city_IDs[1])
//                      - distEngine.getLocalCost(city_IDs[0], s_city_IDs[0]);
//            if (diffValue < 0) {
//              diffValue += distEngine.getLocalCost(s_city_IDs[0], s_city_IDs[1])
//                         - distEngine.getLocalCost(city_IDs[1], s_city_IDs[1]);
              diffValue = MutateKnowledge.get2EdgeChangeDiffValue(distEngine,
                                                  city_IDs[0], s_city_IDs[0],
                                                  city_IDs[1], s_city_IDs[1]);
              if (diffValue < 0) {
                execCount++;
                interal2Exchange(tour, tour_IDs[0], s_tour_IDs[0], tour_IDs[1], s_tour_IDs[1]);
                improvement_flag = true;
                totalDeltaValue+=diffValue;
                break;
              }
//            }
          }
          //Backward 2-Opt
          p_tour_IDs[1] = BasicArray.getPrecessorID(n, tour_IDs[1]);
          p_city_IDs[1] = tour[p_tour_IDs[1]];
          if (tour_IDs[0] != p_tour_IDs[1] && p_tour_IDs[0] != tour_IDs[1]) {
//            diffValue = distEngine.getLocalCost(p_city_IDs[0], p_city_IDs[1])
//                      - distEngine.getLocalCost(p_city_IDs[0], city_IDs[0]);
//            if (diffValue < 0) {
//              diffValue += distEngine.getLocalCost(city_IDs[0], city_IDs[1])
//                         - distEngine.getLocalCost(p_city_IDs[1], city_IDs[1]);
              diffValue = MutateKnowledge.get2EdgeChangeDiffValue(distEngine,
                                                  p_city_IDs[0], city_IDs[0],
                                                  p_city_IDs[1], city_IDs[1]);
              if (diffValue < 0) {
                execCount++;
                interal2Exchange(tour, p_tour_IDs[0], tour_IDs[0], p_tour_IDs[1], tour_IDs[1]);
                totalDeltaValue+=diffValue;
                improvement_flag = true;
                break;
              }
//            }
          }

          h++;
        }
      }
    }
    return diffValue;
  }

  public static void interal2Exchange(int[] tour, int cityX1AID, int cityX1BID, int cityX2AID, int cityX2BID) {
    int n = tour.length;
    if (BasicArray.getPeriodDistance(n, cityX1BID, cityX2AID)<BasicArray.getPeriodDistance(n, cityX2BID, cityX1AID)) {
      ArrayOperator.inverseSegment(tour, cityX1BID, cityX2AID);
    } else {
      ArrayOperator.inverseSegment(tour, cityX2BID, cityX1AID);
    }
  }

  //Here minID<maxID
  private static boolean pureOnce2opt(int[] tour, int[][] distEngine, int minID, int maxID, boolean isSteadyUpdated) {
    int n = tour.length;
    int cityX1AID = minID;
    int cityX1BID = BasicArray.getSuccessorID(n, cityX1AID);
    int cityX2AID = maxID;
    int cityX2BID = BasicArray.getSuccessorID(n, cityX2AID);

    boolean isUpdated = !isSteadyUpdated;
    if (isSteadyUpdated) {
      int diff = MutateKnowledge.get2EdgeChangeDiffValue(distEngine,
                 tour[cityX1AID], tour[cityX1BID],
                 tour[cityX2AID], tour[cityX2BID]);
      isUpdated = (diff<0);
    }
    if (isUpdated) {
      interal2Exchange(tour, cityX1AID, cityX1BID, cityX2AID, cityX2BID);
      return true;
    }
    return false;
  }

//  public boolean once2opt(int[] tour, int[][] distEngine, int minV, int maxV, boolean isSteadyUpdated) {
//    //note: each int in tour[] is an index to an array of city objects
//    int n = tour.length;
//    v2[0] = minV;
//    v2[1] = maxV;
//    Arrays.sort(v2);
//    if (v2[1]-v2[0]<2 || v2[1]-v2[0]>n-2) {
//      return false;
//    }
//    return pureOnce2opt(tour, distEngine, v2[0], v2[1], isSteadyUpdated);
//  }
}
