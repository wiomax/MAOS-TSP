/**
 * Description: provide dual pointers, all the elements must be different, and
 *  each element value is belong to [0, elemSize-1]
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 19, 2005
 */

package Global.basic.data.collection;

import java.util.*;
import Global.methods.*;

public class DualIAlienArray implements IDynamicICollectionEngine, IAlienICollectionEngine {
  //all the values are different to each others
  private int[] valueArray, indexArray;
  private int elemSize = 0;

  //temp value
  private int sqrtSize = 0;

  public DualIAlienArray(int maxSize) {
    setSize(maxSize);
  }

  public boolean isEmpty() {
    return elemSize==0;
  }

  public void setSize(int maxSize) {
    valueArray = new int[maxSize];
    indexArray = new int[maxSize];
    Arrays.fill(indexArray, -1);
    elemSize = 0;
    sqrtSize = (int)Math.sqrt(maxSize);
  }

  public void importDualIAlienArray(DualIAlienArray alienArray) {
    System.arraycopy(alienArray.valueArray, 0, this.valueArray, 0, valueArray.length);
    System.arraycopy(alienArray.indexArray, 0, this.indexArray, 0, valueArray.length);
    this.elemSize = alienArray.elemSize;
  }

  public void addElements(int[] dataV) {
    for(int i=0; i<dataV.length; i++) {
      addElement(dataV[i]);
    }
  }

  public void addSafeElements(int[] dataV, int startIndex, int endIndex) {
    System.arraycopy(dataV, startIndex, valueArray, elemSize, endIndex-startIndex);
    for (int i=startIndex; i<endIndex; i++) {
      indexArray[valueArray[elemSize]]=elemSize;
      elemSize++;
    }
  }

  public int getMaxSize() {
    return valueArray.length;
  }

  public boolean addUnsafeElement(int elemV) {
    if (!isValid(elemV)||elemSize>=getMaxSize()-1) {
      return false;
    }
    return addElement(elemV);
  }

  public boolean addElement(int elemV) {
    if (indexArray[elemV]!=-1) return false;
    valueArray[elemSize]=elemV;
    indexArray[elemV]=elemSize;
    elemSize++;
    return true;
  }

  public boolean isValid(int elemV) {
    return (elemV>=0 && elemV<getMaxSize());
  }

  public boolean removeElementAt(int elemID) {
    int elemV = getElementAt(elemID);
    if (elemV==-1) return false;
    return removeElement(elemV);
  }

  public boolean removeElement(int elemV) {
    int postSize = elemSize-1;
    if (postSize<0){
      return false;
    }
    if (this.getElementID(elemV)==-1) return false;
    valueArray[indexArray[elemV]]=valueArray[postSize];
    indexArray[valueArray[postSize]]=indexArray[elemV];
    indexArray[elemV] = -1;
    elemSize--;
    return true;
  }

  public int[] getValueArray() {
    return valueArray;
  }

  public int getElementAt(int elemID) {
    return valueArray[elemID];
  }

  public int getElementID(int elemV) {
    return indexArray[elemV];
  }

  public void stepClear() {
    for (int i=0; i<elemSize; i++) {
      indexArray[valueArray[i]]=-1;
    }
    elemSize = 0;
  }

  public void fullClear() {
    Arrays.fill(indexArray, -1);
    elemSize = 0;
  }
  
  public void clear() {
    if (elemSize<sqrtSize) {
      stepClear();
    } else {
      fullClear();
    }
  }

  public int getRandomElement() {
    return valueArray[RandomGenerator.intRangeRandom(elemSize)];
  }

  public int getSize() {
    return elemSize;
  }

}
