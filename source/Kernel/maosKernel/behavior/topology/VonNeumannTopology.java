/**
 * Description: The grid connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 27, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import java.util.*;
import Global.basic.data.collection.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;

public class VonNeumannTopology extends AbsCycleTopology {
  private int rowNumber = -1;
  private int colNumber = -1;

  private int[][] nodeMatrix;
  private int[] nodeLocIDs;
  private IArray[] tempArrays;

  public VonNeumannTopology(){}

  protected void internalBasicInit(int nodeNumber) {
    initNodeMemory(nodeNumber);
    initMatrix(nodeNumber);
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("rowNumber", rowNumber));
    initUtility(new IntegerUtility("colNumber", colNumber));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    rowNumber = TypeConverter.toInteger(getValue("rowNumber"));
    colNumber = TypeConverter.toInteger(getValue("colNumber"));
  }

  private void initNodeMemory(int nodeNumber) {
    nodeLocIDs = new int[nodeNumber];
    tempArrays = new IArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      tempArrays[i] = new IArray(4);
    }
  }

  private void initMatrix(int nodeNumber) {
    int rowN = rowNumber, colN = colNumber;
    if (rowNumber==-1 && colNumber==-1) {
      rowN = colN = (int)Math.ceil(Math.sqrt((double)nodeNumber));
    } else if (rowNumber==-1) {
      rowN = (int)Math.ceil(nodeNumber/(double)colNumber);
    } else if (colNumber==-1) {
      colN = (int)Math.ceil(nodeNumber/(double)rowNumber);
    }
    nodeMatrix = new int[rowN][colN];
  }

  private void initConnectedNodeIDsAt(IArray tempArray, int nodeID) {
    int locID = nodeLocIDs[nodeID];
    tempArray.clear();
    int rowID = locID/nodeMatrix[0].length;
    int colID = locID%nodeMatrix[0].length;

    int candidateID;
    candidateID = nodeMatrix[(rowID-1+nodeMatrix.length)%nodeMatrix.length][colID];
    if (candidateID!=-1) {
      tempArray.addElement(candidateID);
    }
    candidateID = nodeMatrix[(rowID+1)%nodeMatrix.length][colID];
    if (candidateID!=-1) {
      tempArray.addElement(candidateID);
    }
    candidateID = nodeMatrix[rowID][(colID-1+nodeMatrix[0].length)%nodeMatrix.length];
    if (candidateID!=-1) {
      tempArray.addElement(candidateID);
    }
    candidateID = nodeMatrix[rowID][(colID+1)%nodeMatrix.length];
    if (candidateID!=-1) {
      tempArray.addElement(candidateID);
    }
  }

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return tempArrays[nodeID];
  }

  private int getMatrixSize() {
    if (nodeMatrix.length == 0)return 0;
    else return nodeMatrix.length * nodeMatrix[0].length;
  }

  public void innerInitTopology() {
    int maxSize = getMatrixSize();
    if (maxSize==0) return;
    int nodeNumber = super.getNodeNumber();
    RandomGenerator.randomDistinctSelection(nodeNumber, nodeLocIDs);
    for (int i=0; i<nodeMatrix.length; i++) {
      Arrays.fill(nodeMatrix[i], -1);
    }

    for (int i=0; i<nodeNumber; i++) {
      nodeMatrix[nodeLocIDs[i]/nodeMatrix[0].length][nodeLocIDs[i]%nodeMatrix[0].length] = i;
    }

    for (int i=0; i<nodeNumber; i++) {
      initConnectedNodeIDsAt(tempArrays[i], i);
    }
  }
}
