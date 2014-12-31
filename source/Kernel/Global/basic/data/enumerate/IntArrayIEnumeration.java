/**
 * Description: provide an Enumeration with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 01, 2006
 *
 */

package Global.basic.data.enumerate;

public class IntArrayIEnumeration implements IIEnumeration {
  private int[] valArray;
  private int currentID;

  public IntArrayIEnumeration(int[] valArray) {
    setValArray(valArray);
  }

  public void setValArray(int[] valArray) {
    this.valArray = valArray;
    currentID = -1;
  }

  public boolean hasMoreElements() {
    return(valArray.length>currentID+1);
  }

  public int nextElement() {
    currentID++;
    return valArray[currentID];
  }
}
