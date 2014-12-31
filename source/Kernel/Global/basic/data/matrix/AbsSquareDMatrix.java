/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public abstract class AbsSquareDMatrix extends AbsRegularDMatrix implements ISquareDMatrixEngine {
  protected double[][] dataMatrix;

  protected AbsSquareDMatrix() {}

  public void setSymmatryValueAt(double value, int nodeI, int nodeJ) {
    setValueAt(value, nodeI, nodeJ);
    setValueAt(value, nodeJ, nodeI);
  }

  abstract public void setValueAt(double value, int nodeI, int nodeJ);

  public int getElementNumber() {
    return getNodeNumber();
  }
}
