/**
 * Description: The static methods for get global cost and local cost between two nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.represent;

public class CostMethods {
	
  public static double getLocalCost(int nodeA, int nodeB, IGetCostMatrixEngine dataStorager) {
    int[][] costMatrix = dataStorager.getCostMatrix();
  	return costMatrix[nodeA][nodeB];
  }

  public static double getGlobalCost(int[] permutations, IGetLocalCostEngine localCostEngine) {
  	double totalLength = localCostEngine.getLocalCost(permutations[permutations.length-1], permutations[0]);
    for (int i=1; i<permutations.length; i++) {
      totalLength += localCostEngine.getLocalCost(permutations[i-1], permutations[i]);
    }
    return totalLength;
  }
}
