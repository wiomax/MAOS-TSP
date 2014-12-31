/**
 * Description: provide the Probability information for nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 7, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Oct 08, 2006    Simplified version
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.construct;

import Global.basic.data.collection.*;
import implement.TSP.behavior.neighbor.*;
import implement.TSP.represent.*;

public class ReverseProbabilityHolder implements IGetProbabiityEngine, INodeNumberEngine {

  private double[][] probMatrix;

  private double beta = 2.0;                    /* importance of heuristic evaluation */


  public ReverseProbabilityHolder(INeighborEngine neighborEngine, IGetLocalCostEngine localCostEngine) {
    int nodeNumber = neighborEngine.getNodeNumber();
    probMatrix = new double[neighborEngine.getNodeNumber()][];
    int[][] costMatrix = localCostEngine.getCostMatrix();
    for (int i=0; i<nodeNumber; i++) {
      probMatrix[i] = new double[neighborEngine.getElementNumberAt(i)];
      int[] nnArray = neighborEngine.getNNArrayAt(i);
      for (int j = 0 ; j < probMatrix[i].length ; j++ ) {
        probMatrix[i][j] = Math.pow(1.0/costMatrix[i][nnArray[j]],beta);
      }
    }
  }

  public boolean isPartialMode() {
    return true;
  }

  public int getNodeNumber() {
    return probMatrix.length;
  }

  public double[] getProbabilityArrayAt(int index) {
    return probMatrix[index];
  }
}

