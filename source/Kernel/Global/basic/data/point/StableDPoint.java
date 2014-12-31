/**
 * Description: provide the location information of a point
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 1, 2003
 *
 */

package Global.basic.data.point;

import Global.basic.*;
import Global.basic.data.collection.INodeNumberEngine;

public class StableDPoint extends BasicObject implements IGetDArrayEngine, INodeNumberEngine {
  protected double[] location;

  public StableDPoint(int dim) {
    initArray(dim);
  }

  public StableDPoint(double[] location) {
    initArray(location);
  }

  public double[] getDArray() {
    return location;
  }

  public int getNodeNumber() {
    return location.length;
  }

  public void initArray(int dim) {
    location = new double[dim];
  }

  public void initArray(double[] loc) {
    location = loc;
  }

  public double getValueAt(int index) {
    return location[index];
  }

  public Object clone() {
    try {
      StableDPoint newPoint = (StableDPoint)super.clone();
      newPoint.initArray(getNodeNumber());
      newPoint.importBasicDDPoint(this);
      return newPoint;
    } catch(Exception e) {
      return null;
    }
  }

  public void importBasicDDPoint(StableDPoint point) {
    importDArray(point);
  }

  public void importDArray(IGetDArrayEngine locationEngine) {
    double[] pointLoc = locationEngine.getDArray();
    System.arraycopy(pointLoc, 0, location, 0, pointLoc.length);
  }
}
