/**
 * Description: The description of a utility: an AbstractNode with value in integer type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 20, 2004
 *
 */

package Global.basic.nodes.utilities;

import Global.methods.*;

public class IntegerUtility extends StringableUtility {

  public IntegerUtility(String outName) {
    super(outName);
    this.description = "int";
  }

  public IntegerUtility(String outName, int defaultV) {
    this(outName);
    this.setValue(new Integer(defaultV));
  }

  public boolean isValidDataType(Object data) {
    try {
      data = getRealObject(data.toString());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getValueString() {
    return TypeConverter.toString(getDefaultValue());
  }

  public Object getClonedValue(Object value) {
    if(value==null) return null;
    return new Integer(value.toString());
  }

  public int getDefaultValue() {
    return ((Integer)value).intValue();
  }

  protected Object getRealObject(String sVal) throws Exception {
    return new Integer(TypeConverter.toInteger(sVal));
  }
}
