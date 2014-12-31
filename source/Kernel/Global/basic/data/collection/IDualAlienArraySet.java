/**
 * Description: IDualAlienArraySet
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2006
 * Xiaofeng Xie    May 31, 2006
 *
 * @version 1.0
 */


package Global.basic.data.collection;

import Global.methods.*;

public class IDualAlienArraySet {
  protected DualIAlienArray[] arraySet;

  protected IDualAlienArraySet(DualIAlienArray[] arraySet) {
    this.arraySet = arraySet;
  }

  public IDualAlienArraySet(int nodeNumber, int maxElemNumber) {
    initArraySet(nodeNumber, maxElemNumber);
  }

  public void initArraySet(int nodeNumber, int maxElemNumber) {
    arraySet = new DualIAlienArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      arraySet[i] = new DualIAlienArray(maxElemNumber);
    }
  }

  public IDualAlienArraySet toSubIAlienArraySet(int subSize) {
    if (subSize<0||subSize>this.getNodeNumber()) return this;
    int[] indices = RandomGenerator.randomDistinctSelection(this.getNodeNumber(), subSize);
    DualIAlienArray[] subArraySet = new DualIAlienArray[subSize];
    for (int i=0; i<subSize; i++) {
      subArraySet[i] = arraySet[indices[i]];
    }
    return new IDualAlienArraySet(subArraySet);
  }

  public void importElemArrayAt(DualIAlienArray array, int nodeID) {
    arraySet[nodeID].importDualIAlienArray(array);
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

  public DualIAlienArray getArrayAt(int index) {
    return arraySet[index];
  }

  public void removeNode(int elemV) {
    for (int i=0; i<this.getNodeNumber(); i++) {
      this.getArrayAt(i).removeElement(elemV);
    }
  }

  public boolean removeNodeAt(int elemV, int nodeID) {
    DualIAlienArray inNNArray = this.getArrayAt(nodeID);
    return inNNArray.removeElement(elemV);
  }

  public boolean addNodeTo(int elemV, int nodeID) {
    DualIAlienArray inNNArray = this.getArrayAt(nodeID);
    return inNNArray.addElement(elemV);
  }
}
