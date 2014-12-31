/**
 * Description: provide the information for boolean point
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 06, 2006
 */

package Global.basic.data.point;

public interface IBPointEngine {
  public boolean getValueAt(int index);
  public void delValueAt(int index);
  public void setValueAt(int index);
}
