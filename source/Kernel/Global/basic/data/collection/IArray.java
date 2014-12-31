/**
 * Description: provide a Vector with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2005    Adapt from JDK1.4 Vector
 * Xiaofeng Xie    Nov 22, 2005    Become an array with unfixed elements
 *
 */

package Global.basic.data.collection;

public class IArray implements IDynamicICollectionEngine {
  protected int elementCount = 0;
  protected int[] elementData;

  public IArray(int maxSize) {
    elementData = new int[maxSize];
  }

  public boolean removeElement(int value) {
    boolean hasElement =false;
    for (int i=elementCount-1; i>=0; i--) {
      if (elementData[i]==value) {
        this.removeElementAt(i);
        hasElement = true;
      }
    }
    return hasElement;
  }

  public IArray(int elementCount, int[] elementData) {
    this.elementCount = elementCount;
    this.elementData = elementData;
  }

  public void importIArray(IBasicICollectionEngine iArray) {
    this.clear();
    for (int i=0; i<iArray.getSize(); i++) {
      this.addElement(iArray.getElementAt(i));
    }
  }

  public void importIArray(IArray iArray) {
    this.elementCount = iArray.elementCount;
    System.arraycopy(iArray.elementData, 0, this.elementData, 0, elementCount);
  }

  public int getSize() {
    return elementCount;
  }

  public boolean isEmpty() {
    return elementCount==0;
  }

  public boolean isFull() {
    return elementCount==elementData.length;
  }

  public int[] getClonedValues() {
    int[] values = new int[elementCount];
    System.arraycopy(elementData, 0, values, 0, elementCount);
    return values;
  }

  public void setSize(int size){
    this.elementCount = size;
  }

  public int[] getOrigValues() {
    return elementData;
  }

  public int[] getValueArray() {
    return elementData;
  }

  public int getElementAt(int index) {
    return elementData[index];
  }

  public boolean removeElementAt(int index) {
    if(index<0||index>=elementCount) {
      return false;
    }
    System.arraycopy(elementData, index+1, elementData, index, elementCount-index-1);
    elementCount --;
    return true;
  }

  public void clear() {
    elementCount = 0;
  }

  public void clear(int index) {
    if(index<elementCount) elementCount = index;
  }

  public boolean addElement(int value) {
    if (isFull()) return false;
    elementData[elementCount] = value;
    elementCount++;
    return true;
  }

}
