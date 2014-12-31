/**
 * Description: A point in a matrix representation
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 18, 2006
 */

package Global.basic.data.point;

import Global.basic.data.collection.*;

public class MatrixBPoint {
  private IAlienArraySet colPoint;
  private IAlienArraySet rowPoint;

  public MatrixBPoint(int rowNumber, int colNumber) {
    colPoint = new IAlienArraySet(colNumber, rowNumber);
    rowPoint = new IAlienArraySet(rowNumber, colNumber);
  }
  
  public void clear() {
    colPoint.clear();
    rowPoint.clear();
  }

  public void importColumnAt(BasicIAlienArray colData, int colID) {
    colPoint.importElemArrayAt(colData, colID);
    for (int i = 0; i < colData.getSize(); i++) {
      rowPoint.addNodeTo(colID, colData.getElementAt(i));
    }
  }

  public BasicIAlienArray getColElementsAt(int colID) {
    return colPoint.getArrayAt(colID);
  }
  
  public int getRowElementValueAt(int rowID, int seqID) {
    return rowPoint.getValueAt(rowID, seqID);
  }

  public int getRowElementNumber(int rowID) {
    return rowPoint.getElementNumberAt(rowID);
  }
  
  public int getRowNumber() {
    return rowPoint.getNodeNumber();
  }
  
  public int getColNumber() {
    return colPoint.getNodeNumber();
  }

  public void addElement(int rowID, int colID) {
    colPoint.addAlienNodeTo(rowID, colID);
    rowPoint.addAlienNodeTo(colID, rowID);
  }

  public void removeElement(int rowID, int colID) {
    colPoint.removeNodeAt(rowID, colID);
    rowPoint.removeNodeAt(colID, rowID);
  }
}

