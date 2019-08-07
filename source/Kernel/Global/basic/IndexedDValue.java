/**
 * Description: provide a double value with an index
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 18, 2005
 */

package Global.basic;

public class IndexedDValue implements Comparable<IndexedDValue> {
  public int index = 0;              //the index
  public double value = 0;           //The value

  public IndexedDValue() {}

  public IndexedDValue(int i, double v) {
    index = i;
    value = v;
  }

  public int compareTo(IndexedDValue outO) {
    if (value<outO.value) return -1;
    else if (value==outO.value) return 0;
    else return 1;
  }
}
