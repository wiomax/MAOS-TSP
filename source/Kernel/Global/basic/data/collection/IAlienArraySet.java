/**
 * Description: A set of BasicIAlienArray objects
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2006
 *
 * @version 1.0
 */


package Global.basic.data.collection;

import Global.methods.*;

public class IAlienArraySet {
  protected BasicIAlienArray[] arraySet;

  protected IAlienArraySet(BasicIAlienArray[] arraySet) {
    this.arraySet = arraySet;
  }

  public IAlienArraySet(int nodeNumber, int maxElemNumber) {
    initArraySet(nodeNumber, maxElemNumber);
  }

  public void initArraySet(int nodeNumber, int maxElemNumber) {
    arraySet = new BasicIAlienArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      arraySet[i] = new BasicIAlienArray(maxElemNumber);
    }
  }

  public IAlienArraySet toSubIAlienArraySet(int subSize) {
    if (subSize<0||subSize>this.getNodeNumber()) return this;
    int[] indices = RandomGenerator.randomDistinctSelection(this.getNodeNumber(), subSize);
    BasicIAlienArray[] subArraySet = new BasicIAlienArray[subSize];
    for (int i=0; i<subSize; i++) {
      subArraySet[i] = arraySet[indices[i]];
    }
    return new IAlienArraySet(subArraySet);
  }

  public void importElemArrayAt(BasicIAlienArray array, int nodeID) {
    arraySet[nodeID].importIArray(array);
  }

  public int getValueAt(int nodeID, int elemID) {
    return arraySet[nodeID].getElementAt(elemID);
  }

  public int getElementNumberAt(int index) {
    return arraySet[index].getSize();
  }

  public int getNodeNumber() {
    return arraySet.length;
  }

  public void clear() {
    for (int i=0; i<getNodeNumber(); i++) {
      clearAt(i);
    }
  }

  public int getMaxCardinalityID() {
    int mcID = -1;
    int mcValue = -1;
    for (int i=0; i<this.getNodeNumber(); i++) {
      if (mcValue<getElementNumberAt(i)) {
        mcValue = getElementNumberAt(i);
        mcID = i;
      }
    }
    return mcID;
  }

  public void clearAt(int index) {
    arraySet[index].clear();
  }

  public BasicIAlienArray getArrayAt(int index) {
    return arraySet[index];
  }

  public void removeNode(int elemV) {
    for (int i=0; i<this.getNodeNumber(); i++) {
      this.getArrayAt(i).removeElement(elemV);
    }
  }

  public boolean removeNodeAt(int elemV, int nodeID) {
    BasicIAlienArray inNNArray = this.getArrayAt(nodeID);
    return inNNArray.removeElement(elemV);
  }

  public boolean addAlienNodeTo(int elemV, int nodeID) {
    BasicIAlienArray inNNArray = this.getArrayAt(nodeID);
    return inNNArray.addAlienElement(elemV);
  }

  public boolean addNodeTo(int elemV, int nodeID) {
    BasicIAlienArray inNNArray = this.getArrayAt(nodeID);
    return inNNArray.addElement(elemV);
  }
}
