/**
 * Description: The description of 3-opt strategies.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005    For TSP problem
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Sep 02, 2006    To correct mapping of exchangeCases
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

import Global.methods.*;

import static java.lang.Math.min;

import Global.basic.nodes.utilities.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.*;
import implement.TSP.behavior.neighbor.*;
import implement.TSP.represent.*;

public final class Basic3OPT extends AbsExplicitLocalSearch {
  private double bit_Ratio = 0.01;

  //temp values
  private boolean[] bit_flags = new boolean[0];

  private int[] cityPosition = new int[0];      /* positions of cities in tour */
  private int[] c_tour_IDs = new int[3];        //selected IDs in tour
  private int[] p_tour_IDs = new int[3];        /* ID predecessors*/
  private int[] s_tour_IDs = new int[3];        /* ID successors*/
  private int[] c_city_IDs = new int[3];        //selected nodes

  //global information
  private AbsNeighborManager neighborEngine;
  private int[][] costMatrix;

  public Basic3OPT() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("bit_Ratio", bit_Ratio));
    initUtility(new BasicUtility("neighborEngine", neighborEngine));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    bit_Ratio = TypeConverter.toDouble(getValue("bit_Ratio"));
    neighborEngine = (AbsNeighborManager)(getValue("neighborEngine"));
  }

  protected boolean generate(SearchState baseState) {
    betterOpt(baseState.getIArray(), costMatrix, neighborEngine);
    return true;
  }

  protected void setRootInfo(AbsLandscape virtualLandscape) {
    initMemory(virtualLandscape.getSearchSpace().getNodeNumber());
    costMatrix = ((InternalRepresentation)virtualLandscape).getIGetProblemDataEngine().getCostMatrix();
  }

  public void betterOpt(int[] tour, int[][] costMatrix, INeighborEngine neighborEngine) {
    betterOpt(tour, costMatrix, neighborEngine, false);
  }

  private void initMemory(int tourLen) {
    if (swapData.length <tourLen) {
      swapData = new int [tourLen];
    }
    if (cityPosition.length <tourLen) {
      cityPosition = new int [tourLen];
    }
    if (bit_flags.length<tourLen) {
      bit_flags = new boolean[tourLen];
    }
  }

  /*
    FUNCTION:       3(+2)-opt the tour
    INPUT:          pointer to the tour that is to optimize
    OUTPUT:         none
    (SIDE)EFFECTS:  tour is 3(+2)-opt
  */
  public void betterOpt(int[] tour, int[][] costMatrix, INeighborEngine neighborEngine, boolean isOnce) {

    int n = tour.length;

    if (bit_Ratio<1) java.util.Arrays.fill(bit_flags, false);

    int decrease_breaks;                  /* Stores decrease by breaking two edges (a,b) (c,d) */

    int i, h, g;

    int nn_ls0, nn_ls1;

    boolean improvement_flag = true;
    int randStartID = 0;

    while ( improvement_flag ) {
      for ( i = 0 ; i < n ; i++ ) {
        cityPosition[tour[i]] = i;
      }
      improvement_flag = false;

      randStartID = RandomGenerator.intRangeRandom(n);
      for (i = 0; i < n; i++) {
        if (improvement_flag) {
          if (isOnce) return;

          if (bit_Ratio<1)
          for (int j=0; j<cityIDs.length; j++) {
            bit_flags[cityIDs[j]] = false;
          }
          break;
        }

        c_tour_IDs[0] = BasicArray.getPeriodID(n, randStartID+i);  /* first ID in tour */
        c_city_IDs[0] = tour[c_tour_IDs[0]];   /* first city */
        if (Math.random()>bit_Ratio && bit_flags[c_city_IDs[0]]) continue;

        s_tour_IDs[0] = BasicArray.getSuccessorID(n, c_tour_IDs[0]);

        h = 0;    /* Search for one of the h-nearest neighbors */
        nn_ls0 = neighborEngine.getElementNumberAt(c_city_IDs[0]);
        while ( h < nn_ls0 ) {
          if (improvement_flag) {
            break;
          }
          c_city_IDs[1] = neighborEngine.getNNArrayAt(c_city_IDs[0])[h]; /* second city*/
          c_tour_IDs[1] = cityPosition[c_city_IDs[1]];       /* second ID in tour*/
          s_tour_IDs[1] = BasicArray.getSuccessorID(n, c_tour_IDs[1]);
          p_tour_IDs[1] = BasicArray.getPrecessorID(n, c_tour_IDs[1]);
          h++;
          if (c_tour_IDs[1]==s_tour_IDs[0]) {
            continue;
          }

          /* Here a fixed radius neighbor search is performed */
          if ( costMatrix[c_city_IDs[0]][tour[s_tour_IDs[0]]] < costMatrix[c_city_IDs[0]][c_city_IDs[1]] ) {
            continue;
          }

          /* Now perform the innermost search */
          g = 0;
          nn_ls1 = neighborEngine.getElementNumberAt(tour[s_tour_IDs[0]]);
          decrease_breaks = costMatrix[c_city_IDs[0]][tour[s_tour_IDs[0]]];
          decrease_breaks += min(costMatrix[tour[s_tour_IDs[1]]][c_city_IDs[1]], costMatrix[tour[p_tour_IDs[1]]][c_city_IDs[1]]);
          decrease_breaks = costMatrix[c_city_IDs[0]][c_city_IDs[1]]-decrease_breaks;
          while (g < nn_ls1) {
            c_city_IDs[2] = neighborEngine.getNNArrayAt(tour[s_tour_IDs[0]])[g]; /* third city*/
            g++;
            c_tour_IDs[2] = cityPosition[c_city_IDs[2]]; /* third ID in tour*/
            s_tour_IDs[2] = BasicArray.getSuccessorID(n, c_tour_IDs[2]);
            p_tour_IDs[2] = BasicArray.getPrecessorID(n, c_tour_IDs[2]);

            if (c_tour_IDs[2] == c_tour_IDs[0]||c_tour_IDs[2]==c_tour_IDs[1]) {
              continue;
            }

            /* Perform fixed radius neighbor search for innermost search */
            if (decrease_breaks+costMatrix[tour[s_tour_IDs[0]]][c_city_IDs[2]]>0) {
              continue;
            }

            //try 3Opt cases
            if (isReverse(c_tour_IDs)) {
              //case 2:
              improvement_flag = try3opt_OneCase(tour, costMatrix, c_tour_IDs[0], p_tour_IDs[2], c_tour_IDs[1], 2);
              if (improvement_flag) break;
            } else {
              //case 1:
              improvement_flag = try3opt_OneCase(tour, costMatrix, c_tour_IDs[0], p_tour_IDs[1], p_tour_IDs[2], 1);
              if (improvement_flag) break;
              //case 3:
              improvement_flag = try3opt_OneCase(tour, costMatrix, c_tour_IDs[0], c_tour_IDs[1], c_tour_IDs[2], 3);
              if (improvement_flag) break;
              //case 4:
              improvement_flag = try3opt_OneCase(tour, costMatrix, c_tour_IDs[0], p_tour_IDs[1], c_tour_IDs[2], 4);
              if (improvement_flag) break;
            }
          }
        }
        if (bit_Ratio<1) bit_flags[c_city_IDs[0]] = true;
      }
    }
  }

  //get forward order of c_tour_IDs[]
  private boolean isReverse(int[] c_tour_IDs) {
    if (c_tour_IDs[1] > c_tour_IDs[0]) {
      if (c_tour_IDs[2] < c_tour_IDs[1] && c_tour_IDs[2] > c_tour_IDs[0]) {
        return true;
      }
    } else {
      if (c_tour_IDs[2] > c_tour_IDs[0] || c_tour_IDs[2] < c_tour_IDs[1]) {
        return true;
      }
    }
    return false;
  }

  private void toTourIDs(int[] tourIDs, int minV, int midV, int maxV, int[] tour) {
    int tourLen = tour.length;
    tourIDs[0] = minV;                                           //cityX1AID
    tourIDs[2] = midV;                                           //cityX2AID
    tourIDs[4] = maxV;                                           //cityX3AID
    tourIDs[1] = BasicArray.getSuccessorID(tourLen, tourIDs[0]); //cityX1BID
    tourIDs[3] = BasicArray.getSuccessorID(tourLen, tourIDs[2]); //cityX2BID
    tourIDs[5] = BasicArray.getSuccessorID(tourLen, tourIDs[4]); //cityX3BID
  }

  private int getBias(int minV, int midV, int maxV) {
    if (minV>midV) return 1;
    if (midV>maxV) return 2;
    return 0;
  }

  private void toCityIDs(int[] cityIDs, int[] tour, int[] tourIDs) {
    for (int i=0; i<tourIDs.length; i++) {
      cityIDs[i] = tour[tourIDs[i]];
    }
  }

  private int[] tourIDs = new int[6];
  private int[] cityIDs = new int[6];
  public boolean try3opt_OneCase(int[] tour, int[][] costMatrix, int minV, int midV, int maxV, int optCase) {
    toTourIDs(tourIDs, minV, midV, maxV, tour);
    toCityIDs(cityIDs, tour, tourIDs);

    int diff = get3EdgeChangeDiffValue(costMatrix,
               cityIDs[0], cityIDs[1],
               cityIDs[2], cityIDs[3],
               cityIDs[4], cityIDs[5], optCase);
    if (diff < 0) {
      int bias = getBias(minV, midV, maxV);
      int biasCase = optCase;
      if (optCase<4) {
        biasCase = (3+optCase-1-bias)%3+1;
      }
      interalSorted3Exchange(tour, tourIDs[2*(bias%3)], tourIDs[2*(bias%3)+1], tourIDs[2*((1+bias)%3)], tourIDs[2*((1+bias)%3)+1], tourIDs[2*((2+bias)%3)], tourIDs[2*((2+bias)%3)+1], biasCase);
      return true;
    }
    return false;
  }


  // From Edge(cityX1A, cityX1B)+Edge(cityX2A, cityX2B)+Edge(cityX3A, cityX3B)
  // ->Case 1: Edge(cityX1A, cityX2B)+Edge(cityX2A, cityX3A)+Edge(cityX3B, cityX1B)
  // ->Case 2: Edge(cityX1A, cityX3A)+Edge(cityX2A, cityX3B)+Edge(cityX1B, cityX2B)
  // ->Case 3: Edge(cityX1A, cityX2A)+Edge(cityX2B, cityX3B)+Edge(cityX3A, cityX1B)
  // ->Case 4: Edge(cityX1A, cityX2B)+Edge(cityX2A, cityX3B)+Edge(cityX3A, cityX1B)
  public int get3EdgeChangeDiffValue(int[][] costMatrix, int cityX1A, int cityX1B, int cityX2A, int cityX2B, int cityX3A, int cityX3B, int changeCase) {
    int oldEdgesLength =
            costMatrix[cityX1A][cityX1B]+
            costMatrix[cityX2A][cityX2B]+
            costMatrix[cityX3A][cityX3B];
    int newEdgesLength = Integer.MAX_VALUE;
    switch (changeCase) {
      case 1:
        newEdgesLength =
            costMatrix[cityX1A][cityX2B] +
            costMatrix[cityX2A][cityX3A] +
            costMatrix[cityX3B][cityX1B];
        break;
      case 2:
        newEdgesLength =
            costMatrix[cityX1A][cityX3A] +
            costMatrix[cityX2A][cityX3B] +
            costMatrix[cityX1B][cityX2B];
        break;
      case 3:
        newEdgesLength =
            costMatrix[cityX1A][cityX2A] +
            costMatrix[cityX2B][cityX3B] +
            costMatrix[cityX3A][cityX1B];
        break;
      case 4:
        newEdgesLength =
            costMatrix[cityX1A][cityX2B] +
            costMatrix[cityX2A][cityX3B] +
            costMatrix[cityX3A][cityX1B];
        break;
      default:
        return Integer.MAX_VALUE;
    }
    return (newEdgesLength - oldEdgesLength);
  }

  // From Edge(cityX1A, cityX1B)+Edge(cityX2A, cityX2B)+Edge(cityX3A, cityX3B)
  // ->Case 1: Edge(cityX1A, cityX2B)+Edge(cityX2A, cityX3A)+Edge(cityX3B, cityX1B)
  // ->Case 2: Edge(cityX1A, cityX3A)+Edge(cityX2A, cityX3B)+Edge(cityX1B, cityX2B)
  // ->Case 3: Edge(cityX1A, cityX2A)+Edge(cityX2B, cityX3B)+Edge(cityX3A, cityX1B)
  // ->Case 4: Edge(cityX1A, cityX2B)+Edge(cityX2A, cityX3B)+Edge(cityX3A, cityX1B)
  public void interalSorted3Exchange(int[] tour, int cityX1AID, int cityX1BID, int cityX2AID, int cityX2BID, int cityX3AID, int cityX3BID, int exchangeCase) {
    switch (exchangeCase) {
      case 1:
        ArrayOperator.inverseSegment(tour, cityX1BID, cityX2AID);
        break;
      case 2:
        ArrayOperator.inverseSegment(tour, cityX2BID, cityX3AID);
        break;
      case 3:
        ArrayOperator.inverseSegment(tour, cityX3BID, cityX1AID);
        break;
      case 4:
        break;
      default:
        return;
    }
    exchange2NeighborSegments(tour, cityX1AID, cityX2AID, cityX3AID);
  }

  //To exchange the segment (MinID+1 -> MidID) and the segment (MidID+1->MaxID)
  //Here minID<midID<maxID
  private int[] swapData = new int [0];
  public void exchange2NeighborSegments(int[] srcData, int minID, int midID, int maxID) {
    System.arraycopy(srcData, minID+1, swapData, 0, midID-minID);
    System.arraycopy(srcData, midID+1, srcData, minID+1, maxID-midID);
    System.arraycopy(swapData, 0, srcData, maxID-midID+minID+1, midID-minID);
  }

}
