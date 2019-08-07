/**
* Description: The description of a utility: an AbstractNode with value in switch type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

import java.util.*;
import Global.basic.data.bound.*;

public class SwitchUtility extends BasicUtility {
  public static final String SWITCH_UTIL_NAME = "SWITCH";
  BasicIBound limitation = new BasicIBound();

  public SwitchUtility() {
    super(SWITCH_UTIL_NAME);
    value = new Vector<Object>();
  }

  public SwitchUtility(String name) {
    super(name);
  }

  public SwitchUtility(BasicIBound limit) {
    this.limitation = limit;
  }

  public SwitchUtility(int fixedSize) {
    limitation.setFixBound(fixedSize);
  }

  public void clearAllData() {
    getAllData().clear();
  }

  @SuppressWarnings("unchecked")
	public Vector<Object> getAllData() {
    return (Vector<Object>) value;
  }

  public Object getElementAt(int index) {
    return getAllData().elementAt(index);
  }

  public boolean setGroupValues(Vector<? extends Object> outValue) {
    for(int i=0; i<outValue.size(); i++) {
      boolean isValid = setValue(outValue.elementAt(i));
      if(!isValid) return false;
    }
    return true;
  }
  public int getElementsSize() {
    return getAllData().size();
  }

  public boolean setValue(Object outValue) {
    if(super.isSatisfyConstraints(outValue)) {
      Vector<Object> allData = getAllData();
      if (limitation.isSatisfyCondition(getElementsSize())) {
        allData.add(outValue);
        return true;
      }
    }
    return false;
  }
}
