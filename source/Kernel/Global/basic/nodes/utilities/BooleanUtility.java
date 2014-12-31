/**
 * Description: The description of a utility: an AbstractNode with value in boolean type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 20, 2004
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

public class BooleanUtility extends StringableUtility {

  public BooleanUtility(String outName) {
    super(outName);
    this.description = "bool";
  }

  public BooleanUtility(String outName, boolean defaultV) {
    this(outName);
    this.setValue(new Boolean(defaultV));
  }

  public boolean isValidDataType(Object data) {
    try {
      data = new Boolean(data.toString());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Object getClonedValue(Object value) {
    if(value==null) return null;
    return new Boolean(value.toString());
  }

  public boolean getDefaultValue() {
    return ((Boolean)value).booleanValue();
  }

  protected Object getRealObject(String sVal) throws Exception {
    return new Boolean(sVal);
  }
}
