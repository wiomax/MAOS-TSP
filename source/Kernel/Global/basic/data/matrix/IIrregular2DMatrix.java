/**
* Description: provide the information for an irregular 2D matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 20, 2005
 */

package Global.basic.data.matrix;

public class IIrregular2DMatrix extends AbsIMatrix {
  protected int[][] dataMatrix;

  public IIrregular2DMatrix(int[][] dataMatrix) {
    this.dataMatrix = dataMatrix;
  }

  public int[][] getDataMatrix() {
    return dataMatrix;
  }

  public int getNodeNumber() {
    return dataMatrix.length;
  }

  public int getElementNumberAt(int index) {
    return dataMatrix[index].length;
  }

  public int[] getArrayAt(int rowIndex) {
    return dataMatrix[rowIndex];
  }

  public int getValueAt(int nodeID, int elemID) {
    return dataMatrix[nodeID][elemID];
  }

  public void setValueAt(int value, int nodeID, int elemID) {
    dataMatrix[nodeID][elemID] = value;
  }
}
