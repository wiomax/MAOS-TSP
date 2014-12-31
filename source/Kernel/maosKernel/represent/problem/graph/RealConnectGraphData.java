/**
 * Description: provide the information for the graph data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006     
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.represent.problem.graph;

import  maosKernel.represent.problem.*;
import Global.basic.data.collection.*;

public class RealConnectGraphData extends AbsProblemData implements IGetGraphDataEngine {
  private int [][] fullAttackNodes;
  private boolean [][] fullAttackFlags;

  public RealConnectGraphData(BasicConnectGraphData graphData) {
    int nodeNumber = graphData.getNodeNumber();
    fullAttackFlags = new boolean[nodeNumber][nodeNumber];
    graphData.getAttackFlagsMatrix(fullAttackFlags);

    fullAttackNodes = new int[nodeNumber][];
    fullAttackNodes = BasicConnectGraphData.getAttackNodesMatrix(fullAttackFlags);
//    getDegreeDistribution();
  }

  public void getDegreeDistribution() {
    int[] degrees = new int[fullAttackNodes.length];
    for (int i=0; i<degrees.length; i++) {
      degrees[i] = this.getSaturationDegreeAt(i);
      System.out.println(degrees[i]);
    }
    java.util.Arrays.sort(degrees);
    IArray segmentIDArray = new IArray(degrees.length);
    IArray segmentWVArray = new IArray(degrees.length);
    BasicCollection.toSegments(segmentIDArray, segmentWVArray, degrees);
    for (int i=0; i<segmentIDArray.getSize()-1; i++) {
      System.out.println(segmentWVArray.getElementAt(i)/(double)degrees.length+" \t"+segmentIDArray.getElementAt(i+1)/(double)degrees.length);
    }
    System.exit(0);
  }

  protected void removeNodes(IAlienICollectionEngine tobeReducedIDs) {
    int nodeNumber = getNodeNumber();
    int newSize = nodeNumber-tobeReducedIDs.getSize();
    boolean[][] newFullAttackFlags = new boolean[newSize][newSize];
    int iIndex=0, jIndex = 0;

    for (int i=0; i<newSize; i++) {
      while (tobeReducedIDs.getElementID(iIndex)!=-1) {
        iIndex++;
      }
      jIndex = 0;
      for (int j=0; j<newSize; j++) {
        while (tobeReducedIDs.getElementID(jIndex)!=-1) {
          jIndex++;
        }
        newFullAttackFlags[i][j] = fullAttackFlags[iIndex][jIndex];
        jIndex++;
      }
      iIndex++;
    }
    fullAttackFlags = newFullAttackFlags;
    fullAttackNodes = new int[newSize][];
    fullAttackNodes = BasicConnectGraphData.getAttackNodesMatrix(fullAttackFlags);
  }

  public final boolean isAttacked(int nodeA, int nodeB) {
    return (fullAttackFlags[nodeA][nodeB]);
  }
  public boolean[][] getAttackMatrix() {
    return fullAttackFlags;
  }

  public final int[] getAttackNodesAt(int nodeID) {
    return fullAttackNodes[nodeID];
  }

  public final int getNodeNumber() {
    return fullAttackNodes.length;
  }

  public int getSaturationDegreeAt(int nodeID) {
    return getAttackNodesAt(nodeID).length;
  }
}

