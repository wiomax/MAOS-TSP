/**
 * Description: get available states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.memory;

public interface IGetEachEncodedStateEngine {
  public int getLibSize();
  public EncodedState getSelectedPoint(int index);
}
