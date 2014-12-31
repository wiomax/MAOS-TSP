/**
 * Description: The interface for get distance between two states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008 
 *
 * @version M01.00.00
 */


package implement.common.knowledge.distance;

import java.util.*;

import maosKernel.represent.landscape.*;

import maosKernel.represent.landscape.space.*;

public class AdjacencyDistanceCalculator implements ICalcDistanceEngine {
  private SearchState baseState;
  
  private boolean[][] edgeMatrix;
  
  public AdjacencyDistanceCalculator() {}
  
  private void setBaseState(SearchState baseState) {
    this.baseState = baseState;
    int nodeNumber = baseState.getNodeNumber();
    if (edgeMatrix == null) edgeMatrix = new boolean[nodeNumber][nodeNumber];

    for (int i=0; i<nodeNumber; i++) {
      Arrays.fill(edgeMatrix[i], true);
    }
    edgeMatrix[baseState.getValueAt(nodeNumber-1)][baseState.getValueAt(0)] = false;
    edgeMatrix[baseState.getValueAt(0)][baseState.getValueAt(nodeNumber-1)] = false;
    
    for (int i=1; i<nodeNumber; i++) {
      edgeMatrix[baseState.getValueAt(i-1)][baseState.getValueAt(i)] = false;
      edgeMatrix[baseState.getValueAt(i)][baseState.getValueAt(i-1)] = false;
    }
  }
  
  public double getDistance(SearchState stateA, SearchState stateB) {
    if (!stateA.equals(baseState)) {
      setBaseState(stateA);
    }
    return getDistance(stateB);
  }
  
  private double getDistance (SearchState referState) {
    int distV = 0;
    int nodeNumber = referState.getNodeNumber();
    if (edgeMatrix[referState.getValueAt(nodeNumber-1)][referState.getValueAt(0)]) distV ++;
    for (int i=1; i<nodeNumber; i++) {
      if (edgeMatrix[referState.getValueAt(i-1)] [referState.getValueAt(i)]) distV ++;
    }
    return distV;
  }
}
