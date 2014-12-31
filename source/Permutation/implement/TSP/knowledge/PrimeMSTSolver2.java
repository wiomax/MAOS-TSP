/**
 * The MinimumSpanningTree function determines a minimum spanning tree.
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
 */

package implement.TSP.knowledge;

import java.util.*;
import Global.methods.*;
import Global.basic.data.matrix.*;

public class PrimeMSTSolver2 implements IGetSpanningTreeEngine {
  private boolean[] usedFlagSet;

  public PrimeMSTSolver2(int nodeNumber) {
    usedFlagSet = new boolean[nodeNumber];
  }

  /**
  * @very slow (O(E*V)), should be improved by binary heap or Fibonacci heaps
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
  public double getSpanningTree(SpanningTree spanningTree, ISquareIMatrixEngine absDMatrix) {
    Arrays.fill(usedFlagSet, false);

    int n = absDMatrix.getNodeNumber();

    double small;
    double length, minLength = 0;
    int parentID = 0, currID = 0;

    /* select arbitrary point as starting point */
    int usedID = RandomGenerator.intRangeRandom(n);
    usedFlagSet[usedID] = true;
    spanningTree.setRootNode(usedID);

    int count = 1;
    int i, j;
    while (count < n) {
      small = Double.MAX_VALUE;

      for (i = 0; i < n; i++) {
        if (usedFlagSet[i]) {
          for (j = 0; j < n; j++) {
            if (!usedFlagSet[j]) {
              length = absDMatrix.getValueAt(i, j);

              if (length < small) {
                small = length;
                parentID = i;
                currID = j;
              }
            }
          }
        }
      }

      spanningTree.addLink(currID, parentID);
      usedFlagSet[currID] = true;

      minLength += small;
      count++;
    }
    return minLength;
  }
}

