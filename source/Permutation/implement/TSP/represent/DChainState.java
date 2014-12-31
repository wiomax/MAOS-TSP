/**
 * Description: provide a doubly linked list tour
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 18, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.TSP.represent;

import Global.methods.*;

public final class DChainState {
  private int[] prevNodes;
  private int[] postNodes;

  public DChainState(int nodeNumber){
    prevNodes = new int[nodeNumber];
    postNodes = new int[nodeNumber];
  }

  public boolean isNeighbor(int cityA, int cityB) {
    return prevNodes[cityA]==cityB || postNodes[cityA]==cityB;
  }
  
  public boolean isFullRepeat(DChainState dLinkPoint, int nodeID) {
    boolean isRepeat = false;
    if ((prevNodes[nodeID]==dLinkPoint.prevNodes[nodeID])) {
      isRepeat = true;
    } else if ((prevNodes[nodeID]==dLinkPoint.postNodes[nodeID])) {
      isRepeat = true;
    }
    if (!isRepeat) return false;
    isRepeat= false;
    if ((postNodes[nodeID]==dLinkPoint.postNodes[nodeID])) {
      isRepeat = true;
    } else if ((postNodes[nodeID]==dLinkPoint.prevNodes[nodeID])) {
      isRepeat = true;
    }
    return isRepeat;
  }

  public int getRepeatEdgeNumberAt(DChainState dLinkPoint, int nodeID) {
    int count = 0;
    if ((prevNodes[nodeID]==dLinkPoint.prevNodes[nodeID])) {
      count ++;
    } else if ((prevNodes[nodeID]==dLinkPoint.postNodes[nodeID])) {
      count ++;
    }
    if ((postNodes[nodeID]==dLinkPoint.postNodes[nodeID])) {
      count ++;
    } else if ((postNodes[nodeID]==dLinkPoint.prevNodes[nodeID])) {
      count ++;
    }
    return count;
  }

  public int getSameEdgeNumber(DChainState dLinkPoint) {
    int nodeNumber = getNodeNumber();
    int count = 0;
    for(int i=0; i<nodeNumber; i++) {
      count += getRepeatEdgeNumberAt(dLinkPoint, i);
    }
    return count/2;
  }

  public int getDiffEdgeNumber(DChainState dLinkPoint) {
    int nodeNumber = getNodeNumber();
    return nodeNumber-getSameEdgeNumber(dLinkPoint);
  }

  public void importPoint(int[] tourPoint) {
    int nodeNumber = tourPoint.length;
    int currID;
    for( int j = 0; j < nodeNumber; ++j ) {
      currID = tourPoint[j];
      prevNodes[currID]=tourPoint[BasicArray.getPrecessorID(nodeNumber, j)];
      postNodes[currID]=tourPoint[BasicArray.getSuccessorID(nodeNumber, j)];
    }
  }

  public void importPoint(DChainState dLinkPoint) {
    System.arraycopy(dLinkPoint.prevNodes, 0, prevNodes, 0, this.getNodeNumber());
    System.arraycopy(dLinkPoint.postNodes, 0, postNodes, 0, this.getNodeNumber());
  }

  public int getNextID(int preID, int currID) {
    if(prevNodes[currID]!=preID) return prevNodes[currID];
    else return postNodes[currID];
  }

  public void toBasicPoint(int[] tourPoint) {
    int nodeNumber = getNodeNumber();
    int preID = -1;
    int oCurrID = -1;
    int currID = RandomGenerator.intRangeRandom(nodeNumber);
    tourPoint[0] = currID;
    for(int i=1; i<nodeNumber; i++) {
      oCurrID = currID;
      currID = getNextID(preID, currID);
      preID = oCurrID;
      tourPoint[i] = currID;
    }
  }

  private int swapNodeID;
  public void swapChainAt(int cityID) {
    swapNodeID = prevNodes[cityID];
    prevNodes[cityID] = postNodes[cityID];
    postNodes[cityID] = swapNodeID;
  }

  public int getPreCityIDAt(int cityID) {
    return prevNodes[cityID];
  }

  public int getPostCityIDAt(int cityID) {
    return postNodes[cityID];
  }

  public int getNodeNumber() {
    return prevNodes.length;
  }
  // From Edge(node1A, node1B)+Edge(node2A, node2B) -> Edge(node1A, node2A)+Edge(node1B, node2B)
  // Usually for a) joining two cycles or b) breaking one cycle
  // @params: node1A, node1B, node2A, node2B are IDs of cities
  public void changeSolForTwoEdges(DChainState chainState, int node1A , int node1B, int node2A, int node2B) {
    chainState.changeChainCityIDAt(node1A, node1B, node2A);
    chainState.changeChainCityIDAt(node1B, node1A, node2B);
    chainState.changeChainCityIDAt(node2A, node2B, node1A);
    chainState.changeChainCityIDAt(node2B, node2A, node1B);
  }

  public void changeChainCityIDAt(int cityID, int oldID, int newID) {
    if(prevNodes[cityID]==oldID) prevNodes[cityID]=newID;
    else postNodes[cityID]=newID;
  }

//  //abCycle: the even edges are removed; and the odd edges are added
//  //From a single cycle -> multi cycles
//  private int r1,r2, j1,j2, n_cycle;
//  public void changeSolForABCycle(int[] abCycle) {
////    GlobalTools.CPUTimeCostCounter.setStart("changeSolForABCycle1");
//    n_cycle = abCycle.length;
//
//    for(j1=0;j1<n_cycle/2;j1++) {
//      j2 = 2*j1;
//      r1=abCycle[1+j2];r2=abCycle[(2+j2)%n_cycle];
//
//      changeChainCityIDAt(r1, r2, abCycle[  j2]);
//      changeChainCityIDAt(r2, r1, abCycle[(3+j2)%n_cycle]);
//    }
////    GlobalTools.CPUTimeCostCounter.setEnd("changeSolForABCycle1");
//  }
}
