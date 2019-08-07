/**
 * The Prim's Algorithm obtains a minimum spanning tree.
 *
 * @ Author        Create/Modi     Note
 * Keld Helsgaun   Jun XX, 2002
 * Xiaofeng Xie    May 21, 2005    Adapt it into MAOS
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 * Xiaofeng Xie    May 21, 2009    MAOS M01.00.02
 *
 * @version M01.00.02
 * @since M01.00.00
 * 
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 * [2] R. C. Prim: Shortest connection networks and some generalizations. 
 *     In: Bell System Technical Journal, 36 (1957), pp. 1389-1401
 */

package implement.TSP.knowledge;

import java.util.*;
import Global.basic.data.collection.*;
import Global.basic.data.matrix.*;
import Global.methods.RandomGenerator;

public class PrimeMSTSolver implements IGetSpanningTreeEngine {
  private DualIAlienArray unusedNodeSet;
  private int[] minNodeArray;
  private int[] minCostArray;

  public PrimeMSTSolver(int nodeNumber) {
    minNodeArray = new int[nodeNumber];
    minCostArray = new int[nodeNumber];
    unusedNodeSet = new DualIAlienArray(nodeNumber);
  }

  /**
  * @ May be improved by binary heap in O(E*log(V)) or Fibonacci heap in O(E + V log V)
  *
  * Step 0: Pick any vertex as a starting vertex. (Call it S). Mark it with any
  *  given colour, say red.
  * Step 1:  Find the nearest neighbour of S (call it P1). Mark both P1 and the
  *  edge SP1 red. cheapest unmarked (uncoloured) edge in the graph that doesn't
  *  close a coloured circuit. Mark this edge with same colour of Step 1.
  * Step 2  Find the nearest uncoloured neighbour to the red subgraph (i.e., the
  *  closest vertex to any red vertex). Mark it and the edge connecting the vertex
  *  to the red subgraph in red.
  * Step 3 Repeat Step 2 until all vertices are marked red. The red subgraph is a
  *  minimum spanning tree.
  */

  public double getSpanningTree(SpanningTree spanningTree, ISquareIMatrixEngine costMatrix) {
    int n = costMatrix.getNodeNumber();
    Arrays.fill(minNodeArray, -1);
    Arrays.fill(minCostArray, Integer.MAX_VALUE);
    unusedNodeSet.clear();
    for (int i=0; i<n; i++) {
      unusedNodeSet.addElement(i);
    }
    
    int curSize = n;
    int compCost, compID=-1, minID = -1;
    int sumCost=0, minCost;
    
    /* select arbitrary point as starting point */
    int curID = RandomGenerator.intRangeRandom(n);
    spanningTree.setRootNode(curID);
    while(true) {
      unusedNodeSet.removeElement(curID);
      curSize = unusedNodeSet.getSize();
      if (curSize==0) break;

      minCost = Integer.MAX_VALUE;
      for (int i=0; i<curSize; i++) {
        compID = unusedNodeSet.getElementAt(i);
        compCost = costMatrix.getValueAt(compID, curID);
        
        if (compCost < minCostArray[compID] || ((compCost == minCostArray[compID]) && (curID<minNodeArray[compID]))) {
          minCostArray[compID] = compCost;
          minNodeArray[compID] = curID;
        }
        
        //obtain min node, can be improved by using Heap
        if ((minCostArray[compID] < minCost) || ((minCostArray[compID] == minCost) && ((minNodeArray[compID]<minNodeArray[minID])||((minNodeArray[compID]==minNodeArray[minID]) && compID<minID)))) {
          minCost = minCostArray[compID];
          minID = compID;
        }
      }
      spanningTree.addLink(minID, minNodeArray[minID]);
      curID = minID;
      sumCost += minCost;
    }
    return sumCost;
  }
}

