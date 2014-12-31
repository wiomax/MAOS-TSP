/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public class FullSquareIMatrix extends AbsRegularIMatrix implements ISquareIMatrixEngine {
  protected int[][] matrixData;

  public FullSquareIMatrix(int[][] matrixData) {
    this.matrixData = matrixData;
  }

  public int getNodeNumber() {
    return matrixData.length;
  }

  public int getElementNumber() {
    return getNodeNumber();
  }

  public int[] getArrayAt(int rowIndex) {
    return matrixData[rowIndex];
  }

  public int getValueAt(int nodeI, int nodeJ) {
    return matrixData[nodeI][nodeJ];
  }
}
