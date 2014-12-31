/**
 * Description: provide a spanning tree: a spanning tree of a graph G
  * with n nodes is a tree with n-1 edges from G.
 *
 * @ Author        Create/Modi     Note
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

import Global.math.*;
import Global.methods.*;
import Global.basic.data.matrix.*;
import maosKernel.represent.landscape.space.*;

public class SpanningTree {
  protected int rootID = -1;
  int[] nodeReferIDArray;
  protected SpanningTreeNode[] treeNodes;

  public SpanningTree(int nodeNumber){
    nodeReferIDArray = new int[nodeNumber];
    treeNodes = new SpanningTreeNode[nodeNumber];
    for(int i=0; i<treeNodes.length; i++) {
      treeNodes[i] = new SpanningTreeNode();
    }
  }

  private int getTotalCost(ISquareIMatrixEngine distEngine, int nodeID, SpanningTreeNode node) {
    int distance = 0;
    int parentID = node.getParentID();
    if (parentID != SpanningTreeNode.NULL_NODE) {
      distance += distEngine.getValueAt(parentID, nodeID);
    }
    int childID;
    for(int i=0; i<node.getChildSize(); i++) {
      childID = node.getChildIDAt(i);
      distance += getTotalCost(distEngine, childID, getNodeAt(childID));
    }
    return distance;
  }

  public int getTotalCost(ISquareIMatrixEngine distEngine) {
    return getTotalCost(distEngine, rootID, this.getNodeAt(rootID));
  }

  public int[] getSequenceArray() {
    fillSequenceArray(0, rootID);
    return nodeReferIDArray;
  }

  private int fillSequenceArray(int count, int nodeID) {
    nodeReferIDArray[count] = nodeID;
    count ++;
    SpanningTreeNode node = this.getNodeAt(nodeID);
    for(int i=0; i<node.getChildSize(); i++) {
      count = fillSequenceArray(count, node.getChildIDAt(i));
    }
    return count;
  }

  public int getRootID() {
    return rootID;
  }

  public void clear() {
    rootID = -1;
    for(int i=0; i<treeNodes.length; i++) {
      treeNodes[i].clear();
    }
  }

  public int get_Norm_Value() {
    int[] vValues = new int[getNodeNumber()];
    for(int i=0; i<vValues.length; i++) {
      vValues[i] = treeNodes[i].get_V_Value();
    }
    return ArrayMath.squareSum(vValues);
  }

  public void initTourState(SearchState tourPoint){
    int nodeNumber = getNodeNumber();
    int currRefID= 0;
    int parentID, currID;

    currID = tourPoint.getValueAt(currRefID);

    setRootNode(currID);

    for(int i=1; i <nodeNumber; i++) {
      parentID = currID;
      currRefID = BasicArray.getSuccessorID(nodeNumber, currRefID);
      currID = tourPoint.getValueAt(currRefID);
      addLink(currID, parentID);
    }
  }

  public void addLink(int nodeID, int parentID) {
    treeNodes[parentID].addChild(nodeID);
    treeNodes[nodeID].setParentID(parentID);
  }

  public void setRootNode(int nodeIndex) {
    this.rootID = nodeIndex;
    treeNodes[nodeIndex].setParentID(SpanningTreeNode.NULL_NODE);
  }

  public SpanningTreeNode getNodeAt(int index) {
    return treeNodes[index];
  }

  public boolean isDirectedEdge(int startID, int endID) {
    return(treeNodes[endID].parentID==startID);
  }

  public boolean isIndirectedEdge(int startID, int endID) {
    return(isDirectedEdge(startID, endID)|| isDirectedEdge(endID, startID));
  }

  public int getNodeNumber() {
    return treeNodes.length;
  }

  public boolean isValid() {
    return true;
  }
}
