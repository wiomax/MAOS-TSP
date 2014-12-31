/**
 * Description: process a Symmetry matrix with int data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2005    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.basic.data.matrix;

public class SymmetryIMatrix extends AbsSquareIMatrix {
  public SymmetryIMatrix(int size) {
    initDataMatrix(size);
  }

  private void initDataMatrix(int matrixSize) {
    if (matrixSize<0) return;
    dataMatrix = new int[matrixSize][];
    for (int i=0; i<matrixSize; i++) {
      dataMatrix[i] = new int[i+1];
    }
  }

  public int getNodeNumber() {
    return dataMatrix.length;
  }

  public int[] getArrayAt(int rowIndex) {
    int[] dataArray = new int[this.getNodeNumber()];
    for (int i=0; i<dataArray.length; i++) {
      dataArray[i] = getValueAt(rowIndex, i);
    }
    return dataArray;
  }

  public void setValueAt(int value, int nodeI, int nodeJ) {
    if (nodeI>=nodeJ) {
      dataMatrix[nodeI][nodeJ] = value;
    } else {
      dataMatrix[nodeJ][nodeI] = value;
    }
  }

  public int getValueAt(int nodeI, int nodeJ) {
    if (nodeI>=nodeJ) {
      return dataMatrix[nodeI][nodeJ];
    } else {
      return dataMatrix[nodeJ][nodeI];
    }
  }
}
