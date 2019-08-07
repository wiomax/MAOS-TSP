/**
 * Description: provide an integer value with an index
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 18, 2005
 */

package Global.basic;

public class IndexedIValue implements Comparable<IndexedIValue> {
  public int index = 0;              //the index
  public int value = 0;              //The value

  public IndexedIValue() {}

  public IndexedIValue(int i, int v) {
    index = i;
    value = v;
  }

  public int compareTo(IndexedIValue outO) {
    if (value<outO.value) return -1;
    else if (value==outO.value) return 0;
    else return 1;
  }
}
