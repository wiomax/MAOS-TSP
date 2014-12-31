/**
 * Description: attempts to find penalties (pi-values).
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
 * [2] See HLK 1.3 code
 */

package implement.TSP.knowledge;

import Global.basic.data.matrix.*;

public class PenaltyHandler {
  private PiCostMatrix piCostMatrix;

  //****************************
  private SpanningTree spanningTree;
  private AlphaMatrixManager alphaMatrixManager;
  private IGetSpanningTreeEngine getSpanningTreeEngine;
  private Minimum1TreeHandler min1TreeHandler;
  //****************************

  public PenaltyHandler(PiCostMatrix piCostMatrix) {
    this.piCostMatrix = piCostMatrix;

    int nodeNumber = piCostMatrix.getNodeNumber();
    spanningTree = new SpanningTree(nodeNumber);
    alphaMatrixManager = new AlphaMatrixManager(nodeNumber);
    min1TreeHandler = new Minimum1TreeHandler(nodeNumber);
    getSpanningTreeEngine = new PrimeMSTSolver(nodeNumber);
  }

  public void setIGetSpanningTreeEngine(IGetSpanningTreeEngine getSpanningTreeEngine) {
    this.getSpanningTreeEngine = getSpanningTreeEngine;
  }

  public ISquareIMatrixEngine getAlphaMatrix() {
    alphaMatrixManager.toAlphaMatrix(spanningTree, piCostMatrix);
    return alphaMatrixManager.getAlphaMatrix();
  }

  public ISquareIMatrixEngine getPenaltiedMatrix() {
    return piCostMatrix.getCostMatrix();
  }

  /*
   The getPublicW function returns the cost of a minimum 1-tree.
  */
  public double getPublicW() {
    return getPrivateW()/(double)piCostMatrix.getPrecision();
  }

  public int getPrivateW() {
    return spanningTree.getTotalCost(piCostMatrix)
           + min1TreeHandler.getMax1TreeEdgeCost()
           - piCostMatrix.getPenaltyValue();
  }

  /*
   The minimum 1-tree is found by determining the minimum spanning tree and
   then adding an edge corresponding to the second nearest neighbor of one
   of the leaves of the tree (any node which has degree 1). The leaf chosen
   is the one that has the longest second nearest neighbor distance.
  */
  public void creatMinimum1Tree() {
    spanningTree.clear();
    getSpanningTreeEngine.getSpanningTree(spanningTree, piCostMatrix);
    min1TreeHandler.initMinimum1TreeFromSpanningTree(spanningTree, piCostMatrix);
  }

  public int getNodeNumber() {
    return piCostMatrix.getNodeNumber();
  }
}
