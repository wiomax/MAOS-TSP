/**
 * Description: process a Symmetry matrix with double data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2005    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.basic.data.matrix;

public class SymmetryDMatrix extends AbsSquareDMatrix {
  private boolean withDiagonal = false;

  public SymmetryDMatrix(int size) {
    initDataMatrix(size);
  }

  public SymmetryDMatrix(int size, boolean withDiagonal) {
    this.withDiagonal = withDiagonal;
    initDataMatrix(size);
  }

  private void initDataMatrix(int size) {
    int matrixSize = size-getDiagonalBias();
    if (matrixSize<0) return;
    dataMatrix = new double[matrixSize][];
    for (int i=0; i<matrixSize; i++) {
      dataMatrix[i] = new double[i];
    }
  }

  private int getDiagonalBias() {
    if(withDiagonal) return 0;
    else return 1;
  }

  public int getNodeNumber() {
    return dataMatrix.length+getDiagonalBias();
  }

  public double[] getArrayAt(int rowIndex) {
    double[] dataArray = new double[this.getNodeNumber()];
    for (int i=0; i<dataArray.length; i++) {
      dataArray[i] = getValueAt(rowIndex, i);
    }
    return dataArray;
  }

  public void setSymmatryValueAt(double value, int nodeI, int nodeJ) {
    setValueAt(value, nodeI, nodeJ);
  }

  public void setValueAt(double value, int nodeI, int nodeJ) {
    int bias = getDiagonalBias();
    if (nodeI>=nodeJ) {
      dataMatrix[nodeI-bias][nodeJ] = value;
    } else {
      dataMatrix[nodeJ][nodeI-bias] = value;
    }
  }

  public double getValueAt(int nodeI, int nodeJ) {
    int bias = getDiagonalBias();
    if (nodeI>=nodeJ) {
      return dataMatrix[nodeI-bias][nodeJ];
    } else {
      return dataMatrix[nodeJ][nodeI-bias];
    }
  }
}
