/**
 * Description: provide the location information of a point
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 1, 2003
 *
 */

package Global.basic.data.point;

import Global.basic.*;
import Global.basic.data.collection.*;

public abstract class AbsBPoint extends BasicObject implements IBPointEngine, IImportableEngine, INodeNumberEngine {

  public AbsBPoint(int dim) {
    initArray(dim);
  }

  public AbsBPoint(boolean[] location) {
    initArray(location);
  }
  public void setValueAt(boolean value, int index) {
    if (value) this.setValueAt(index);
    else this.delValueAt(index);
  }

  abstract public IAlienICollectionEngine getTrueElements();

  abstract public void clear();

  abstract public int getElementNumber(boolean specElement);

  abstract public void randInitialized();

  abstract public void initArray(int dim);

  abstract public void initArray(boolean[] loc);

  abstract public void flipValueAt(int index);
}
