/**
 * Description: provide the location information of a point
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 1, 2003
 *
 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * Please acknowledge the author(s) if you use this code in any way.
 */

package Global.basic.data.point;

import java.util.*;
import Global.methods.*;
import Global.basic.data.collection.*;

public class StableBPoint extends AbsBPoint implements IGetBArrayEngine {
  protected boolean[] location;

  public StableBPoint(int dim) {
    super(dim);
  }

  public StableBPoint(boolean[] location) {
    super(location);
  }

  public boolean[] getBArray() {
    return location;
  }

  public int getNodeNumber() {
    return location.length;
  }

  public void randInitialized() {
    for (int i=0; i<this.getNodeNumber(); i++) {
      location[i] = RandomGenerator.booleanRandom();
    }
  }

  public void initArray(int dim) {
    location = new boolean[dim];
  }

  public void initArray(boolean[] loc) {
    location = loc;
  }

  public boolean getValueAt(int index) {
    return location[index];
  }

  public void setValueAt(boolean value, int index) {
    location[index] = value;
  }

  public void delValueAt(int index) {
    location[index] = false;
  }

  public void setValueAt(int index) {
    location[index] = true;
  }

  public void flipValueAt(int index) {
    location[index] = !location[index];
  }

  public IAlienICollectionEngine getTrueElements() {
    int[] elements = BasicArray.getFlagIndices(getBArray(), true);
    return new BasicIAlienArray(elements.length, elements);
  }

  public int getElementNumber(boolean specElement) {
    return BasicArray.getElementNumber(getBArray(), specElement);
  }

  public void clear() {
    Arrays.fill(this.location, false);
  }

  public Object clone() {
    try {
      StableBPoint newPoint = (StableBPoint)super.clone();
      if(location!=null) {
        newPoint.initArray(getNodeNumber());
        newPoint.importPoint(this);
      }
      return newPoint;
    } catch(Exception e) {
      return null;
    }
  }

  public void importData(IImportableEngine point) {
    importPoint((StableBPoint)point);
  }

  public void importPoint(StableBPoint point) {
    importBArray(point);
  }

  public void importBArray(IGetBArrayEngine locationEngine) {
    System.arraycopy(locationEngine.getBArray(), 0, location, 0, location.length);
  }
}
