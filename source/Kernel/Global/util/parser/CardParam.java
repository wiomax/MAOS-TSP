/**
 * Description: store the parameter of command infomation that specified by users.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep. 19, 2002    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.util.parser;

import Global.basic.*;
import Global.define.*;
import Global.methods.*;

public class CardParam extends BasicAttrib {
  String seprateTag = "~";

  ValueUnion value = new ValueUnion();

  public CardParam(String newName, ValueUnion newValue){
    name = newName;
    value = newValue;
  };

  public CardParam(String newName, String newValue){
    setData(newName, newValue);
  };

  public ValueUnion getValue() {
    return value;
  }

  public void setData(String newName, String content) {
    name = newName;
    String[] values = GlobalString.tokenize(content, seprateTag);
    if(values.length==1) {
      value = new ValueUnion(values[0]);
    } else if(values.length>1) {
      value = new ValueUnion();
      value.setData(values[0], values[1]);
      if(values.length>2) {
        this.description = values[2];
      }
    }
  }

  public void loadObject(String tobeLoadStr) throws Exception {
    String[] data = GlobalString.tokenize(tobeLoadStr, BasicTag.EQUAL_TAG);
    if(data.length!=2) return;
    GlobalString.trim(data[1], " []");
    setData(data[0], data[1]);
  }

  public String saveObject() {
    String[] valueStrs = new String[3];
    valueStrs[0] = value.toString();
    valueStrs[1] = value.getClassName();
    valueStrs[2] = this.description;
    String varStr = "["+GlobalString.serinize(valueStrs, BasicTag.SEPERATE_TAG)+"]";
    return name+BasicTag.EQUAL_TAG+varStr;
  }
}
