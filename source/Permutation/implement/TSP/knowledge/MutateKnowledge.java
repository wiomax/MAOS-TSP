/**
 * Description: The description of basic knolwedge for mutating edges.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005     For TSP problem
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 */

package implement.TSP.knowledge;

public class MutateKnowledge {
//  public static int get2EdgeChangeDiffValue(IGetLocalCostEngine distEngine, int[] tour, int node1AID, int node2AID) {
//    int n = tour.length;
//    return get2EdgeChangeDiffValue(distEngine,
//      tour[node1AID],
//      tour[BasicArray.getSuccessorID(n, node1AID)],
//      tour[node2AID],
//      tour[BasicArray.getSuccessorID(n, node2AID)]);
//  }

  // From Edge(node1A, node1B)+Edge(node2A, node2B)
  //   -> Edge(node1A, node2A)+Edge(node1B, node2B)
  public static int get2EdgeChangeDiffValue(int[][] costMatrix, int node1A, int node1B, int node2A, int node2B) {
    return  costMatrix[node1A][node2A]
          + costMatrix[node1B][node2B]
          - costMatrix[node1A][node1B]
          - costMatrix[node2A][node2B];
  }

//  public static int get2EdgeChangeDiffValue(int[][] costMatrix, int node1A, int node1B, int node2A, int node2B) {
//    return  distEngine.getLocalCost(node1A, node2A)
//          + distEngine.getLocalCost(node1B, node2B)
//          - distEngine.getLocalCost(node1A, node1B)
//          - distEngine.getLocalCost(node2A, node2B);
//  }

  // From Edge(node1A, node1B)+Edge(node2A, node2B)
  //   -> Edge(node1A, node2A)+Edge(node1B, node2B)
  //  while neglecting Edge(node1A, node1B)
//  public static int getPartial2EdgeChangeDiffValue(IGetLocalCostEngine distEngine, int node1A, int node1B, int node2A, int node2B) {
//    return  distEngine.getLocalCost(node1B, node2B)
//          - distEngine.getLocalCost(node1A, node1B)
//          - distEngine.getLocalCost(node2A, node2B);
//  }
}
