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

import Global.methods.*;
import Global.basic.data.collection.*;

public class DualBPoint extends AbsBPoint {
  protected DualIAlienArray trueElements;

  public DualBPoint(int dim) {
    super(dim);
  }

  public Object clone() {
    try {
      DualBPoint obj = (DualBPoint)super.clone();
      obj.initArray(getNodeNumber());
      obj.trueElements.importDualIAlienArray(trueElements);
      return obj;
    } catch(Exception e) {
      return null;
    }
  }

  public DualBPoint(boolean[] location) {
    super(location);
  }

  public int getNodeNumber() {
    return trueElements.getMaxSize();
  }

  public int getElementNumber(boolean specElement) {
    if (specElement) return trueElements.getSize();
    else return getNodeNumber()-trueElements.getSize();
  }

  public void randInitialized() {
    trueElements.clear();
    for (int i=0; i<this.getNodeNumber(); i++) {
      if (RandomGenerator.booleanRandom()) trueElements.addElement(i);
    }
  }

  public IAlienICollectionEngine getTrueElements() {
    return this.trueElements;
  }

  public void initArray(int dim) {
    trueElements = new DualIAlienArray(dim);
  }

  public void initArray(boolean[] loc) {
    trueElements.clear();
    for (int i=0; i<loc.length; i++) {
      if (loc[i]) trueElements.addElement(i);
    }
  }

  public void clear() {
    trueElements.clear();
  }

  public boolean getValueAt(int index) {
    if (trueElements.getElementID(index)!=-1) return true;
    else return false;
  }

  public void delValueAt(int index) {
    trueElements.removeElement(index);
  }

  public void setValueAt(int index) {
    trueElements.addElement(index);
  }

  public void flipValueAt(int index) {
    if (getValueAt(index)) delValueAt(index);
    else setValueAt(index);
  }

  public void importPoint(DualBPoint point) {
    trueElements.importDualIAlienArray(point.trueElements);
  }
  
  public void getBitString(boolean[] bitstring) {
    for (int i=0; i<this.getNodeNumber(); i++) {
      bitstring[i] = getValueAt(i);
    }
  }

  public void importPoint(AbsBPoint point) {
    if (point instanceof DualBPoint) {
      importPoint((DualBPoint)point);
    }
  }

  public void importData(IImportableEngine point) {
    if (point instanceof DualBPoint) {
      importPoint((DualBPoint)point);
    }
  }
}
