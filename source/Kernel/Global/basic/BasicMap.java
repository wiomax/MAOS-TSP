/**
 * Description: gives a map: name and value
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 10, 2004
 */

package Global.basic;

import Global.define.*;
import Global.methods.*;

public class BasicMap extends BasicName {
  public String EqualSymbol = BasicTag.EQUAL_TAG;
  public String value = "";

  public BasicMap() {}

  public BasicMap(BasicMap bm) {
    name = bm.name;
    value = bm.value;
  }

  public BasicMap(String mapData) {
    setName(mapData);
  }

  public BasicMap(String name, String value) {
    setMap(name, value);
  }

  public BasicMap(String name, Object value) {
    setMap(name, value);
  }

  public Object clone(){
    try {
      BasicMap bn = (BasicMap)super.clone();
      bn.EqualSymbol = EqualSymbol;
      bn.value = value;
      return bn;
    } catch(Exception e) {
      return null;
    }
  }

  public boolean isEqual(BasicMap mapData) {
    return(mapData.name.trim().equalsIgnoreCase(name.trim())&&mapData.value.trim().equalsIgnoreCase(value.trim()));
  }

  public void setMap(String name, Object value) {
    this.name = name;
    if (value==null) {
      value = "";
    }
    this.value = value.toString();
  }

  public void setMap(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public boolean setLimitedData(String mapData) {
    String[] data = GlobalString.tokenize(mapData, EqualSymbol);
    if(data.length>2) return false;
    if(data.length==2) {
      setMap(data[0].trim(), data[1].trim());
    } else {
      setMap(data[0].trim(), "");
    }
    return true;
  }

  public boolean setUnlimitedData(String mapData, String seperator) {
    int index = mapData.indexOf(seperator);
    if (index==-1) {
      return false;
    }
    name = mapData.substring(0, index);
    value = mapData.substring(index+1);
    return true;
  }

  public boolean setUnlimitedData(String mapData) {
    return setUnlimitedData(mapData, EqualSymbol);
  }

  public String toUnlimitedString() {
    return name + EqualSymbol+value;
  }

  public String toNormalString() {
    if(value.equalsIgnoreCase("")) return "";
    try {
      new Double(value);
    } catch (Exception e) {
      if(!value.startsWith("[")||!value.endsWith("]")) {
        return value;
      }
    }
    return name + EqualSymbol + value;
  }
}
