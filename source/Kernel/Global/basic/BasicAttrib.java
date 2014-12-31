/**
 * Description: provide a named class, including name, key, description
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 1, 2003     
 */

package Global.basic;

import Global.methods.*;

public class BasicAttrib extends BasicName implements IBasicAttribEngine {
  protected String key = "";
  public String description = "";         //The description

  public BasicAttrib() {}

  public BasicAttrib(String name) {
    super(name);
  }

  public Object clone(){
    try {
      BasicAttrib no = (BasicAttrib)super.clone();
      no.importBasicAttrib(this);
      return no;
    } catch(Exception e) {
      return null;
    }
  }

  public void importBasicAttrib(BasicAttrib no) {
    super.setName(no.name);
    this.key = no.key;
    this.description = no.description;
  }


  public boolean isSameKey(String outKey) {
    return key.equalsIgnoreCase(outKey)||key.equalsIgnoreCase(ANY_KEY)||outKey.equalsIgnoreCase(ANY_KEY);
  }

  public boolean isSameNameAndKey(String outName, String outKey) {
    return isSameName(outName) && isSameKey(outKey);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  //name(key)
  public String getDetailName() {
    String str = name;
    if(!GlobalString.isNull(key)) {
      str += "("+key+")";
    }
    return str;
  }


  public String getDescription() {
    return description;
  }

}
