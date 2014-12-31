/**
 * Description: The description of a utility: an AbstractNode with value in double type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2004
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

import Global.methods.*;

public class DoubleUtility extends StringableUtility {
  protected DoubleUtility(String outName) {
    super(outName);
    this.description = "double";
  }

  public DoubleUtility(String outName, double defaultV) {
    this(outName);
    this.setValue(new Double(defaultV));
  }

  public boolean isValidDataType(Object data) {
    try {
      data = new Double(data.toString());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getValueString() {
    return TypeConverter.toString(getDefaultValue());
  }

  protected Object getRealObject(String sVal) throws Exception {
    return new Double(TypeConverter.toDouble(sVal));
  }

  public double getDefaultValue() {
    return ((Double)value).doubleValue();
  }

  public Object getClonedValue(Object value) {
    if(value==null) return null;
    return new Double(value.toString());
  }

}
