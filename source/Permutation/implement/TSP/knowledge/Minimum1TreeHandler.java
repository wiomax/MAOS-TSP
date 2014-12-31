/**
 * The Minimum1TreeCost function returns the cost of a minimum 1-tree.
 *
   The minimum 1-tree is found by determining the minimum spanning tree and 
   then adding an edge corresponding to the second nearest neighbor of one 
   of the leaves of the tree (any node which has degree 1). The leaf chosen
   is the one that has the longest second nearest neighbor distance.
 *
 * @ Author        Create/Modi     Note
 * Keld Helsgaun   Jun XX, 2002
 * Xiaofeng Xie    May 21, 2005
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
import Global.basic.data.*;

public class Minimum1TreeHandler {
  private int maxCostID = SpanningTreeNode.NULL_NODE;
  private int[] min1TreeEdgeIDs;
  private int[] min1TreeEdgeCosts;

  public Minimum1TreeHandler(int nodeNumber) {
    min1TreeEdgeIDs = new int[nodeNumber];
    min1TreeEdgeCosts = new int[nodeNumber];
  }

  public void initMinimum1TreeFromSpanningTree(SpanningTree spanningTree, ISquareIMatrixEngine costMatrix) {
    int nodeNumber = spanningTree.getNodeNumber();
    int currCost = 0, /*minCost = Integer.MAX_VALUE,*/ maxCost = -1;
    for (int i = 0; i < nodeNumber; i++) {
      handleMinimum1TreeNodeAt(i, spanningTree, costMatrix);
      if (min1TreeEdgeIDs[i]!=SpanningTreeNode.NULL_NODE) {
        currCost = min1TreeEdgeCosts[i];
        if (currCost > maxCost) {
          maxCost = currCost;
          maxCostID = i;
        }
      }
    }
  }


  public BasicEdge getMax1TreeEdge() {
    return new BasicEdge(maxCostID, min1TreeEdgeIDs[maxCostID]);
  }

  public int getMax1TreeEdgeCost() {
    return min1TreeEdgeCosts[maxCostID];
  }

  private void handleMinimum1TreeNodeAt(int nodeID, SpanningTree spanningTree, ISquareIMatrixEngine costMatrix) {
    SpanningTreeNode node = spanningTree.getNodeAt(nodeID);
    if (node.getDegree() != 1) {
      min1TreeEdgeIDs[nodeID] = SpanningTreeNode.NULL_NODE;
      min1TreeEdgeCosts[nodeID] = 0;
      return;
    }
    int nodeNumber = spanningTree.getNodeNumber();
    int minCost = Integer.MAX_VALUE;
    int minCostID = SpanningTreeNode.NULL_NODE;
    int currCost;
    for (int i = 0; i < nodeNumber; i++) {
      if (i != nodeID && i != node.getParentID()) {
        currCost = costMatrix.getValueAt(nodeID, i);
        if (currCost < minCost) {
          minCost = currCost;
          minCostID = i;
        }
      }
    }
    min1TreeEdgeCosts[nodeID] = minCost;
    min1TreeEdgeIDs[nodeID] = minCostID;
  }
}

