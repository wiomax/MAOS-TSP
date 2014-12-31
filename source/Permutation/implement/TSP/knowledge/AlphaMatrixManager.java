/**
 * Description: The candidate edges of each node are ordered according to
 *  their alpha-values.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 22, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 */

package implement.TSP.knowledge;

import Global.basic.data.matrix.*;

public class AlphaMatrixManager {
  private int[][] betaMatrix;  //temp value for speed up from O(n^3)->O(n^2)
  private int[][] alphaMatrix;

  public AlphaMatrixManager(int nodeNumber) {
    betaMatrix = new int[nodeNumber][nodeNumber];
    alphaMatrix = new int[nodeNumber][nodeNumber];
  }

  public FullSquareIMatrix getAlphaMatrix() {
    return new FullSquareIMatrix(alphaMatrix);
  }

  public void toAlphaMatrix(SpanningTree spanningTree, ISquareIMatrixEngine costMatrix) {
    int[] nodeReferIDArray = spanningTree.getSequenceArray();
    int nodeNumber = spanningTree.getNodeNumber();
    int h, k, parentID = 0;
    for (int i=0; i<nodeNumber; i++) {
      h = nodeReferIDArray[i];
      for (int j=i+1; j<nodeNumber; j++) {
        k = nodeReferIDArray[j];
        if (spanningTree.isDirectedEdge(h, k)) {
          betaMatrix[h][k] = costMatrix.getValueAt(h, k);
        } else {
          parentID = spanningTree.getNodeAt(k).getParentID();
          betaMatrix[h][k] = Math.max(betaMatrix[h][parentID], costMatrix.getValueAt(k, parentID));
        }
        betaMatrix[k][h] = betaMatrix[h][k];

        alphaMatrix[h][k] = costMatrix.getValueAt(h, k)-betaMatrix[h][k];
        alphaMatrix[k][h] = alphaMatrix[h][k];
      }
    }
  }
}

