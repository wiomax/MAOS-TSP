/**
 * Description: provide a boolean value with an index
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 18, 2005
 */

package Global.basic;

public class IndexedBValue {
  public int index = 0;                 //the index
  public boolean value = true;          //The value

  public IndexedBValue() {}

  public IndexedBValue(int i, boolean v) {
    index = i;
    value = v;
  }
}
