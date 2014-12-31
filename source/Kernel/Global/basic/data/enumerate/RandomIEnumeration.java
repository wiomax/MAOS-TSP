/**
 * Description: provide an Enumeration with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 01, 2006
 *
 */

package Global.basic.data.enumerate;

public class RandomIEnumeration implements IIEnumeration {
  private int maxSize;
  private int currentID;
  private int selectedID;

  public RandomIEnumeration(int maxSize) {
    setMaxSize(maxSize);
  }

  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
    currentID = -1;
  }

  public boolean hasMoreElements() {
    for(int i=currentID+1; i<maxSize; i++) {
      if (Math.random()<0.5) {
        selectedID = i;
        return true;
      }
    }
    return false;
  }

  public int nextElement() {
    currentID = selectedID;
    return currentID;
  }
}
