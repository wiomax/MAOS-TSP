/**
 * Description: The interface for get distance between two states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008 
 *
 * @version M01.00.00
 * 
 * @Reference
 * [1] Reeves, C.R. 1999. Landscapes, operators and heuristic search. Annals of 
 *     Operations Research 86:473-490.
 * [2] Watson, J.-P., L. Barbulescu, A.E. Howe, and L.D. Whitley. 1999. Algorithm 
 *     performance and problem structure for flow-shop scheduling. In National 
 *     Conference on Artificial Intelligence. Orlando, Florida, USA. 688-695.
 */


package implement.common.knowledge.distance;

import java.util.Arrays;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;

public class PrecedenceDistanceCalculator implements ICalcDistanceEngine {
  private SearchState baseState;
  private boolean[][] precedenceMatrix;
  
  public PrecedenceDistanceCalculator() {
  }
  
  private void setBaseState(SearchState baseState) {
    this.baseState = baseState;
    int nodeNumber = baseState.getNodeNumber();
    if (precedenceMatrix == null) precedenceMatrix = new boolean[nodeNumber][nodeNumber];

    for (int i=0; i<nodeNumber; i++) {
      Arrays.fill(precedenceMatrix[i], true);
    }
    
    int[] vArray = baseState.getIArray();
    
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        precedenceMatrix[vArray[i]][vArray[j]] = false;
      }
    }
  }  

  private double getDistance (SearchState referState) {
    int nodeNumber = referState.getNodeNumber();
    int distV = 0;
    
    int[] vArray = referState.getIArray();
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        if (precedenceMatrix[vArray[i]][vArray[j]]) {
          distV ++;
        }
      }
    }
    return distV;
  }
  
  // Precedence-based measure
  public double getDistance(SearchState stateA, SearchState stateB) {
    if (!stateA.equals(baseState)) {
      setBaseState(stateA);
    }
    return getDistance(stateB);
  }
  
}
