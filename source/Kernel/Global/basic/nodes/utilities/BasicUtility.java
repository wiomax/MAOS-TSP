/**
 * Description: The description of a utility: an AbstractNode with value in given type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

import Global.basic.IGetSummaryEngine;
import Global.basic.nodes.*;

public class BasicUtility extends AbstractNode implements IDataTypeDescriptionEngine, IDataConstraintEngine {

//  public boolean isInput = true;
//  public boolean isOutput = true;
  protected Object value;

  public BasicUtility() {
  }

  public BasicUtility(String outName) {
    this();
    this.setName(outName.trim());
  }

  public BasicUtility(String outName, Object val) {
    this(outName);
    this.setValue(val);
  }

  public BasicUtility getCopiedUtility() {
    BasicUtility utility = (BasicUtility)super.clone();
    utility.value = getClonedValue();
//    utility.isInput = this.isInput;
    return utility;
  }

  public boolean setValue(Object outValue) {
    if(isSatisfyConstraints(outValue)) {
      value = outValue;
      return true;
    }
    return false;
  }

  public String getSummary() {
    if(value==null) {
      return name;
    } else if (value instanceof IGetSummaryEngine) {
      return name+"="+((IGetSummaryEngine)value).getSummary();
    } else {
      return name+"="+getValueString();
    }
  }

  public String getValueString() {
    return value.toString();
  }

  public String getDataTypeDescription() {
    return description;
  }

  public Object getValue() {
    return value;
  }

  protected boolean isValidDataType(Object data) {
    if(data==null) return false;
    return true;
  }

  public boolean isSatisfyConstraints() {
    return isSatisfyConstraints(value);
  }

  public boolean isSatisfyConstraints(Object data) {
    if(!isValidDataType(data)) {
      return false;
    }
    return true;
  }

  public Object getClonedValue(Object val) {
    return val;
  }

  public Object getClonedValue() {
    return getClonedValue(value);
  }
}
