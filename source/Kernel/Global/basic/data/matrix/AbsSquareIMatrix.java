/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public abstract class AbsSquareIMatrix extends AbsRegularIMatrix implements ISquareIMatrixEngine {
  protected int[][] dataMatrix;

  protected AbsSquareIMatrix() {}

  abstract public void setValueAt(int value, int nodeI, int nodeJ);

  public int getElementNumber() {
    return getNodeNumber();
  }
}
