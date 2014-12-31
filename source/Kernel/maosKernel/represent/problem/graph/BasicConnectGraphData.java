/**
 * Description: provide the information for an basic graph
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005     xiaofengxie@tsinghua.org.cn
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.represent.problem.graph;

import Global.basic.*;
import Global.basic.data.*;
import Global.basic.data.collection.*;
import Global.methods.*;

public class BasicConnectGraphData extends BasicAttrib implements INodeNumberEngine {
  public int nodeNumber = 0;
  protected BasicEdge[] attackEdges = new BasicEdge[0];

  public void setAttackEdges(BasicEdge[] edges) {
    attackEdges = edges;
  }

  public int getNodeNumber() {
    return this.nodeNumber;
  }

  public boolean[][] getAttackFlagsMatrix(boolean [][] tempAttackNodes) {
    for(int i=0; i<attackEdges.length; i++) {
      tempAttackNodes[attackEdges[i].startIndex][attackEdges[i].endIndex]=true;
      tempAttackNodes[attackEdges[i].endIndex][attackEdges[i].startIndex]=true;
    }
    return tempAttackNodes;
  }

  public static int[][] getAttackNodesMatrix(boolean[][] flagsMatrix) {
    int nodeNumber = flagsMatrix.length;
    int [][] attackNodes = new int[nodeNumber][];
    for(int i=0; i<nodeNumber; i++) {
      attackNodes[i] = BasicArray.getFlagIndices(flagsMatrix[i], true);
    }
    return attackNodes;
  }
}

