/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;


public abstract class AbsDMatrix extends AbsVMatrix implements IGetDValueAt2DEngine, IGetDArrayAtEngine {

  abstract public double[] getArrayAt(int rowIndex);

  public double getValueAt(int nodeI, int nodeJ) {
    return getArrayAt(nodeI)[nodeJ];
  }
}
