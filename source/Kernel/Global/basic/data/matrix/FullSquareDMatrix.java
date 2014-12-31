/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public class FullSquareDMatrix extends AbsSquareDMatrix {

  public FullSquareDMatrix(double[][] dataMatrix) {
    this.dataMatrix = dataMatrix;
  }

  public int getNodeNumber() {
    return dataMatrix.length;
  }

  public double[] getArrayAt(int rowIndex) {
    return dataMatrix[rowIndex];
  }

  public void setValueAt(double value, int nodeI, int nodeJ) {
    dataMatrix[nodeI][nodeJ] = value;
  }
  public double getValueAt(int nodeI, int nodeJ) {
    return dataMatrix[nodeI][nodeJ];
  }
}
