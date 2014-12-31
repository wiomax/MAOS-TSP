/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public abstract class AbsIMatrix extends AbsVMatrix implements IGetIValueAt2DEngine, IGetIArrayAtEngine {

  abstract public int[] getArrayAt(int rowIndex);

  public int getValueAt(int nodeI, int nodeJ) {
    return getArrayAt(nodeI)[nodeJ];
  }
}
