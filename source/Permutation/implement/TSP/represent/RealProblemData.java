/**
 * Description: provide the information for the problem data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006 
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.represent;

import maosKernel.represent.problem.*;
import Global.basic.data.matrix.*;

public final class RealProblemData extends AbsProblemData implements IGetProblemDataEngine {
  private int[][] costMatrix;

  public RealProblemData() {
    this.setDescription("Traveling Saleman Problem");
  }

  public void setCostMatrix(int[][] costMatrix) {
    this.costMatrix = costMatrix;
  }

  public void setCostMatrix(ISquareIMatrixEngine costEngine) {
    int nodeNumber = costEngine.getNodeNumber();
    costMatrix = new int[nodeNumber][nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<nodeNumber; j++) {
        costMatrix[i][j] = costEngine.getValueAt(i, j);
      }
    }
  }

  public final int[][] getCostMatrix() {
    return costMatrix;
  }

  public final int getLocalCost(int nodeA, int nodeB) {
    return costMatrix[nodeA][nodeB];
  }

  public final int getNodeNumber() {
    return costMatrix.length;
  }

  public int getGlobalCost(int[] permutations) {
    int totalLength = getLocalCost(permutations[permutations.length-1], permutations[0]);
    for (int i=1; i<permutations.length; i++) {
      totalLength += getLocalCost(permutations[i-1], permutations[i]);
    }
    return totalLength;
  }
}

