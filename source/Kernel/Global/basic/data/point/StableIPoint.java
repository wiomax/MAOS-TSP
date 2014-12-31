/**
 * Description: provide the location information of a point
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005
 *
 */

package Global.basic.data.point;

import Global.basic.*;
import Global.basic.data.collection.*;

public class StableIPoint extends BasicObject implements INodeNumberEngine, IGetIArrayEngine {
  protected int[] location;

  public StableIPoint(int dim) {
    location = new int[dim];
  }

  public StableIPoint(int[] location) {
    initLocation(location);
  }

  public void initLocation(int[] location) {
    this.location = location;
  }

  public int[] getIArray() {
    return location;
  }

  public void setValueAt(int value, int index) {
    location[index] = value;
  }

  public int getValueAt(int index) {
    return location[index];
  }

  public int getNodeNumber() {
    return location.length;
  }

  public void importStableIPoint(StableIPoint point) {
    System.arraycopy(point.location, 0, location, 0, location.length);
  }

  public Object clone() {
    try {
      StableIPoint obj = (StableIPoint)super.clone();
      obj.location = new int[getNodeNumber()];
      obj.importStableIPoint(this);
      return obj;
    } catch(Exception e) {
      return null;
    }
  }
}
