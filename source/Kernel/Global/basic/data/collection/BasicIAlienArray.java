/**
 * Description: Global package for storing different integer numbers.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 27, 2005
 *
 * @version 1.0
 */


package Global.basic.data.collection;

import Global.methods.*;

public class BasicIAlienArray extends IArray implements IAlienICollectionEngine, IElemRemoveEngine {

  public BasicIAlienArray(int size) {
    super(size);
  }

  public BasicIAlienArray(int elementCount, int[] elementData) {
    super(elementCount, elementData);
  }


  public boolean removeElement(int value) {
    int index = getElementID(value);
    if (index != -1) {
      removeElementAt(index);
      return true;
    }
    return false;
  }

  public boolean addAlienElement(int value) {
    return super.addElement(value);
  }

  public int getElementID(int value) {
    return BasicArray.getExactIndex(elementData, value, 0, elementCount);
  }

  public boolean addElement(int value) {
    int index = getElementID(value);
    if (index==-1) {
      return super.addElement(value);
    } else {
      return false;
    }
  }
}
