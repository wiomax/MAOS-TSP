/**
 * Description: process a Symmetry matrix with boolean data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2005    xiaofengxie@tsinghua.org.cn
 * Xiaofeng Xie    Sep 09, 2006    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.basic.data.matrix;

import java.util.*;

public class SymmetryBMatrix {
  protected boolean[][] dataMatrix;

  public SymmetryBMatrix(int matrixSize) {
    if (matrixSize<0) return;
    dataMatrix = new boolean[matrixSize][];
    for (int i=0; i<matrixSize; i++) {
      dataMatrix[i] = new boolean[i+1];
    }
  }

  public void initAll(boolean value) {
    for (int i = 0; i < getNodeNumber(); i++) {
      Arrays.fill(dataMatrix[i], value);
    }
  }

  public void initNode(int nodeID, boolean value) {
    for (int i=0; i<nodeID; i++) {
      dataMatrix[nodeID][i] = value;
    }
    for (int i=nodeID+1; i<getNodeNumber(); i++) {
      dataMatrix[i][nodeID] = value;
    }
  }

  public int getNodeNumber() {
    return dataMatrix.length;
  }

  public void setValueAt(boolean value, int rowID, int colID) {
    if (rowID>=colID) {
      dataMatrix[rowID][colID] = value;
    } else {
      dataMatrix[colID][rowID] = value;
    }
  }

  public boolean getValueAt(int rowID, int colID) {
    if (rowID>=colID) {
      return dataMatrix[rowID][colID];
    } else {
      return dataMatrix[colID][rowID];
    }
  }
}
