/**
 * Description: The description of a utility: an AbstractNode with value in string array type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

import java.util.*;
import Global.methods.*;

public class StringArrayUtility extends StringableUtility {

  public StringArrayUtility(String outName, Vector<Object> val) {
    this(outName, GlobalString.serinize(val, "|"));
  }

  public StringArrayUtility(String outName, String[] val) {
    this(outName, GlobalString.serinize(val, "|"));
  }

  public StringArrayUtility(String outName, String val) {
    this(outName);
    this.setValue(val);
  }

  public StringArrayUtility(String outName) {
    super(outName);
    this.description = "StringArray";
  }

  public Object getClonedValue(Object value) {
    if(value==null) return null;
    return value.toString();
  }

  public String[] getDefaultValue() {
    if(value==null) {
      return new String[0];
    }
    if (value instanceof String[]) return (String[])value;
    return GlobalString.tokenize(value.toString(), "|");
  }

  public boolean setDefaultValue(String[] sVal) {
    return setValue(GlobalString.serinize(sVal, "|"));
  }

  public boolean isValidDataType(Object data) {
    return true;
  }
}
