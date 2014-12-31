/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;


public abstract class AbsRegularDMatrix extends AbsDMatrix implements IRegular2DEngine {

  public int getElementNumberAt(int index) {
    return getElementNumber();
  }

}
