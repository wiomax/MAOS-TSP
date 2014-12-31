/**
 * Description: obtains the extremal value
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.basic.data.collection;

public class ExtremalCollector {
  private boolean isMax;
  private int extremalV;
  IPumpICollectionEngine dataArray;

  public ExtremalCollector(boolean isMax) {
    this.isMax = isMax;
  }

  public void initIPumpICollectionEngine(IPumpICollectionEngine dataArray) {
    this.dataArray = dataArray;
  }

  public void clear() {
    extremalV = -Integer.MAX_VALUE;
    if (!isMax) extremalV = Integer.MAX_VALUE;
    this.dataArray.clear();
  }

  public int getExtremalValue() {
    return this.extremalV;
  }

  public void submitData(int index, int value) {
    if ((extremalV < value)==isMax && extremalV!=value) {
      extremalV = value;
      dataArray.clear();
      dataArray.addElement(index);
    } else if (extremalV==value) {
      dataArray.addElement(index);
    }
  }
}
