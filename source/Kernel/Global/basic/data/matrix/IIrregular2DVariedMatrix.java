/**
* Description: provide the information for an irregular 2D matrix with no
 *   repeated & varied elements at a node
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 20, 2005
 * Xiaofeng Xie    Nov 21, 2005    extended from IIrregular2DMatrix
 */

package Global.basic.data.matrix;

import java.util.*;
import Global.methods.*;
import Global.math.*;

public class IIrregular2DVariedMatrix extends IIrregular2DMatrix {
  protected int[] sizeArray;

  public IIrregular2DVariedMatrix(int nodeNumber, int maxElemNumber) {
    super(new int[nodeNumber][maxElemNumber]);
    sizeArray = new int[nodeNumber];
  }

  public int getTotalSize() {
    return ArrayMath.totalSum(sizeArray);
  }

  public int getElementNumberAt(int index) {
    return sizeArray[index];
  }

  public void importElemArrayAt(IIrregular2DMatrix outMatrix, int outNodeID, int inNodeID) {
    System.arraycopy(outMatrix.getArrayAt(outNodeID), 0, this.getArrayAt(inNodeID), 0, outMatrix.getElementNumberAt(outNodeID));
    this.sizeArray[inNodeID] = outMatrix.getElementNumberAt(outNodeID);
  }

  //only valid as the values in the dataMatrix is the column IDs in a regular matrix
  public IIrregular2DVariedMatrix transpose() {
    IIrregular2DVariedMatrix transposeMatrix = new IIrregular2DVariedMatrix(this.getMaxCardinality(), this.getNodeNumber());
    for (int i=0; i<this.getNodeNumber(); i++) {
      for (int j=0; j<this.getElementNumberAt(i); j++) {
        transposeMatrix.addNodeTo(i, this.getValueAt(i, j));
      }
    }
    return transposeMatrix;
  }

  public boolean addArrayData(int[] arrayData) {
    for (int i=0; i<arrayData.length; i++) {
      if(!addNodeTo(i, arrayData[i])) {
        return false;
      }
    }
    return true;
  }

  public void reset() {
    Arrays.fill(sizeArray, 0);
  }

  public void resetAt(int index) {
    sizeArray[index] = 0;
  }

  public void setSizeAt(int index, int size) {
    sizeArray[index] = size;
  }

  public boolean removeNodeAt(int elemID, int nodeID) {
    int[] inNNArray = super.getArrayAt(nodeID);
    int index = BasicArray.getExactIndex(inNNArray, elemID, 0, sizeArray[nodeID]);
    if (index != -1) {
      System.arraycopy(inNNArray, index+1, inNNArray, index, sizeArray[nodeID]-index-1);
      sizeArray[nodeID] --;
      return true;
    }
    return false;
  }

  public boolean addNodeTo(int elemID, int nodeID) {
    int[] inNNArray = super.getArrayAt(nodeID);
    if (BasicArray.getExactIndex(inNNArray, elemID, 0, sizeArray[nodeID]) == -1) {
      inNNArray[sizeArray[nodeID]] = elemID;
      sizeArray[nodeID] ++;
      return true;
    }
    return false;
  }

  public boolean addMatrix(IIrregular2DMatrix dataMatrix) {
    int nodeNumber = this.getNodeNumber();
    if (dataMatrix.getNodeNumber() != nodeNumber) {
      return false;
    }
    for (int i=0; i<nodeNumber; i++) {
      int[] outNNArray = dataMatrix.getArrayAt(i);
      for (int j=0; j<dataMatrix.getElementNumberAt(i); j++) {
        if (!addNodeTo(outNNArray[j], i)) {
          return false;
        }
      }
    }
    return true;
  }
}
